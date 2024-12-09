package org.example.libmgmt.DB;

import java.sql.Date;

public class Comment {
  private int UID;
  private int docID;
  private Date date;
  private int rating;
  private String comment;

  public static Comment[] getComments(Document doc, int commentsLoaded) {
    Comment[] cmtList = new Comment[20];
    for (int i = 0; i < 20; i++) {
      cmtList[i] = new Comment();
    }
    return cmtList;
  }

  public Comment() {
    UID = 123456789;
    date = Date.valueOf("2024-09-12");
    rating = 4;
    comment = "SDFGHJKL";
  }

  public int getDocID() {
    return docID;
  }

  public int getUID() {
    return UID;
  }

  public String getDate() {
    return date.toString();
  }

  public int getRating() {
    return rating;
  }

  public String getComment() {
    return comment;
  }

  public void setUID(int UID) {
    this.UID = UID;
  }

  public void setDocID(int docID) {
    this.docID = docID;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }
}
