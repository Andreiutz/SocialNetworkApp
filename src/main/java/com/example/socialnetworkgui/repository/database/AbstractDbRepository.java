package com.example.socialnetworkgui.repository.database;


import com.example.socialnetworkgui.domain.Entity;
import com.example.socialnetworkgui.domain.validators.Validator;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;


/**
 * Abstract repository for storing entities in database
 * @param <ID> identifier of an entity
 * @param <E> entity
 */
public abstract class AbstractDbRepository<ID, E extends Entity<ID>> implements DBRepository<ID,E> {

    protected String url;
    protected String userName;
    protected String password;
    protected Validator<E> validator;

    /**
     * Constructor
     * @param url address of the database
     * @param userName username for database login
     * @param password password for database login
     * @param validator entity validato
     */
    public AbstractDbRepository(String url, String userName, String password, Validator<E> validator) {
        this.url = url;
        this.userName = userName;
        this.password = password;
        this.validator = validator;
    }

    /**
     * Abstract function for extracting an entity from the database
     * @param resultSet result given by the query
     * @return the entity created by the resultSet
     * @throws SQLException if something wrong happens
     */
    protected abstract E extractEntity(ResultSet resultSet) throws SQLException;

    /**
     * Generates the query for selecting an entity based of an id
     * @return the requested SQL query
     */
    protected abstract String getByIdQuery(ID id);

    /**
     * Generates the query for selecting all entities from the database
     * @return the requested SQL query
     */
    protected abstract String getAllQuery();

    /**
     * Generates the query for inserting a new value in the database
     * @return the requested SQL query
     */
    protected abstract String saveEntityQuery(E entity);

    /**
     * Generates the query for deleting a value from the database
     * @return the requested SQL query
     */
    protected abstract String deleteEntityQuery(ID id);

    /**
     * Generates the query for updating an entity from the database
     * @return the requested SQL query
     */
    protected abstract String updateEntityQuery(E entity);

    /**
     * Searches for an entity based on a given ID
     * @param id -the id of the entity to be returned
     *           id must not be null
     * @return the entity, if found or null
     */
    @Override
    public E findOne(ID id) {
        try (Connection connection = DriverManager.getConnection(url, userName, password);
             PreparedStatement statement = connection.prepareStatement(
                     getByIdQuery(id));
             ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractEntity(resultSet);
                } else {
                    return null;
                }
        } catch (SQLException exception) {
            return null;
        }
    }

    /**
     * @return all the entities stored in the database
     */
    @Override
    public Iterable<E> findAll() {
        Set<E> entities = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, userName, password);
        PreparedStatement statement = connection.prepareStatement(
                getAllQuery());
        ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                E entity = extractEntity(resultSet);
                entities.add(entity);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return entities;
    }

    /**
     * Saves a new entity in the database, if valid
     * @param entity
     *         entity must be not null
     * @return true if the entity was successfully saved, false otherwise
     */
    @Override
    public boolean save(E entity) {
        if (entity == null) throw new IllegalArgumentException("entity must not be null!");
        E foundEntity = findOne(entity.getId());
        if (foundEntity != null) {
            return false;
        }
        validator.validate(entity);
        String sql = saveEntityQuery(entity);
        try (Connection connection = DriverManager.getConnection(url, userName, password);
        PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
            return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    /**
     * Deletes an entity based on a given ID
     * @param id
     *      id must be not null
     * @return the entity which was deleted or null if nothing was deleted
     */
    @Override
    public E delete(ID id) {
        E entity = findOne(id);
        if (entity == null) {
            return null;
        } else {
            String sql = deleteEntityQuery(id);
            try(Connection connection = DriverManager.getConnection(url, userName, password);
            PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.executeUpdate();
                return entity;
            } catch (SQLException exception) {
                exception.printStackTrace();
                return null;
            }
        }
    }

    /**
     * Updated an entity from the database
     * @param entity
     *          entity must not be null
     * @return true, if the entity was successfully updated, false otherwise
     */
    @Override
    public boolean update(E entity) {
        if (entity == null) {
            throw new IllegalArgumentException("entity must be not null!");
        }
        validator.validate(entity);
        String sql = updateEntityQuery(entity);
        try (Connection connection = DriverManager.getConnection(url, userName, password);
        PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
            return true;
        } catch (SQLException exception) {
            return false;
        }
    }

    /**
     * Returns a query based on a custom sql query
     * @param sqlStatement
     * @return
     */
    @Override
    public Iterable<E> getCustomList(String sqlStatement) {
        Set<E> entities = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, userName, password);
             PreparedStatement statement = connection.prepareStatement(
                     sqlStatement);
             ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                E entity = extractEntity(resultSet);
                entities.add(entity);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return entities;
    }
}
