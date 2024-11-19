package org.example.libmgmt;

import org.example.libmgmt.DB.User;
import org.example.libmgmt.DB.UserDAO;

import java.util.Scanner;

public class HomePage extends Page {
    //Placeholder
    private static final int MEMBER = 1;
    private static final int MANAGER = 2;

    // User object store basic info of user online account: username, UID, displayName, Avatar photo
    private User user;

    // Default constructor to skip login session, used for testing.
    public HomePage() {}

    //not
    public HomePage(String username) {
        // Check if the user is logged in (Login session for this user created when authenticating)
//        if (isLoggedIn(username)) {
            // User's constructor takes 1 argument (username) to fetch other data from server.
            UserDAO userDAO = UserDAO.getInstance();
            user = userDAO.getUserFromUsername(username);
            SceneManager.switchPage(this);
//        } else {
//            System.out.println("Authentication failed. Press enter to get back to login session.");
//            Scanner sc = new Scanner(System.in);
//            sc.nextLine();
//            sc.close();
//        }
    }

    // Shows content on page.
    @Override
    public void show() {
        System.out.println("Choose user type (for testing purpose): ");
        System.out.println("[1] Member.");
        System.out.println("[2] Manager.");
        System.out.println("Enter selection: ");
        Scanner sc = new Scanner(System.in);
        int cmd = sc.nextInt();
        //sc.close();
        switch(cmd) {
//            case 1 -> {
//                memberHomepage();
//            }
//            case 2 -> {
//                managerHomepage();
//            }
            default -> {
                System.out.println("Wrong selection.");
            }
        }
    }
//
//    // Shows specialized homepage for manager user.
//    private void managerHomepage() {
//        System.out.println("Welcome, [Manager]!");
//        System.out.println("Choose an action:");
//        System.out.println("[0] Exit");
//        System.out.println("[1] Add Document");
//        System.out.println("[2] Remove Document");
//        System.out.println("[3] Update Document");
//        System.out.println("[4] Find Document");
//        System.out.println("[5] Display Document");
//        System.out.println("[6] Add User");
//        System.out.println("[7] Borrow Document");
//        System.out.println("[8] Return Document");
//        System.out.println("[9] Display User Info");
//        System.out.print("Enter selection: ");
//        Scanner sc = new Scanner(System.in);
//        int cmd = sc.nextInt();
//        switch (cmd) {
//            case 0 -> {
//                SceneManager.switchPage(null);
//            }
////            case 1 -> {
////                addDocumentUI();
////            }
////            case 2 -> {
////                removeDocumentUI();
////            }
////            case 3 -> {
////                updateDocumentUI();
////            }
////            case 4 -> {
////                searchDocUI();
////            }
////            case 5 -> {
////                displayDocumentUI();
////            }
////            case 6 -> {
////                addUserUI();
////            }
////            case 7 -> {
////                borrowDocumentUI();
////            }
////            case 8 -> {
////                returnDocumentUI();
////            }
////            case 9 -> {
////                displayUserInfoUI();
////            }
//        }
//    }
//
//    private void addDocumentUI() {
//        // Metadata of new document: title, author, publisher, ...
//        //Document newDoc = new Document(...);
//        Scanner sc = new Scanner(System.in);
//        String title = sc.next();
//        sc.nextLine();//call if from next to nextLine
//        String author = sc.nextLine();
//        String publisher = sc.nextLine();
//        int quantity = sc.nextInt();
//        String tags = sc.next();
//        int visited = sc.nextInt();
//        Document newDoc = new Document(title, author, publisher, quantity, tags, visited);
//
//        // Adds document to document DB. After sending ADD request, addDocument receives a docId.
//        // We will store document content in a separate database, due to the large size of document content (May go up to
//        // thousands pages with lots of photos). That PDF file takes quite a time to load, but only one book content is shown at a time.
//        // We will use docId to download content only when user select display document.
//        DocumentDAO docDAO = DocumentDAO.getInstance();
//        newDoc.setDocID(docDAO.addDocument(newDoc));
//        //doc.addDocument();
//
//        // Document content: the actual data/content inside the book
//        // Document content may have it own class, or just use some class/library object to hold the file. You can decide or we will discuss later.
//        DocumentContent dc = new DocumentContent(contentFile);
//        doc.updateContent(dc);
//    }
//
//    private void removeDocumentUI() {
//        // The currently selected document. All functions below also use this representation.
//        Document doc;
//
//        // Remove all document data from DB with matched docID
//        //doc.removeDocument();
//        DocumentDAO docDAO = DocumentDAO.getInstance();
//        docDAO.deleteDocument(doc.getDocID());
//    }
//
//    private void updateDocumentUI() {
//        Document doc;
//        DocumentContent updateContent;
//
//        // Copy doc object to this, we will modify this.
//        Document updatedVersion = new document(needUpdate);
//
//        // ...Code to modify needUpdate, put it into updatedVersion. If updatedVersion == doc (no change's been made), don't query the server.
//        // We can compare 2 version in updateInfo method, then we don't need to wrap updateInfo into this if block.
////        if (!updatedVersion.equals(needUpdate)) {
////            doc.updateInfo(updatedVersion);
////        }
//        DocumentDAO docDAO = DocumentDAO.getInstance();
//        docDAO.updateDocument(updatedVersion);
//
//        // Upload new PDF file to updateContent. If there's no need to update content, let it null and we won't push content change to server
//        // Similar to updateInfo above, we can check if content is null in updateContent method.
//        if (updateContent != null) {
//            doc.updateContent(updateContent);
//        }
//    }
//
//    private void displayDocumentUI() {
//        Document doc;
//        DocumentContent docContent = doc.downloadContent();
//    }
//
//    //not
//    private void addUserUI() {
//        // Create new user with basic info: username, full name(display name), email, ... (password not included).
//        // If successfully created, server will generate a random password then send back to user.
//        User newUser = new User(...);
//        String pwd = newUser.addUser();
//        System.out.println("Password: " + pwd);
//        System.out.println("Remember to change password");
//    }
//
//    //not
//    private void borrowDocumentUI() {
//        // A form will be displayed. Some fields in the form: Document, User, Borrow Date, Return Date, ...(many more if you can think of)
//        DocumentDAO docDao = DocumentDAO.getInstance();
//        UserDAO userDAO = UserDAO.getInstance();
//        List<Document> docList = docDao.searchDocKey(keyword);
//        Document doc = docList.get(ith_item);
//
//        User borrower = userDAO.getUserFromUsername(username);
//        // ...
//        // May show a bit info of document and borrower for librarian to review if doc is suitable for borrower or borrower is restricted from some aspects.
//        if (doc.isAvailable()) {
//            // borrowDocument(doc, user, isAdminRequest)
//            Request.borrowDocument(doc, borrower, true);
//        }
//    }
//
//    private void displayUserInfoUI() {
//        User user;
//        // Some getter method to get user info
//        // ...
//    }
//
//    //not
//    private void returnDocumentUI() {
//        Document doc;
//        long copyId;
//        User user;
//        // Each physical copy of a document has its unique identification number. Use that to verify it is the authentic copy from library, not a fake replacement.
//        returnDocument(user, doc, copyId);
//    }
//
//    // Shows specialized homepage for member user.
//    public void memberHomepage() {
//        System.out.println("Welcome, [Member]!");
//        System.out.println("Choose an action:");
//        System.out.println("[0] Exit");
//        System.out.println("[1] View library");
//        System.out.println("[2] Search for documents");
//        System.out.println("[3] View book borrowed history");
//        System.out.println("[4] Change account info");
//        System.out.print("Enter selection: ");
//        Scanner sc = new Scanner(System.in);
//        int cmd = sc.nextInt();
//        //sc.close();
//        switch (cmd) {
//            case 0 -> {
//                SceneManager.switchPage(null);
//            }
//            case 1 -> {
//                viewLibrary();
//            }
//            case 2 -> {
//                searchDocUI();
//            }
//            case 3 -> {
//                borrowHistory();
//            }
//            case 4 -> {
//                changeInfo();
//            }
//        }
//    }
//
//    // Shows document list.
//    private void viewLibrary() {
//        System.out.println("Choose a section:");
//        System.out.println("[1] Most read recently");
//        System.out.println("[2] Latest Books");
//        System.out.println("[3] Latest Thesis'");
//        Scanner sc = new Scanner(System.in);
//        System.out.print("Enter selection: ");
//        int section = sc.nextInt();
//        //sc.close();
//
//        // Get documents info corresponding to each section. Later all 3 sections are shown simultaneously, each with 10 - 15 books.
//        // Fetch more books if user select see more.
//        // On book list screen, each book is represented with the book cover photo, title and author. Other info will be shown in book info screen.
//        DocumentDAO docDao = DocumentDAO.getInstance();
//        List<Document> documentList = docDao.sortedList(false);
//
//        // For this demo, only show title and author of document
//        int idx = 1;
//        for (Document d : documentList) {
//            System.out.println("[" + idx + "] " + d.getName());
//            System.out.println("by " + d.getAuthor());
//        }
//    }
//
//    // Searches for documents.
//    private void searchDocUI() {
//        System.out.println("Search for document.");
//        System.out.print("Enter keyword: ");
//        Scanner sc = new Scanner(System.in);
//        String keyword = sc.nextLine();
////        sc.close();
//
//        // Search for documents, of course!
//        DocumentDAO docDao = DocumentDAO.getInstance();
//        List<Document> result = docDao.searchDocKey(keyword);
//        System.out.println(result.size() + " result(s) found.");
//
//        int idx = 1;
//        for (Document d : result) {
//            System.out.println("[" + idx + "] " + d.getName());
//            System.out.println("by " + d.getAuthor());
//        }
//    }
//
//    // Shows user's borrow history
//    private void borrowHistory() {
//        System.out.println("Book History");
//
//        // Query all borrow history records with matched UserID
//        List<Document> result = BorrowDAO.borrowHistory(userId);
//
//        int idx = 1;
//        for (Document d : result) {
//            System.out.println("[" + idx + "] " + d.getName());
//            System.out.println("by " + d.getAuthor());
//        }
//    }
//
//    private void changeInfo() {
//        System.out.println("Choose an option:");
//        System.out.println("[1] Change password");
//        System.out.println("[2] Change personal information");
//        System.out.print("Enter selection: ");
//        Scanner sc = new Scanner(System.in);
//        int cmd = sc.nextInt();
////        sc.close();
//        switch (cmd) {
//            case 1 -> {
//                changePwd();
//            }
//            case 2 -> {
//                changePersonalInfo();
//            }
//        }
//    }
//
//    private void changePwd() {
//        Scanner sc = new Scanner(System.in);
//        System.out.print("Current Password: ");
//        char cmd;
//        String pwd = sc.nextLine();
//        while (!isCorrectPassword(user.getUsername(), pwd)) {
//            System.out.print("Wrong password. Retype? (Y/n): ");
//            cmd = (char) sc.nextByte();
//            if (cmd == 'N' || cmd == 'n') {
//                return;
//            }
//            System.out.print("Current Password: ");
//            pwd = sc.nextLine();
//        }
//
//        System.out.print("New Password: ");
//        String newPwd = sc.nextLine();
//        while (!isValidPassword(newPwd)) {
//            System.out.println("Password must have at least 6 characters and contain only common use letters, digits or punctuation characters!");
//            System.out.println("Retype? (Y/n): ");
//            cmd = (char) sc.nextByte();
//            if (cmd == 'N' || cmd == 'n') {
//                return;
//            }
//            System.out.print("New password: ");
//            newPwd = sc.nextLine();
//        }
//
//        System.out.print("Confirm Password: ");
//        String confirmPwd = sc.nextLine();
//        while (newPwd.compareTo(confirmPwd) != 0) {
//            System.out.println("Those passwords are not identical!");
//            System.out.println("Retype? (Y/n): ");
//            cmd = (char) sc.nextByte();
//            if (cmd == 'N' || cmd == 'n') {
//                return;
//            }
//            System.out.print("New password: ");
//            confirmPwd = sc.nextLine();
//        }
//        applyPasswordChange(user.getUsername(), confirmPwd);
//        System.out.println("Password changed successfully!");
////        sc.close();
//
//    }
//
//    public boolean isCorrectPassword(String username, String password) {
//        AccountDAO accountDAO = AccountDAO.getInstance();
//        Account account = accountDAO.getAccountFromUsername(username);
//        if (Objects.equals(account.getPassword(), password)) {
//            return true;
//        }
//        return false;
//    }
//
//    public boolean isValidPassword(String password) {
//        if (password.length() < 6) return false;
//        if (password.contains(" ")) return false;
//        for (int i = 0; i < password.length(); ++i) {
//            char x = password.charAt(i);
//            if (x < 33 || x > 126) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    public void applyPasswordChange(String username, String newPass) {
//        AccountDAO accountDAO = AccountDAO.getInstance();
//        Account account = new Account(username, newPass);
//        accountDAO.changePassword(account);
//    }
//
//    //not - book borrowing should be directly
//    public void viewDocumentInfo(Document doc) {
//        // ...Code to show doc info
//        // will use getter to get info
//
//        System.out.println("Borrow the physical document? (Y/n):");
//        Scanner sc = new Scanner(System.in);
//        char cmd = (char) sc.nextByte();
//        if (cmd == 'Y' || cmd == 'y') {
//            // borrowBook(doc, user, isAdminRequest)
//            Request.borrowDocument(doc, user, false);
//        }
//    }
//
//    //not dunno
//    // Later the change will be sent as a request and will be examined manually
//    private void changePersonalInfo() {
//        Request.changePersonalInfo(user, updatedUser, false);
//    }
}
