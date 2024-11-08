package org.example.libmgmt.control;

public class LoginControl {
    public static final int SUCCEED = 0;
    public static final int PASSWORD_NOT_MATCHES = 1;
    public static final int USERNAME_NOT_EXISTS = 2;
    public static final int INVALID_CREDENTIALS = 3;
    public static final int COULD_NOT_CONNECT = 4;


    public static int authenticate(String username, String password) {
        int i = 0;
        for (int j = 0; j < 1000000; j++) {
            i += 2;
        }
        if (!isValidUsername(username) || !isValidPassword(password)) {
            return INVALID_CREDENTIALS;
        }
        return SUCCEED;
    }

    private static boolean isValidPassword(String password) {
//        return password.matches("\\A([!-~]+){6,}\\z}");
        return true;
    }

    private static boolean isValidUsername(String username) {
//        return username.matches("\\A([a-zA-Z0-9_.]+){6,}\\z");
        return true;
    }
}
