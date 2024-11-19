package org.example.libmgmt.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DocumentDAO {
    private static DocumentDAO instance;
    private DocumentDAO() {};

    public static DocumentDAO getInstance() {
        if (instance == null) {
            instance = new DocumentDAO();
        }
        return instance;
    }

    //return docID for new Document
    public int addDocument(Document doc) {
        int docID = 0;
        try {
            Connection db = LibraryDB.getConnection();
            String sql = "INSERT INTO document (name, author, publisher, quantity, tags, visited, type, ISBN) "
                    + "VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement ps = db.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); // query may generate A_I key
            ps.setString(1, doc.getName());
            ps.setString(2, doc.getAuthor());
            ps.setString(3, doc.getPublisher());
            ps.setInt(4, doc.getQuantity());
            ps.setString(5, doc.getTagsString());
            ps.setInt(6, doc.getVisited());
            ps.setBoolean(7, doc.getType());
            ps.setString(8, doc.getISBN());

            int rowAffected = ps.executeUpdate();
            if (rowAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    docID = rs.getInt(1);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return docID;
    }

    public void deleteDocument(int docID) {
        try {
            Connection db = LibraryDB.getConnection();
            String sql = "DELETE FROM document where DocID = ?";
            PreparedStatement ps = db.prepareStatement(sql);
            ps.setInt(1,docID);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateDocument(Document updated) {
        try {
            Connection db = LibraryDB.getConnection();
            String sql = "UPDATE document SET name = ?, author = ?, publisher = ?, "
                    + "quantity = ?, tags = ?, visited = ?, type = ?, ISBN = ? WHERE docID = ?";

            PreparedStatement ps = db.prepareStatement(sql);
            ps.setString(1, updated.getName());
            ps.setString(2, updated.getAuthor());
            ps.setString(3, updated.getPublisher());
            ps.setInt(4, updated.getQuantity());
            ps.setString(5, updated.getTagsString());
            ps.setInt(6, updated.getVisited());
            ps.setBoolean(7, updated.getType());
            ps.setString(8, updated.getISBN());

            ps.setInt(9, updated.getDocID()); // Assuming Document has a method getId() to get the document ID

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

    public List<Document> searchDocKey(String keyword) {
        List<Document> documents = new ArrayList<>();

        try {
            Connection db = LibraryDB.getConnection();
            String sql = "SELECT * FROM document WHERE name LIKE ?";

            PreparedStatement ps = db.prepareStatement(sql);
            keyword = "%" + keyword + "%";//no 'quote'
            ps.setString(1, keyword);
//            ps.setString(2, searchKeyword);
//            ps.setString(3, searchKeyword);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Assuming Document has a constructor or setters to set fields
                Document doc = new Document();
                doc.setDocID(rs.getInt("docID"));
                doc.setISBN(rs.getString("ISBN"));
                doc.setName(rs.getString("name"));
                doc.setAuthor(rs.getString("author"));
                doc.setPublisher(rs.getString("publisher"));
                doc.setQuantity(rs.getInt("quantity"));
                doc.setTags(rs.getString("tags"));
                doc.setVisited(rs.getInt("visited"));
                doc.setType(rs.getBoolean("type"));

                documents.add(doc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return documents;
    }

    //type: (0: book) / (1: thesis)
    public List<Document> sortedList(boolean type) {
        List<Document> documents = new ArrayList<>();

        try {
            Connection db = LibraryDB.getConnection();
            String sql = "SELECT * FROM document WHERE type = ? ORDER BY DocID DESC";

            PreparedStatement ps = db.prepareStatement(sql);
            ps.setBoolean(1, type); // 0 for books, 1 for thesis, etc.

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Document doc = new Document();
                doc.setDocID(rs.getInt("docID"));
                doc.setISBN(rs.getString("ISBN"));
                doc.setName(rs.getString("name"));
                doc.setAuthor(rs.getString("author"));
                doc.setPublisher(rs.getString("publisher"));
                doc.setQuantity(rs.getInt("quantity"));
                doc.setTags(rs.getString("tags"));
                doc.setVisited(rs.getInt("visited"));
                doc.setType(rs.getBoolean("type"));

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
                doc = new Document();
                doc.setDocID(rs.getInt("DocID"));
                doc.setISBN(rs.getString("ISBN"));
                doc.setName(rs.getString("Name"));
                doc.setAuthor(rs.getString("Author"));
                doc.setPublisher(rs.getString("Publisher"));
                doc.setQuantity(rs.getInt("Quantity"));
                doc.setTags(rs.getString("Tags"));
                doc.setVisited(rs.getInt("Visited"));
                doc.setType(rs.getBoolean("Type"));
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
        return doc;
    }
}
