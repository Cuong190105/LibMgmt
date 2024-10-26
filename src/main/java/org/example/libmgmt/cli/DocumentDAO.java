package org.example.libmgmt.cli;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DocumentDAO {
    private static DocumentDAO instance;
    private DocumentDAO(){};

    public static DocumentDAO getInstance() {
        if (instance == null) {
            instance = new DocumentDAO();
        }
        return instance;
    }

    public int addDocument(Document doc) {
        int docID = 0;
        try {
            Connection db = LibraryDB.getConnection();
            String sql = "INSERT INTO librarydb.document (title, author, publisher, quantity, tags, visited) "
                    + "VALUES (?,?,?,?,?,?)";
            PreparedStatement ps = db.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); // query may generate A_I key
            ps.setString(1, doc.getTitle());
            ps.setString(2, doc.getAuthor());
            ps.setString(3, doc.getPublisher());
            ps.setInt(4, doc.getQuantity());
            ps.setString(5, doc.getTags());
            ps.setInt(6, doc.getVisited());

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
            String sql = "DELETE FROM librarydb.document where DocID = ?";
            PreparedStatement ps = db.prepareStatement(sql);
            ps.setInt(1,docID);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
