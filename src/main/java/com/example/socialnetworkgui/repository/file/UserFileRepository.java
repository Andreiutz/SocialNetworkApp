package com.example.socialnetworkgui.repository.file;

import com.example.socialnetworkgui.domain.user.Address;
import com.example.socialnetworkgui.domain.user.User;
import com.example.socialnetworkgui.domain.validators.Validator;
import com.example.socialnetworkgui.utils.constants.TimeFormatConstants;

import java.time.LocalDate;
import java.util.List;

public class UserFileRepository extends AbstractFileRepository<String, User> {

    public UserFileRepository(String fileName, Validator<User> validator) {
        super(fileName, validator);
    }

    /**
     * Extracts a User entity from a list of Strings
     * Format:
     * 0 -> id
     * 1 -> firstName
     * 2 -> lastname
     * 3 -> emailAddress
     * 4 -> birth date
     * 5 -> country
     * 6 -> county
     * 7 -> city
     * 8 -> street
     * 9 -> streetNumber
     * @param attributes
     * @return User
     */
    @Override
    public User extractEntity(List<String> attributes) {
        //TODO: implement method
        return new User(
                attributes.get(0),
                attributes.get(1),
                attributes.get(2),
                attributes.get(3),
                LocalDate.parse(attributes.get(4), TimeFormatConstants.DATE_FORMAT),
                new Address(
                        attributes.get(5),
                        attributes.get(6),
                        attributes.get(7),
                        attributes.get(8),
                        Long.parseLong(attributes.get(9))
                ),
                attributes.get(10)
        );
    }

    /**
     * Parses a User entity into a String
     * Format:
     * 0 -> id
     * 1 -> firstName
     * 2 -> lastname
     * 3 -> emailAddress
     * 4 -> birth date
     * 5 -> country
     * 6 -> county
     * 7 -> city
     * 8 -> street
     * 9 -> streetNumber
     * @param entity
     * @return User
     */
    @Override
    protected String createEntityAsString(User entity) {
        return entity.getId() + ";"
                +entity.getFirstName() + ";"
                +entity.getLastName() + ";"
                +entity.getEmail() + ";"
                +entity.getBirthDate().format(TimeFormatConstants.DATE_FORMAT) + ";"
                +entity.getAddress().getCountry() + ";"
                +entity.getAddress().getCounty() + ";"
                +entity.getAddress().getCity() + ";"
                +entity.getAddress().getStreet() + ";"
                +entity.getAddress().getStreetNumber();
    }
}
