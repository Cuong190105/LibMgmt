
package org.example.libmgmt.DB;

import javafx.scene.image.Image;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DocumentDAO implements Extractor<Document> {
  private static DocumentDAO instance;
  private DocumentDAO() {}

  public static DocumentDAO getInstance() {
    if (instance == null) {
      instance = new DocumentDAO();
    }
    return instance;
  }

  @Override
  public Document extract(ResultSet rs) throws SQLException {
    Document doc = new Document();
    doc.setDocID(rs.getInt("docID"));
    doc.setTitle(rs.getString("title"));
    doc.setAuthor(rs.getString("author"));
    doc.setPublisher(rs.getString("publisher"));
    doc.setQuantity(rs.getInt("quantity"));
    doc.setTags(rs.getString("tags"));
    doc.setVisited(rs.getInt("visited"));
    doc.setThesis(rs.getBoolean("thesis"));
    doc.setISBN(rs.getString("ISBN"));
    doc.setVotes(rs.getInt("votes"));
    doc.setScore(rs.getInt("Score"));
    doc.setCover(rs.getBlob("Cover"));

    //doc.setContent(rs.getBinaryStream("Content"));
    doc.setDescription(rs.getString("Description"));
    return doc;
  }


  //return docID for new Document
  public int addDocument(Document doc) {
    int docID = 0;
    try {
      Connection db = LibraryDB.getConnection();
      String sql = "INSERT INTO document (title, author, publisher, publishYear, quantity, tags, visited, thesis, ISBN, votes, score, cover, description) "
          + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
      PreparedStatement ps = db.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); // query may generate A_I key
      ps.setString(1, doc.getTitle()); //should have used index++
      ps.setString(2, doc.getAuthor());
      ps.setString(3, doc.getPublisher());
      ps.setInt(4, doc.getPublishYear());
      ps.setInt(5, doc.getQuantity());
      ps.setString(6, doc.getTagsString());
      ps.setInt(7, doc.getVisited());
      ps.setBoolean(8, doc.isThesis());
      ps.setString(9, doc.getISBN());
      ps.setInt(10, doc.getVotes());
      ps.setInt(11, doc.getScore());
      ps.setBlob(12, ImageUtil.convertImageToInputStream(doc.getCover()));
      ps.setString(13, doc.getDescription());

      int rowAffected = ps.executeUpdate();
      if (rowAffected > 0) {
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
          docID = rs.getInt(1);
          doc.setDocID(docID);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return docID;
  }

  public void deleteDocument(int docID) {
    try {
      Connection db = LibraryDB.getConnection();
      String sql = "DELETE FROM document where DocID = ?";
      PreparedStatement ps = db.prepareStatement(sql);
      ps.setInt(1, docID);
      ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void updateDocument(Document updated) {
    try {
      Connection db = LibraryDB.getConnection();
      String sql = "UPDATE document SET title = ?, author = ?, publisher = ?, publisherYear = ?, quantity = ?, "
          + "tags = ?, visited = ?, thesis = ?, ISBN = ?, votes = ?, score = ?, cover = ?, description = ? WHERE docID = ?";

      PreparedStatement ps = db.prepareStatement(sql);
      ps.setString(1, updated.getTitle());
      ps.setString(2, updated.getAuthor());
      ps.setString(3, updated.getPublisher());
      ps.setInt (4, updated.getPublishYear());
      ps.setInt(5, updated.getQuantity());
      ps.setString(6, updated.getTagsString());
      ps.setInt(7, updated.getVisited());
      ps.setBoolean(8, updated.isThesis());
      ps.setString(9, updated.getISBN());
      ps.setInt(10, updated.getVotes());
      ps.setInt(11, updated.getScore());
      ps.setBlob(12, ImageUtil.convertImageToInputStream(updated.getCover()));
      ps.setString(13, updated.getDescription());

      ps.setInt(14, updated.getDocID()); // Assuming Document has a method getId() to get the document ID

      int rowAffected = ps.executeUpdate();
      if (rowAffected > 0) {
        System.out.println("Document updated successfully.");
      } else {
        System.out.println("No document found with the provided ID.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public List<Document> searchDocKey(String keyword) throws Exception {
    List<Document> documents = new ArrayList<>();

    Connection db = LibraryDB.getConnection();
    String sql = "SELECT * FROM document WHERE title LIKE ?";

    PreparedStatement ps = db.prepareStatement(sql);
    keyword = "%" + keyword + "%";
    ps.setString(1, keyword);
//            ps.setString(2, searchKeyword);
//            ps.setString(3, searchKeyword);

    ResultSet rs = ps. executeQuery();

    while (rs.next()) {
      Document doc = extract(rs);
      documents.add(doc);
    }
    return documents;
  }

  //thesis: (0: book) / (1: thesis)
  public List<Document> sortedList(boolean thesis) {
    List<Document> documents = new ArrayList<>();

    try {
      Connection db = LibraryDB.getConnection();
      String sql = "SELECT * FROM document WHERE thesis = ? ORDER BY DocID DESC";

      PreparedStatement ps = db.prepareStatement(sql);
      ps.setBoolean(1, thesis); // 0 for books, 1 for thesis, etc.

      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        Document doc = extract(rs);
        documents.add(doc);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return documents;
  }

  /**
   * @param keyword
   * @param filter "sortBy" below
   * @param order ASC / DESC
   * @param type 0:book; 1:thesis; 2:all
   * @return List of filtered Document in "order"
   */
  public List<Document> searchDoc(String keyword, int filter, int order, int type) throws Exception {
    List<Document> documents = new ArrayList<>();
    try {
      Connection db = LibraryDB.getConnection();

      // Define column names based on filter values
      String[] sortBy = {"UID", "title", "publishYear"};
      String orderBy = (order == 1 ? "DESC" : "ASC");
      String optionalThesis = (type == 2 ? "" : "AND thesis = ?");
      // Prepare SQL query
      String sql = "SELECT * FROM document WHERE (title LIKE ? OR author LIKE ?) " + optionalThesis
              + " ORDER BY " + sortBy[filter] + " " + orderBy;

      PreparedStatement ps = db.prepareStatement(sql);
      keyword = "%" + keyword + "%"; // Wrap keyword with '%' for partial match
      ps.setString(1, keyword);
      ps.setString(2, keyword); // Specify whether to filter by thesis or normal book
      if (type != 2) {
        ps.setInt(3, type);
      }
      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        Document doc = extract(rs);
        documents.add(doc);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return documents;
  }

  public Document getDocFromID(int id) {
    Document doc = null;
    try {
      Connection db = LibraryDB.getConnection();
      String sql = "SELECT * FROM document WHERE DocID = ?";
      PreparedStatement ps = db.prepareStatement(sql);
      ps.setInt(1, id);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        doc = extract(rs);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return doc;
  }

  public void uploadContent(int id, Blob content) {
    try {
      Connection db = LibraryDB.getConnection();
      String sql = "INSERT INTO documentContent (docId, content) VALUES (?, ?)";
      PreparedStatement ps = db.prepareStatement(sql);
      ps.setInt(1, id);
      ps.setBlob(2, content);
      ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Blob getContent(int id) {
    Blob content = null;
    try {
      Connection db = LibraryDB.getConnection();
      String sql = "SELECT * FROM documentContent WHERE DocID = ?";
      PreparedStatement ps = db.prepareStatement(sql);
      ps.setInt(1, id);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        content = rs.getBlob("content");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return content;
  }
}