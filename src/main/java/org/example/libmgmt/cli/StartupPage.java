package org.example.libmgmt.cli;

public class StartupPage extends Page {
    public StartupPage() {
        System.out.println("Welcome to LibMa 1.0!");
    }

    @Override
    public void show() {
        System.out.println("Welcome to LibMa 1.0!");
        LoginPage lp = new LoginPage();
    }
}
