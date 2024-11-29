
package org.example.libmgmt.DB;

import java.sql.Blob;
import java.sql.Date;
import java.util.Objects;
import javafx.scene.image.Image;
import org.example.libmgmt.LibMgmt;

/**
 * An instance representing a member.
 */
public class User {
  private int uid;
  private String address;
  private Date dob;
  private String email;
  private String name;
  private String sex;
  private String phone;
  private String SSN;
  private boolean isLibrarian;
  private Image avatar;

  /**
   * Set default user object with avatar placeholder preloaded.
   */
  public User() {
    this.avatar = new Image(Objects.requireNonNull(LibMgmt.class
        .getResourceAsStream("img/accountAction/userPlaceholder.png")));
  }

  /**
   * Create a complete user.
   *
   * @param uid UserID of this user.
   * @param address Address of this user.
   * @param dob Date of birth of this user.
   * @param email Email of this user.
   * @param name Name of this user.
   * @param sex Gender of this user.
   * @param phone Phone number of this user.
   * @param SSN Social Security Number of this user.
   * @param isLibrarian User type of this user.
   */
  public User(int uid, String address, Date dob, String email, String name, String sex, String phone, String SSN, boolean isLibrarian) {
    this.uid = uid;
    this.address = address;
    this.dob = dob;
    this.email = email;
    this.name = name;
    this.sex = sex;
    this.phone = phone;
    this.SSN = SSN;
    this.isLibrarian = isLibrarian;
  }

  public int getUid() {
    return uid;
  }

  public void setUid(int uid) {
    this.uid = uid;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Date getDob() {
    return dob;
  }

  public void setDob(Date dob) {
    this.dob = dob;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getSSN() {
    return SSN;
  }

  public void setSSN(String SSN) {
    this.SSN = SSN;
  }

  public boolean isLibrarian() {
    return isLibrarian;
  }

  public void setLibrarian(boolean librarian) {
    this.isLibrarian = librarian;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    User user = (User) obj;
    return uid == user.uid &&
        Objects.equals(address, user.address) &&
        dob.equals(user.dob) &&
        Objects.equals(email, user.email) &&
        Objects.equals(name, user.name) &&
        Objects.equals(sex, user.sex) &&
        Objects.equals(phone, user.phone) &&
        Objects.equals(SSN, user.SSN) &&
        isLibrarian == user.isLibrarian;
  }

  public Image getAvatar() {
    return avatar;
  }

  public void setAvatar(Blob avatar) {
    try {
      this.avatar = new Image(avatar.getBinaryStream());
    } catch (Exception e) {
      this.avatar = new Image(LibMgmt.class.getResourceAsStream("img/accountAction/userPlaceholder.png"));
    }
  }
}