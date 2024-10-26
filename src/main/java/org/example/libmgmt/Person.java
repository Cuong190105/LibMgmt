package org.example.libmgmt;

public class Person {
    private int id;
    private String name;
    private String password;
    private float fine;

    public Person(int id, String name, String password, float fine) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.fine = fine;
    }

    public Person() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public float getFine() {
        return fine;
    }

    public void setFine(float fine) {
        this.fine = fine;
    }
}
