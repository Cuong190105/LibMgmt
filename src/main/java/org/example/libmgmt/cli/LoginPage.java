package org.example.libmgmt.cli;

import java.util.Scanner;

public class LoginPage extends Page {
    public LoginPage(){
        SceneManager.switchPage(this);
    }

    @Override
    public void show() {
        System.out.println("Choose an action:");
        System.out.println("[1] Sign in.");
        System.out.println("[2] Sign up.");
        System.out.println("[3] Retrieve Password.");
        System.out.println("[0] Exit.");
        Scanner sc = new Scanner(System.in);
        int option = sc.nextInt();
        sc.close();
        switch (option) {
            case 1 -> {
                signIn();
            }
            case 2 -> {
                signUp();
            }
            case 3 -> {
                retrievePwd();
            }
            case 0 -> {
                SceneManager.switchPage(null);
            }
        }
    }
    public void signIn() {
        Scanner sc = new Scanner(System.in);
        System.out.println("SIGN IN");
        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Password: ");
        String pwd = sc.nextLine();
        System.out.println("Logging in");

        //Later I'll change this to be handled by another thread to prevent freezing app while fetching info
        //authenticate(username, password) includes: Checking if username exists in DB, and checking if the password is correct for that username.
        //It returns the validation status: username not exist, password incorrect, login succeeded (, or server is busy).
        //After successfully authenticated, create a session on server side to mark that the user is logged in.
        int status = authenticate(username, pwd);

        if (status == LOGIN_SUCCEEDED) {
            HomePage hp = new HomePage(username);
        } else {
            // Simplifies incorrect credentials handler, update later
            System.out.println("Wrong credentials! Press enter to get back.");
            sc.nextLine();
        }
        sc.close();
    }

    public void signUp() {
        Scanner sc = new Scanner(System.in);
        System.out.println("SIGN UP");
        System.out.print("Username: ");
        String username = sc.nextLine();

        // This function checks that:
        // 1. Username must follow the rules: At least 6 characters, no whitespace, contains only [A-Z], [a-z], [0-9], [.], [_]. (Processes on client side)
        // 2. Username is unique (not existed in DB so far).
        while (!isValidUsername(username)) {
            System.out.println("Username must have at least 6 characters.");
            System.out.println("Only letters (A-Z and a-z), digits (0-9), underscores (_) and periods (.) are allowed.");
            System.out.println("Retype? (Y/n): ");
            char cmd = (char) sc.nextByte();
            if (cmd == 'N' || cmd == 'n') {
                return;
            }
            System.out.print("Username: ");
            username = sc.nextLine();
        }
        System.out.print("Password: ");
        String pwd = sc.nextLine();

        // Password must follow the rules:
        // 1. At least 6 characters
        // 2. Contains only characters can be typed on standard keyboard (for compatibility to store and transfer through internet)
        // (Processes on client side)

        while (!isValidPassword(pwd)) {
            System.out.println("Password must have at least 6 characters and contain only common use letters, digits or punctuation characters!");
            System.out.println("Retype? (Y/n): ");
            char cmd = (char) sc.nextByte();
            if (cmd == 'N' || cmd == 'n') {
                return;
            }
            System.out.print("Password: ");
            pwd = sc.nextLine();
        }

        System.out.print("Confirm Password: ");
        String rePwd = sc.nextLine();

        while (pwd.compareTo(rePwd) != 0) {
            System.out.println("Those passwords are not identical! Retype? (Y/n): ");
            char cmd = (char) sc.nextByte();
            if (cmd == 'N' || cmd == 'n') {
                return;
            }
            System.out.print("Password: ");
            rePwd = sc.nextLine();
        }
        createAccount(username, rePwd);
        System.out.println("Account successfully created! Go back to login page.");
        sc.close();
    }

    // No need to do. Just for demonstration.
    public void retrievePwd() {

    }
}
