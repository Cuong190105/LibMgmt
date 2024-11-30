
package org.example.libmgmt.DB;

import java.io.InputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javafx.scene.image.Image;
import org.example.libmgmt.LibMgmt;

/**
 * An instance storing a document meta info.
 */
public class Document {
  private int docID;
  private Image cover;
  private String title;
  private String author;
  private String publisher;
  private int quantity;
  private List<String> tags;
  private int visited;
  private int publishYear;
  private boolean thesis;
  private String description;
  private String ISBN;
  private int votes;
  private int score;

  /**
   * Default constructor with cover placeholder preloaded.
   */
  public Document() {
    this.cover = new Image(Objects.requireNonNull(LibMgmt.class
        .getResourceAsStream("img/bookCoverPlaceholder.png")));
  }

  /**
   * Complete constructor.
   *
   * @param docID Sets document ID.
   * @param cover Sets cover.
   * @param title Sets title.
   * @param author Sets author.
   * @param publisher Sets publisher.
   * @param quantity Sets quantity.
   * @param tags Set tags.
   * @param visited Set visited times.
   * @param publishYear Sets publish year.
   * @param thesis Sets document type: true for thesis, false for book
   * @param description Sets description.
   * @param ISBN Sets ISBN
   * @param votes Sets votes.
   * @param score Sets critics score.
   */
  public Document(int docID, Image cover, String title, String author, String publisher,
                  int quantity, List<String> tags, int visited, int publishYear,
                  boolean thesis, String description, String ISBN, int votes, int score) {
    this.docID = docID;
    try {
      this.cover = cover;
    } catch (Exception e) {
      this.cover = new Image(Objects.requireNonNull(LibMgmt.class
          .getResourceAsStream("img/bookCoverPlaceholder.png")));
      throw e;
    }
    this.title = title;
    this.author = author;
    this.publisher = publisher;
    this.quantity = quantity;
    this.tags = tags;
    this.visited = visited;
    this.publishYear = publishYear;
    this.thesis = thesis;
    this.description = description;
    this.ISBN = ISBN;
    this.votes = votes;
    this.score = score;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public void setVisited(int visited) {
    this.visited = visited;
  }

  public int getPublishYear() {
    return publishYear;
  }

  public void setPublishYear(int publishYear) {
    this.publishYear = publishYear;
  }

  public boolean isThesis() {
    return thesis;
  }

  public void setThesis(boolean thesis) {
    this.thesis = thesis;
  }

  public void setDocID(int docID) {
    this.docID = docID;
  }

  public int getDocID() {
    return docID;
  }

  public String getTitle() {
    return title;
  }

  public String getAuthor() {
    return author;
  }

  public String getPublisher() {
    return publisher;
  }

  public int getQuantity() {
    return quantity;
  }

  public int getVisited() {
    return visited;
  }

  public String getISBN() {
    return ISBN;
  }

  public void setISBN(String ISBN) {
    this.ISBN = ISBN;
  }

  public List<String> getTags() {
    return tags;
  }

  public Image getCover() {
    return cover;
  }

  /**
   * Set document cover.
   *
   * @param cover Sets document cover.
   */
  public void setCover(Image cover) {
    this.cover = cover;
  }

  public void setCover(Blob cover) {
    try {
      this.cover = new Image(cover.getBinaryStream());
    } catch (Exception e) {
      this.cover = new Image(LibMgmt.class.getResourceAsStream("img/bookCoverPlaceholder.png"));
    }
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  public void setTags(String tags) {
    this.tags = new ArrayList<>();
    if (tags != null && !tags.isEmpty()) {
      Collections.addAll(this.tags, tags.split(","));
    }
  }

  public String getTagsString() {
    String ans = "";
    for (int i = 0; i < tags.size(); ++i) {
      ans += tags.get(i) + (i + 1 != tags.size() ? ",":"");
    }
    return ans;
  }

  public int getVotes() {
    return votes;
  }

  public void setVotes(int votes) {
    this.votes = votes;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Document doc = (Document) obj;
    return this.docID == doc.docID &&
        Objects.equals(this.title, doc.title) &&
        Objects.equals(this.author, doc.author) &&
        Objects.equals(this.publisher, doc.publisher) &&
        this.quantity == doc.quantity &&
        Objects.equals(this.tags, doc.tags) &&
        this.visited == doc.visited &&
        this.thesis == doc.thesis;
  }

  public String print() {
    return " " + docID + "  " + title + "  " + author + "  "
        + publisher + "  " + quantity + "  " + tags + "  "
        + visited + "  " + thesis + "\n";
  }

  /**
   * Get average rating of this document.
   */
  public double getRating() {
    return score * 1.0 / votes;
  }
}