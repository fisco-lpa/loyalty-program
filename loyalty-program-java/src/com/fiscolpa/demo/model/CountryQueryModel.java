package com.fiscolpa.demo.model;

public class CountryQueryModel extends QueryModel {

    private Country country;

    public CountryQueryModel() {
        this(new Country());
    }

    public CountryQueryModel(Country country) {
        this.country = country;
    }

    public Integer getId() {
        return country.getId();
    }

    public void setId(Integer id) {
        country.setId(id);
    }

    public String getCountrycode() {
        return country.getCountrycode();
    }

    public void setCountrycode(String countrycode) {
        country.setCountrycode(countrycode);
    }

    public String getCountryname() {
        return country.getCountryname();
    }

    public void setCountryname(String countryname) {
        country.setCountryname(countryname);
    }
}