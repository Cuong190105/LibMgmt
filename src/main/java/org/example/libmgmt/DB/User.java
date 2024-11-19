package org.example.libmgmt.DB;

import java.sql.Date;
import java.util.Objects;

public class User {
    private int UID;
    private String username;
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

    public User(String username, String address, Date dob, String email, String name, String sex, String phone, String SSN, boolean userType) {
        this.username = username;
        this.address = address;
        this.dob = dob;
        this.email = email;
        this.name = name;
        this.sex = sex;
        this.phone = phone;
        this.SSN = SSN;
        this.userType = userType;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSSN() {
        return SSN;
    }

    public void setSSN(String SSN) {
        this.SSN = SSN;
    }

    public boolean isUserType() {
        return userType;
    }

    public void setUserType(boolean userType) {
        this.userType = userType;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return UID == user.UID &&
                Objects.equals(username, user.username) &&
                Objects.equals(address, user.address) &&
                dob.equals(user.dob) &&
                Objects.equals(email, user.email) &&
                Objects.equals(name, user.name) &&
                Objects.equals(sex, user.sex) &&
                Objects.equals(phone, user.phone) &&
                Objects.equals(SSN, user.SSN) &&
                userType == user.userType;
    }
}
