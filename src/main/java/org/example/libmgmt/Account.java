package org.example.libmgmt;

import java.util.Objects;

public class Account {
    private String username;
    private String password;
    private int UID;

    public Account(int UID, String username, String password) {
        this.UID = UID;
        this.username = username;
        this.password = password;
    }

    public Account() {
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getUID() {
        return UID;
    }

    public void setUID(int UID) {
        this.UID = UID;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Account acc = (Account) obj;
        return this.UID == acc.UID
                && Objects.equals(this.username, acc.username)
                && Objects.equals(this.password, acc.password);
    }

}
