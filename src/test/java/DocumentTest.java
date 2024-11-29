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
    public void reset() throws Exception{
        docSample = new ArrayList<>();
        docSample.add(new Document("Harry Potter", "J.K.Rowling", "Bloomsbury Publishing", 10, "Fantasy,Drama,Fiction", 0, Document.BOOK));
        docSample.add(new Document("Doraemon", "Fujiko F. Fujio", "Shogakukan", 10, "Manga,Family,Comedy", 0, Document.BOOK));
        docSample.add(new Document("Cach lam giau", "M.T.Trung", "NXB Kim Dong", 10, "Life,Drama,Book", 0, Document.BOOK));

        docDAO = DocumentDAO.getInstance();
        LibraryDB.setTesting();

        Connection db = LibraryDB.getConnection();
        db.prepareStatement("DROP TABLE IF EXISTS document;").execute();

        String createTableSQL = """
                CREATE TABLE IF NOT EXISTS document (
                `DocID` int(11) PRIMARY KEY AUTO_INCREMENT,
                `Name` varchar(255) DEFAULT NULL,
                `Author` varchar(255) DEFAULT NULL,
                `Publisher` varchar(255) DEFAULT NULL,
                `Quantity` int(11) DEFAULT NULL,
                `Tags` varchar(255) DEFAULT NULL,
                `Visited` int(11) DEFAULT NULL,
                `type` tinyint(1) DEFAULT 0,
                `ISBN` varchar(50) DEFAULT NULL,
                `Votes` int(11) DEFAULT NULL,
                `Score` double DEFAULT NULL,
                `Cover` mediumblob DEFAULT NULL,
                `Content` mediumblob DEFAULT NULL,
                `Description` varchar(9999) DEFAULT NULL
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
            assertEquals(expected,actual, "Retrieved Doc not match:\n" + expected.print() + actual.print());
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
            assertEquals(expected,actual, "Retrieved Doc not match:\n" + expected.print() + actual.print());
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
    public void testSearchDocKey() {
        docSample.add(new Document("Harry Style", "Eminem", "MTV", 10, "Music", 0, Document.BOOK));
        for (Document doc: docSample) {
            doc.setDocID(docDAO.addDocument(doc));
        }

        List<Document> returnList = docDAO.searchDocKey("Harry");
        assertEquals(2, returnList.size());
        for (Document doc: returnList) {
            assertTrue(doc.getTitle().contains("Harry"), "Retrieved doc not match key word" + doc.print());
        }

        returnList = docDAO.searchDocKey("nonExist");
        assertTrue(returnList.isEmpty());
    }

    @Test
    public void testSortedList() {
        docSample.add(new Document("Harry Style", "Eminem", "MTV", 10, "Music", 0, Document.THESIS));
        docSample.add(new Document("Harry AStyle", "Eminem", "MTVVV", 10, "Musico", 0, Document.THESIS));
        for (Document doc: docSample) {
            doc.setDocID(docDAO.addDocument(doc));
        }
        List<Document> books = docDAO.sortedList(Document.BOOK);
        assertEquals(3, books.size());

        int pre = Integer.MAX_VALUE;

        for (Document book: books) {
            assertFalse(book.isThesis());
            assertTrue(book.getDocID() <= pre);
            pre = book.getDocID();
        }

        List<Document> theses = docDAO.sortedList(Document.THESIS);
        assertEquals(2, theses.size());
        assertTrue(theses.get(0).getDocID() > theses.get(1).getDocID());
    }
}
