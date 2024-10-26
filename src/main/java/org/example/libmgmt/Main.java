package org.example.libmgmt;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to the Library");
        System.out.println("0:Register");
        System.out.println("1:Log In");

        PersonDB personDB = new PersonDB();
        List<Person> users = personDB.getDB();

        int getInt = input.nextInt();
        String getStr;
        if(getInt == 0) {
            System.out.print("Name: ");
            String name = input.next();
            System.out.print("ID: ");
            int id = input.nextInt();
            System.out.print("Password: ");
            String pass = input.next();
            Person person = new Person(id, name, pass, 0);
            PersonDB.add(person);

        } else {
            System.out.println();
        }
    }
}
