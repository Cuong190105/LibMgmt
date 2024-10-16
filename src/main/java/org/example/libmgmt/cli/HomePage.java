package org.example.libmgmt.cli;

import java.util.Scanner;

public class HomePage extends Page {
    //Placeholder
    private static final int MEMBER = 1;
    private static final int MANAGER = 2;

    // User object store basic info of user online account: username, UID, displayName, Avatar photo
    private User user;

    // Default constructor to skip login session, used for testing.
    public HomePage() {}

    public HomePage(String username) {
        // Check if the user is logged in (Login session for this user created when authenticating)
        if (isLoggedIn(username)) {
            // User's constructor takes 1 argument (username) to fetch other data from server.
            user = new User(username);
            SceneManager.switchPage(this);
        } else {
            System.out.println("Authentication failed. Press enter to get back to login session.");
            Scanner sc = new Scanner(System.in);
            sc.nextLine();
            sc.close();
        }
    }

    // Shows content on page.
    @Override
    public void show() {
        System.out.println("Choose user type (for testing purpose): ");
        System.out.println("[1] Member.");
        System.out.println("[2] Manager.");
        System.out.println("Enter selection: ");
        Scanner sc = new Scanner(System.in);
        char cmd = (char) sc.nextByte();
        sc.close();
        switch(cmd) {
            case 1 -> {
                memberHomepage();
            }
            case 2 -> {
                managerHomepage();
            }
            default -> {
                System.out.println("Wrong selection.");
            }
        }
    }

    // Shows specialized homepage for manager user.
    private void managerHomepage() {

    }

    // Shows specialized homepage for member user.
    public void memberHomepage() {
        System.out.println("Welcome, [Member]!");
        System.out.println("Choose an action:");
        System.out.println("[1] View library");
        System.out.println("[2] Search for documents");
        System.out.println("[3] View book borrowed history");
        System.out.println("[4] Change account info");
        System.out.print("Enter selection: ");
        Scanner sc = new Scanner(System.in);
        char cmd = (char) sc.nextByte();
        switch (cmd) {
            case 1 -> {
                viewLibrary();
            }
            case 2 -> {
                searchDoc();
            }
            case 3 -> {
                borrowHistory();
            }
            case 4 -> {
                changeInfo();
            }
        }
    }

    // Shows document list.
    private void viewLibrary() {
        System.out.println("Choose a section:");
        System.out.println("[1] Most read recently");
        System.out.println("[2] Latest Books");
        System.out.println("[3] Latest Thesis'");
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter selection: ");
        char section = (char) sc.nextByte();
        sc.close();
        // Get documents info corresponding to each section. Later all 3 sections are shown simultaneously, each with 10 - 15 books.
        // Fetch more books if user select see more.
        // On book list screen, each book is represented with the book cover photo, title and author. Other info will be shown in book info screen.
        ArrayList<Document> documentList = getDocumentList(section);

        // For this demo, only show title and author of document
        int idx = 1;
        for (Document d : documentList) {
            System.out.println("[" + idx + "] " + d.getTitle());
            System.out.println("by " + d.getAuthor());
        }
    }

    // Searches for documents.
    private void searchDoc() {
        System.out.println("Seach for document.");
        System.out.print("Enter keyword: ");
        Scanner sc = new Scanner(System.in);
        String keyword = sc.nextLine();

        // Search for documents, of course!
        ArrayList<Document> result = getSearchResult(keyword);
        System.out.println(result.size() + " result(s) found.");

        int idx = 1;
        for (Document d : result) {
            System.out.println("[" + idx + "] " + d.getName());
            System.out.println("by " + d.getAuthor());
        }
    }

    // Shows user's borrow history
    private void borrowHistory() {
        System.out.println("Book History");

        // Query all borrow history records with matched UserID
        ArrayList<Document> result = getBorrowHistory(userId);

        int idx = 1;
        for (Document d : result) {
            System.out.println("[" + idx + "] " + d.getName());
            System.out.println("by " + d.getAuthor());
        }
    }

    private void changeInfo() {
        System.out.println("Choose an option:");
        System.out.println("[1] Change password");
        System.out.println("[2] Change personal information");
        System.out.print("Enter selection: ");
        Scanner sc = new Scanner(System.in);
        int cmd = sc.nextInt();
        sc.close();
        switch (cmd) {
            case 1 -> {
                changePwd();
            }
            case 2 -> {
                changePersonalInfo();
            }
        }
    }

    private void changePwd() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Current Password: ");
        char cmd;
        String pwd = sc.nextLine();
        while (!isCorrectPassword(user.getUsername(), pwd)) {
            System.out.print("Wrong password. Retype? (Y/n): ");
            cmd = (char) sc.nextByte();
            if (cmd == 'N' || cmd == 'n') {
                return;
            }
            System.out.print("Current Password: ");
            pwd = sc.nextLine();
        }

        System.out.print("New Password: ");
        String newPwd = sc.nextLine();
        while (!isValidPassword(newPwd)) {
            System.out.println("Password must have at least 6 characters and contain only common use letters, digits or punctuation characters!");
            System.out.println("Retype? (Y/n): ");
            cmd = (char) sc.nextByte();
            if (cmd == 'N' || cmd == 'n') {
                return;
            }
            System.out.print("New password: ");
            newPwd = sc.nextLine();
        }

        System.out.print("Confirm Password: ");
        String confirmPwd = sc.nextLine();
        while (newPwd.compareTo(confirmPwd) != 0) {
            System.out.println("Those passwords are not identical!");
            System.out.println("Retype? (Y/n): ");
            cmd = (char) sc.nextByte();
            if (cmd == 'N' || cmd == 'n') {
                return;
            }
            System.out.print("New password: ");
            confirmPwd = sc.nextLine();
        }
        System.out.println("Password changed successfully!");
        sc.close();

    }

    // Later the change will be sent as a request and will be examined manually
    private void changePersonalInfo() {}
}
