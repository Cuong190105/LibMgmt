package org.example.libmgmt.DB;

import java.sql.Date;

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
