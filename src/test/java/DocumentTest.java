import org.example.libmgmt.DB.Document;
import org.example.libmgmt.DB.DocumentDAO;
import org.example.libmgmt.DB.LibraryDB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DocumentTest {
  private DocumentDAO docDAO;
  private List<Document> docSample;

  @BeforeEach
  public void reset() throws Exception {
    docSample = new ArrayList<>();
    docSample.add(new Document("Harry Potter", "J.K. Rowling", "Bloomsbury", 10));
    docSample.add(new Document("Doraemon", "Fujiko F. Fujio", "Shogakukan", 10));
    docSample.add(new Document("Cach lam giau", "M.T. Trung", "NXB Kim Dong", 10));

    docDAO = DocumentDAO.getInstance();
    LibraryDB.setTesting();

    Connection db = LibraryDB.getConnection();
    db.prepareStatement("DROP TABLE IF EXISTS document;").execute();

    String createTableSQL = """
            CREATE TABLE `document` (
              `DocID` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
              `Title` varchar(255) DEFAULT NULL,
              `Author` varchar(255) DEFAULT NULL,
              `Publisher` varchar(255) DEFAULT NULL,
              `PublishYear` int(11) DEFAULT NULL,
              `Quantity` int(11) DEFAULT NULL,
              `Tags` varchar(255) DEFAULT NULL,
              `Visited` int(11) DEFAULT NULL,
              `thesis` tinyint(1) DEFAULT 0,
              `ISBN` varchar(50) DEFAULT NULL,
              `Votes` int(11) DEFAULT NULL,
              `Score` double DEFAULT NULL,
              `Cover` mediumblob DEFAULT NULL,
              `Description` text DEFAULT NULL
            )""";
    db.prepareStatement(createTableSQL).execute();

    // Clear table data and reset AUTO_INCREMENT to 1
    db.prepareStatement("TRUNCATE TABLE document").execute();
    db.prepareStatement("ALTER TABLE document AUTO_INCREMENT = 1").execute();
  }

  @Test
  public void testAddAndGetDocFromID() {
    for (int i = 0; i < 3; ++i) {
      int newID = docDAO.addDocument(docSample.get(i));
      docSample.get(i).setDocID(newID);
      assertEquals(i + 1, newID, "DocID auto increment not working as intended at: " + i);
    }

    for (int i = 1; i <= 3; ++i) {
      Document actual = docDAO.getDocFromID(i);
      Document expected = docSample.get(i - 1);
      assertNotNull(actual, "Doc should not be null");
      assertEquals(expected, actual, "Retrieved Doc not match:\n" + expected.print() + actual.print());
    }
  }

  @Test
  public void testDeleteDoc() {
    for (int i = 0; i < docSample.size(); ++i) {
      int newID = docDAO.addDocument(docSample.get(i));
      docSample.get(i).setDocID(newID);
      assertEquals(i + 1, newID, "DocID auto increment not working as intended at: " + i);
    }
    docDAO.deleteDocument(3);
    Document actual = docDAO.getDocFromID(3);
    assertNull(actual, "Doc should not be found");

    for (int i = 1; i <= 2; ++i) {
      actual = docDAO.getDocFromID(i);
      Document expected = docSample.get(i - 1);
      assertNotNull(actual, "Doc should not be null");
      assertEquals(expected, actual, "Retrieved Doc not match:\n" + expected.print() + actual.print());
    }
  }

  @Test
  public void testUpdateDoc() {
    for (int i = 0; i < docSample.size(); ++i) {
      int newID = docDAO.addDocument(docSample.get(i));
      docSample.get(i).setDocID(newID);
      assertEquals(i + 1, newID, "DocID auto increment not working as intended at: " + i);
    }

    Document doc1 = docDAO.getDocFromID(1);
    doc1.setVisited(3);
    doc1.getTags().add("Comedy");
    docDAO.updateDocument(doc1);

    Document get1 = docDAO.getDocFromID(1);
    assertEquals(doc1, get1);
  }

  @Test
  public void testSearchDocKey() throws Exception {
    // Add a document with "Harry" in the title
    docSample.add(new Document("Harry Style", "Eminem", "MTV", 10));

    for (Document doc : docSample) {
      int newID = docDAO.addDocument(doc);
      doc.setDocID(newID);
    }

    // Search for documents containing "Harry"
    List<Document> resultList = docDAO.searchDocKey("Harry");
    assertEquals(2, resultList.size(), "Expected 2 documents with 'Harry' in the title");

    for (Document doc : resultList) {
      assertTrue(doc.getTitle().contains("Harry"), "Retrieved document does not match the keyword 'Harry'");
    }

    // Search for a keyword that doesn't exist
    resultList = docDAO.searchDocKey("nonExist");
    assertTrue(resultList.isEmpty(), "Expected no documents to match the keyword 'nonExist'");
  }


  @Test
  public void testSortedList() {
    // Add additional documents
    docSample.add(new Document("Harry Style", "Eminem", "MTV", 10));
    docSample.add(new Document("Harry AStyle", "Eminem", "MTVVV", 10));

    for (Document doc : docSample) {
      int newID = docDAO.addDocument(doc);
      doc.setDocID(newID);
    }

    // Get sorted list of documents (sorted by ID in ascending order)
    List<Document> sortedDocs = docDAO.sortedList(false);
    assertEquals(5, sortedDocs.size(), "Expected 5 documents in the sorted list");

    int previousID = 1000000;
    for (Document doc : sortedDocs) {
      assertTrue(doc.getDocID() < previousID, "Documents are not sorted by descending ID");
      previousID = doc.getDocID();
    }
  }

  @Test
  public void testSearchDoc() throws Exception {
    // Add sample documents to the database
    docSample.add(new Document("Advanced Java", "John Doe", "TechPress", 2020));
    docSample.add(new Document("Introduction to Algorithms", "Cormen", "MIT Press", 2019));
    docSample.add(new Document("Java for Beginners", "Jane Smith", "O'Reilly", 2021)); // Thesis

    for (Document doc : docSample) {
      int newID = docDAO.addDocument(doc);
      doc.setDocID(newID);
    }

    // Test case: Search keyword "Java", sort by title (filter = 1), ascending order (order = 0), all types (type = 2)
    List<Document> results = docDAO.searchDoc("Java", 1, 0, 2);
    assertEquals(2, results.size(), "Expected 2 documents containing 'Java'");
    assertEquals("Advanced Java", results.get(0).getTitle());
    assertEquals("Java for Beginners", results.get(1).getTitle());
  }


}