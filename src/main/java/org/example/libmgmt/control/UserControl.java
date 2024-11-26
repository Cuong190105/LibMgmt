package org.example.libmgmt.control;

import org.example.libmgmt.DB.*;

public class UserControl {
    public static final int SUCCEED = 0;
    public static final int PASSWORD_NOT_MATCHES = 1;
    public static final int USERNAME_NOT_EXISTS = 2;
    public static final int INVALID_CREDENTIALS = 3;
    public static final int COULD_NOT_CONNECT = 4;
    private static User user;
    private static Account account;

    public static int authenticate(String username, String password) {
        if (!Validator.isValidUsername(username) || !Validator.isValidPassword(password)) {
            return INVALID_CREDENTIALS;
        }
        Account acc;
        try {
            acc = AccountDAO.getInstance().getAccountFromUsername(username);
        } catch (DatabaseConnectionException e) {
            return COULD_NOT_CONNECT;
        }
        if (acc == null) {
            return USERNAME_NOT_EXISTS;
        }
        if (acc.getPassword().compareTo(password) != 0) {
            return PASSWORD_NOT_MATCHES;
        }
        user = UserDAO.getInstance().getUserFromUID(acc.getUID());
        account = acc;
        return SUCCEED;
    }

    public static User getUser() {
        return user;
    }

    public static Account getAccount() {
        return account;
    }
}
