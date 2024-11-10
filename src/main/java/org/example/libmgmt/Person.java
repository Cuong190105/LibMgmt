package org.example.libmgmt;

public class Person {
    private int id;
    private String title;
    private String password;
    private float fine;

    public Person(int id, String title, String password, float fine) {
        this.id = id;
        this.title = title;
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
        return title;
    }

    public void setName(String title) {
        this.title = title;
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
