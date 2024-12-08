package org.example.libmgmt.DB;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Using BCrypt algorithm.
 * During login, passwords are verified by rehashing the input and comparing it to the stored hash.
 */

public class PasswordUtil {
  //hash a password
  public static String hashPassword(String plainPassword) {
    return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
  }

  /**
   * check if hash(plainPassword) = hashed.
   * @param plainPassword password which user type in.
   * @param hashedPassword hashed using Bcrypt.
   */
  public static boolean checkPassword(String plainPassword, String hashedPassword) {
    return BCrypt.checkpw(plainPassword, hashedPassword);
  }
}