package com.example.socialnetworkgui.domain.user;

import java.util.Objects;

public class Address {
    private String country;
    private String county;
    private String city;
    private String street;
    private Long streetNumber;

    public Address(String country, String county, String city, String street, Long streetNumber) {
        this.country = country;
        this.county = county;
        this.city = city;
        this.street = street;
        this.streetNumber = streetNumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Long getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(Long streetNumber) {
        this.streetNumber = streetNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return streetNumber == address.streetNumber && country.equals(address.country) && county.equals(address.county) && city.equals(address.city) && street.equals(address.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, county, city, street, streetNumber);
    }

    @Override
    public String toString() {
        return "Address{\n" +
                "\tcountry ='" + country + "'\n" +
                "\tcounty ='" + county + "'\n" +
                "\tcity ='" + city + "'\n" +
                "\tstreet ='" + street + "'\n" +
                "\tstreetNumber ='" + streetNumber + "'\n" +
                '}';
    }
}
