package com.firebase.sample.demo.test.model;

/**
 * Created by Sushant.Patekar on 6/13/2017.
 */

public class UserData {
    public String username;
    public String password;
    public String roleName;

    public UserData(String username, String password, String roleName) {
        this.username = username;
        this.password = password;
        this.roleName = roleName;
    }

    public UserData() {
    }
}
