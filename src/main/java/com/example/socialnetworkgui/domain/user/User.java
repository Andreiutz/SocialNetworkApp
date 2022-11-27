package com.example.socialnetworkgui.domain.user;


import com.example.socialnetworkgui.domain.Entity;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Objects;

public class User extends Entity<String> {
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthDate;
    private Address address;
    private String password;

    /**
     * Copy constructor
     * @param anotherUser, type User
     */
    public User(User anotherUser) {
        this.firstName = anotherUser.firstName;
        this.lastName = anotherUser.lastName;
        this.email = anotherUser.email;
        this.birthDate = anotherUser.birthDate;
        this.address = anotherUser.address;
        this.password = anotherUser.password;
        super.setId(anotherUser.getId());
    }

    /**
     * User's basic constructor withoud ID
     * @param firstName, String
     * @param lastName, String
     */
    public User(String firstName, String lastName, String email, LocalDate date, Address address, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email.toLowerCase(Locale.ROOT);
        this.birthDate = date;
        this.address = address;
        this.password = password;
    }

    /**
     * User's constructor with ID
     * @param firstName, String
     * @param lastName, String
     * @param userName, String
     */
    public User(String userName, String firstName, String lastName, String email, LocalDate date, Address address, String password) {
        this(firstName, lastName, email, date, address, password);
        super.setId(userName);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return firstName.equals(user.firstName) && lastName.equals(user.lastName) && email.equals(user.email) && birthDate.equals(user.birthDate) && address.equals(user.address) && password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), firstName, lastName, email, birthDate, address, password);
    }

    @Override
    public String toString() {
        return "User{\n" +
                "\tuserName  ='" + super.getId() + "'\n" +
                "\tfirstName ='" + firstName + "'\n" +
                "\tlastName  ='" + lastName + "'\n" +
                "\temail     ='" + email + "'\n" +
                "\tbirthDate ='" + birthDate + "'\n"+
                "\taddress   ='" + address.toString() + "'\n" +
                "\tpassword  ='" + password + "'\n" +
                '}';
    }
}