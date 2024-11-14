package org.example.libmgmt.control;

import java.time.Year;

public class Validator {
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

    public static boolean isValidPassword(String password) {
        return password.length() <= 50 && password.matches("\\A([!-~]+){8,}\\z");
    }

    public static boolean isValidUsername(String username) {
        return username.length() <= 30 && username.matches("\\A([a-zA-Z0-9_.]+){6,}\\z");
    }

    public static boolean isValidDate(String day, String month, String year) {
        String pattern = "\\A[0-9]+\\z";
        if (!day.matches(pattern) || !year.matches(pattern)) {
            return false;
        }
        int y = Integer.parseInt(year);
        if (Math.abs(Year.now().getValue() - y) > 120) {
            return false;
        }
        int limit;
        switch(Integer.parseInt(month.substring(6))) {
            case 2:
                if (y % 400 == 0 || (y % 100 != 0 && y % 4 == 0)) {
                    limit = 29;
                } else {
                    limit = 28;
                }
                break;
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                limit = 31;
                break;
            default:
                limit = 30;
        }
        return Integer.parseInt(day) <= limit;
    }
}
