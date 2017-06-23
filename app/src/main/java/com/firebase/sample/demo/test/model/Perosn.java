package com.firebase.sample.demo.test.model;

import java.io.Serializable;

/**
 * Created by Sushant.Patekar on 6/19/2017.
 */

public class Perosn implements Serializable {
    public String firstName;
    public String lastName;
    public String weight;
    public String height;
    public String personAddress;
    public String profileUrl;

    public Perosn() {
    }

    public Perosn(String firstName, String lastName, String weight, String height, String personAddress, String profileUrl) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.weight = weight;
        this.height = height;
        this.personAddress = personAddress;
        this.profileUrl = profileUrl;
    }

    public Perosn(String firstName, String personAddress) {
        this.firstName = firstName;
        this.personAddress = personAddress;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
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

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getPersonAddress() {
        return personAddress;
    }

    public void setPersonAddress(String personAddress) {
        this.personAddress = personAddress;
    }


}
