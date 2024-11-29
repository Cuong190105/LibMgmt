
package org.example.libmgmt.ui.components.body;

import org.example.libmgmt.DB.Document;

public class Comment {
  private long userId;
  private long docId;
  private String date;
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
    userId = 123456789;
    date = "12/09/2024";
    rating = 4;
    comment = "SDFGHJKL";
  }

  public long getUserId() {
    return userId;
  }

  public String getDate() {
    return date;
  }

  public int getRating() {
    return rating;
  }

  public String getComment() {
    return comment;
  }
}