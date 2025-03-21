package org.example.libmgmt.DB;

import java.sql.Date;
import java.util.List;

public class Comment {
  private int UID;
  private int docID;
  private Date date;
  private int rating;
  private String comment;

  public static List<Comment> getComments(Document doc) {
    return CommentDAO.getInstance().comments(doc.getDocID());
  }

  public Comment() {
    UID = 123456789;
    date = Date.valueOf("2024-09-12");
    rating = 4;
    comment = "SDFGHJKL";
  }

  public Comment(int UID, int docID, Date date, int rating, String comment) {
    this.UID = UID;
    this.docID = docID;
    this.date = date;
    this.rating = rating;
    this.comment = comment;
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
