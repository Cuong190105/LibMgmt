package org.example.libmgmt.DB;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class Borrow {
  private int UID, docID;
  private Date borrowingDate, returnDate, dueDate;

  public Borrow() {
  }

  public Borrow(int UID, int docID, Date borrowingDate, Date returnDate, Date dueDate) {
    this.UID = UID;
    this.docID = docID;
    this.borrowingDate = borrowingDate;
    this.returnDate = returnDate;
    this.dueDate = dueDate;
  }

  /**
   * Used for requesting checkout.
   *
   * @param UID
   * @param docID
   */
  public Borrow(int UID, int docID) {
    this.UID = UID;
    this.docID = docID;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDateTime now = LocalDateTime.now();
    String date = dtf.format(now);
    Date today = Date.valueOf(date);
    this.borrowingDate = today;
    Calendar c = Calendar.getInstance();
    c.setTime(today);
    c.add(Calendar.DATE, 14);
    this.dueDate = new Date(c.getTime().getTime());
  }

  public int getUID() {
    return UID;
  }

  public void setUID(int UID) {
    this.UID = UID;
  }

  public int getDocID() {
    return docID;
  }

  public void setDocID(int docID) {
    this.docID = docID;
  }

  public Date getBorrowingDate() {
    return borrowingDate;
  }

  public void setBorrowingDate(Date borrowingDate) {
    this.borrowingDate = borrowingDate;
  }

  public Date getReturnDate() {
    return returnDate;
  }

  public void setReturnDate(Date returnDate) {
    this.returnDate = returnDate;
  }

  public Date getDueDate() {
    return dueDate;
  }

  public void setDueDate(Date dueDate) {
    this.dueDate = dueDate;
  }

}
