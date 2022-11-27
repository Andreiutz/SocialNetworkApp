package com.example.socialnetworkgui.repository.database;


import com.example.socialnetworkgui.domain.user.Address;
import com.example.socialnetworkgui.domain.user.User;
import com.example.socialnetworkgui.domain.validators.UserValidator;
import com.example.socialnetworkgui.utils.constants.TimeFormatConstants;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class UserDbRepository extends AbstractDbRepository<String, User> {

    public UserDbRepository(String url, String userName, String password, UserValidator validator) {
        super(url, userName, password, validator);
    }

    @Override
    protected User extractEntity(ResultSet resultSet) throws SQLException {
        String userName = resultSet.getString("user_name");
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        String email = resultSet.getString("email");
        LocalDate birthDate = resultSet.getDate("birth_date").toLocalDate();
        String country = resultSet.getString("country");
        String county = resultSet.getString("county");
        String city = resultSet.getString("city");
        String street = resultSet.getString("street");
        Long streetName = resultSet.getLong("street_number");
        String password = resultSet.getString("password");

        return new User(
                userName,
                firstName,
                lastName,
                email,
                birthDate,
                new Address(
                        country,
                        county,
                        city,
                        street,
                        streetName
                ),
                password
        );
    }

    @Override
    protected String getByIdQuery(String s) {
        return String.format("SELECT * FROM users WHERE user_name LIKE '%s'", s);
    }

    @Override
    protected String getAllQuery() {
        return "SELECT * FROM users";
    }

    @Override
    protected String saveEntityQuery(User user) {
        return "INSERT INTO users(user_name, first_name, last_name, " +
                "email, birth_date, country, county, city, street, street_number) VALUES " +
                String.format("('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s');",
                        user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getBirthDate().format(TimeFormatConstants.SQL_DATE_FORMAT),
                        user.getAddress().getCountry(),
                        user.getAddress().getCounty(),
                        user.getAddress().getCity(),
                        user.getAddress().getStreet(),
                        user.getAddress().getStreetNumber().toString());
    }

    @Override
    protected String deleteEntityQuery(String s) {
        return String.format("DELETE FROM users WHERE user_name LIKE '%s'", s);
    }

    @Override
    protected String updateEntityQuery(User entity) {
        return String.format("UPDATE users SET " +
                        "first_name = '%s', last_name = '%s', email = '%s', birth_date = '%s', " +
                        "country = '%s', county = '%s', city = '%s', street = '%s', street_number = '%s', password = '%s' WHERE user_name LIKE '%s'",
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getBirthDate().format(TimeFormatConstants.SQL_DATE_FORMAT),
                entity.getAddress().getCountry(),
                entity.getAddress().getCounty(),
                entity.getAddress().getCity(),
                entity.getAddress().getStreet(),
                entity.getAddress().getStreetNumber().toString(),
                entity.getId(),
                entity.getPassword());
    }
}
