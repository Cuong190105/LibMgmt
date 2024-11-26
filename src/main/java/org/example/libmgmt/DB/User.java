package org.example.libmgmt.DB;

import org.example.libmgmt.LibMgmt;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Objects;

public class User {
    private int UID;
    private String address;
    private Date dob;
    private String email;
    private String name;
    private String sex;
    private String phone;
    private String SSN;
    private boolean isLibrarian;
    private InputStream avatar;


    public User() {
        this.address = "123 Hồ Tùng Mậu, Bắc Từ Liêm, Hà Nội";
        this.dob = new Date(12022004);
        this.email = "random@gmail.com";
        this.name = "Lorem Ipsum";
        this.sex = "Nam";
        this.phone = "0987654321";
        this.SSN = "001204020169";
        this.isLibrarian = false;
    }

    public User(String username, String address, Date dob, String email, String name, String sex, String phone, String SSN, boolean isLibrarian) {
        this.address = address;
        this.dob = dob;
        this.email = email;
        this.name = name;
        this.sex = sex;
        this.phone = phone;
        this.SSN = SSN;
        this.isLibrarian = isLibrarian;
    }

    public int getUID() {
        return UID;
    }

    public void setUID(int UID) {
        this.UID = UID;
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

    public boolean isLibrarian() {
        return isLibrarian;
    }

    public void setLibrarian(boolean librarian) {
        this.isLibrarian = librarian;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return UID == user.UID &&
                Objects.equals(address, user.address) &&
                dob.equals(user.dob) &&
                Objects.equals(email, user.email) &&
                Objects.equals(name, user.name) &&
                Objects.equals(sex, user.sex) &&
                Objects.equals(phone, user.phone) &&
                Objects.equals(SSN, user.SSN) &&
                isLibrarian == user.isLibrarian;
    }

    public InputStream getAvatar() {
        return avatar;
    }

    public void setAvatar(Blob avatar) {
        try {
            this.avatar = avatar.getBinaryStream();
        } catch (Exception e) {
            this.avatar = LibMgmt.class.getResourceAsStream("img/accountAction/user0123456.png");
        }
    }
}
