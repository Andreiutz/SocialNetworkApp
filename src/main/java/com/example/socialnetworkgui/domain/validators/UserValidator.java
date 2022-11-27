package com.example.socialnetworkgui.domain.validators;


import com.example.socialnetworkgui.domain.user.Address;
import com.example.socialnetworkgui.domain.user.User;

import java.time.LocalDate;

/**
 *  User Validator, used for validating User Objects
 */

public class UserValidator implements Validator<User> {

    /**
     * Validates a name, whether it is first or last name
     * @param name, String, should not have ' ', digits or special characters, excepting '-'
     * @return true, if name is valid, false otherwise
     */
    private boolean validName(String name) {
        if (name == null) {
            return false;
        }
        if (name.length() == 0) {
            return false;
        }
        for (char character : name.toCharArray()) {
            //We also accept '-' character in case a user has 2 names
            if (!(Character.isLetter(character) || character == '-')) {
                return false;
            }
        }
        return true;
    }

    /**
     * Validates a username
     * @param userName, String, should not have ' '
     * @return true, if userName is valid, false otherwise
     */
    private boolean validUserName(String userName) {
        if (userName == null) {
            return false;
        }
        if (userName.length() == 0) {
            return false;
        }
        //A userName is valid as long as it is not null
        for (char character : userName.toCharArray()) {
            if (character == ' ') {
                return false;
            }
        }
        return true;
    }

    /**
     * Validates an email address
     * @param email, String
     * @return true, if email valid, false otherwise
     */
    private boolean validEmail(String email) {
        if (email == null) return false;
        return (email.matches("^[a-z]+[^\\. ]*@[a-z]+\\.[a-z]+"));
    }

    /**
     * Validates a birth date
     * @param date, LocalDate
     * @return true, date is valid, false otherwise
     */
    private boolean validDate(LocalDate date) {
        if (date == null) return false;
        return date.compareTo(LocalDate.now()) <= 0;
    }

    private boolean validAddress(Address address) {
        boolean valid = true;
        if (address.getCountry() == null || !address.getCountry().matches("^[a-zA-Z-]+$")) valid = false;
        if (address.getCounty() == null || !address.getCounty().matches("^[a-zA-Z-]+$")) valid = false;
        if (address.getCity() == null || !address.getCity().matches("^[a-zA-Z-]+$")) valid = false;
        if (address.getStreet() == null || !address.getStreet().matches("^[a-zA-Z-]+$")) valid = false;
        if (address.getStreetNumber() < 0) valid = false;
        return valid;
    }

    /**
     * Validates all the parameters of a User instance
     * @param entity, User
     * @throws ValidationException, if any of the parameters of the entity are not valid
     */
    @Override
    public void validate(User entity) throws ValidationException {
        String errorMessage = "";
        if (!validName(entity.getFirstName())) {
            errorMessage += "Invalid first name!\n";
        }
        if (!validName(entity.getLastName())) {
            errorMessage += "Invalid last name!\n";
        }
        if (!validUserName(entity.getId())) {
            errorMessage += "Invalid user name!\n";
        }
        if (!validEmail(entity.getEmail())) {
            errorMessage += "Invalid email address!\n";
        }
        if (!validDate(entity.getBirthDate())) {
            errorMessage += "Invalid birth date!\n";
        }
        if (!validAddress(entity.getAddress())) {
            errorMessage += "Invalid address!\n";
        }
        if (errorMessage.length() != 0) {
            throw new ValidationException(errorMessage);
        }

    }
}
