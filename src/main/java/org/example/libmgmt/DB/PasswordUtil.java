package org.example.libmgmt.DB;

import org.mindrot.jbcrypt.BCrypt;

import java.util.Scanner;

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

  public static void main(String[] args) {
    AccountDAO accountDAO = AccountDAO.getInstance();
    Scanner sc = new Scanner(System.in);
    System.out.println("Nhap mat khau hien tai: ");
    String oldPwd = sc.next();
    Account acc = new Account();
    acc = accountDAO.getAccountFromUID(2);
    if (!PasswordUtil.checkPassword(oldPwd, acc.getPassword())) {
      return;
    }

    System.out.println("Mat khau moi: ");
    String newPwd = sc.next();
    //Validator.isValidPassword(newPwd)
    System.out.println("Nhap lai mat khau moi: ");
    String retype = sc.next();

    acc.setPassword(newPwd);
    accountDAO.changePassword(acc);
    System.out.println("mat khau moi da duoc dat ");
  }
}