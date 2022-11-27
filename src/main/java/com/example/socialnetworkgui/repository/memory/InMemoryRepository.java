package com.example.socialnetworkgui.repository.memory;


import com.example.socialnetworkgui.domain.Entity;
import com.example.socialnetworkgui.domain.validators.Validator;
import com.example.socialnetworkgui.repository.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Generic repo for saving entities in memory
 * @param <ID>, the type of the IDs of the entities
 * @param <E>, the Entity Objects
 */
public class InMemoryRepository<ID, E extends Entity<ID>> implements Repository<ID,E> {

    private Validator<E> validator;
    Map<ID,E> entities;

    /**
     * Constructor
     * @param validator, generic validator, specially used for validating the generic entities
     */
    public InMemoryRepository(Validator<E> validator) {
        this.validator = validator;
        entities=new HashMap<ID,E>();
    }

    /**
     * Returns the entity with the given ID or null if it does not exist
     * @param id -the id of the entity to be returned
     *           id must not be null
     * @return the found entity or null
     */
    @Override
    public E findOne(ID id){
        if (id==null)
            throw new IllegalArgumentException("id must be not null");
        return entities.getOrDefault(id, null);
    }

    /**
     * @return all the entities in the repository
     */
    @Override
    public Iterable<E> findAll() {
        return entities.values();
    }

    /**
     * Saves a new entity in the repository, if the ID is not already taken
     * @param entity
     *         entity must be not null
     * @throws ValidationException
     * @return true, if the new entity is saved, false otherwise
     */
    @Override
    public boolean save(E entity) {
        if (entity==null)
            throw new IllegalArgumentException("entity must be not null");
        validator.validate(entity);
        if(entities.get(entity.getId()) != null) {
            return false;
        }
        else entities.put(entity.getId(),entity);
        return true;
    }

    /**
     * Deletes an entity from the repository, if it exists
     * @param id
     *      id must be not null
     * @return the deleted entity, or null if no entity was deleted
     */
    @Override
    public E delete(ID id) {
        E entity = entities.getOrDefault(id, null);
        if (entity != null) {
            entities.remove(id);
        }
        return entity;
    }

    /**
     * Updates an entity which already exists
     * @param entity
     *          entity must not be null
     * @throws ValidationException
     * @return true is the entity was updated, false otherwise
     */
    @Override
    public boolean update(E entity) {

        if(entity == null)
            throw new IllegalArgumentException("entity must be not null!");
        validator.validate(entity);
        if (entities.getOrDefault(entity.getId(), null) != null) {
            entities.put(entity.getId(), entity);
            return true;
        }
        return false;

    }

}
