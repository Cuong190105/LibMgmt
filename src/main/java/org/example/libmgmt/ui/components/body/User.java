package org.example.libmgmt.ui.components.body;

import org.example.libmgmt.LibMgmt;

import java.io.InputStream;
import java.util.Date;

public class User {
    private long UID;
    private String address;
    private Date dob;
    private String email;
    private String name;
    private String sex;
    private String phone;
    private String SSN;
    private InputStream avatar;

    private boolean userType;

    public User(long userId) {
        UID = userId;
        name = "Soume Douge";
        avatar = LibMgmt.class.getResourceAsStream("img/accountAction/user0123456.png");
    }

    public long getUID() {
        return UID;
    }

    public String getAddress() {
        return address;
    }

    public Date getDob() {
        return dob;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public String getPhone() {
        return phone;
    }

    public String getSSN() {
        return SSN;
    }

    public boolean isUserType() {
        return userType;
    }

    public InputStream getAvatar() {
        return avatar;
    }

    public boolean hasRead(long docId) {
        return true;
    }
}