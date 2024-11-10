package org.example.libmgmt.cli;

import java.sql.Date;

public class User {
    private int UID;
    private String username;
    private String password;
    private String address;
    private Date dob;
    private String email;
    private String name;
    private String sex;
    private String phone;
    private String SSN;
    private boolean userType;


    public User() {
    }

    public int getUID() {
        return UID;
    }

    public void setUID(int UID) {
        this.UID = UID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
