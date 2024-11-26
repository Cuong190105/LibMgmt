package org.example.libmgmt.control;

import java.time.Year;

public class Validator {
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
