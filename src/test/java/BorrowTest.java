import org.example.libmgmt.DB.Borrow;
import org.example.libmgmt.DB.BorrowDAO;
import org.example.libmgmt.DB.LibraryDB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BorrowTest {

  private BorrowDAO borrowDAO;
  private Borrow sampleBorrow;

  @BeforeEach
  public void setUp() throws Exception {
    borrowDAO = BorrowDAO.getInstance();

    // Create a sample borrow record
    sampleBorrow = new Borrow(101, 1, Date.valueOf("2024-12-01"), Date.valueOf("2024-12-15"), Date.valueOf("2024-12-10"));

    // Set up a testing environment (like a test database or mock database)
    LibraryDB.setTesting();

    Connection db = LibraryDB.getConnection();
    db.prepareStatement("DROP TABLE IF EXISTS borrow;").execute();

    String createTableSQL = """
            CREATE TABLE `borrow` (
              `UID` int(11) NOT NULL,
              `DocID` int(11) NOT NULL,
              `BorrowingDate` date DEFAULT NULL,
              `DueDate` date DEFAULT NULL,
              `ReturnDate` date DEFAULT NULL
            )""";
    db.prepareStatement(createTableSQL).execute();

    // Clear table data and reset AUTO_INCREMENT to 1
    db.prepareStatement("TRUNCATE TABLE borrow").execute();
    db.prepareStatement("ALTER TABLE borrow AUTO_INCREMENT = 1").execute();
  }

  @Test
  public void testAddBorrow() {
    // Add a borrow record to the database
    borrowDAO.addBorrow(sampleBorrow);

    // Retrieve all borrow records
    List<Borrow> borrowList = borrowDAO.allBorrow();
    assertNotNull(borrowList);
    assertTrue(borrowList.size() > 0);

    // Check if the added borrow record is in the list
    Borrow addedBorrow = borrowList.get(0);
    assertEquals(sampleBorrow.getUID(), addedBorrow.getUID());
    assertEquals(sampleBorrow.getDocID(), addedBorrow.getDocID());
    assertEquals(sampleBorrow.getBorrowingDate(), addedBorrow.getBorrowingDate());
    assertEquals(sampleBorrow.getDueDate(), addedBorrow.getDueDate());
    assertEquals(sampleBorrow.getReturnDate(), addedBorrow.getReturnDate());
  }

  @Test
  public void testAllBorrow() {
    // Add multiple borrow records
    Borrow anotherBorrow = new Borrow(102, 1, Date.valueOf("2024-12-02"), Date.valueOf("2024-12-16"), Date.valueOf("2024-12-12"));
    borrowDAO.addBorrow(sampleBorrow);
    borrowDAO.addBorrow(anotherBorrow);

    // Retrieve all borrow records
    List<Borrow> borrowList = borrowDAO.allBorrow();
    assertNotNull(borrowList);
    assertTrue(borrowList.size() >= 2);

    // Check if the list contains the expected borrow records
    boolean hasSampleBorrow = false;
    boolean hasAnotherBorrow = false;

    for (Borrow br : borrowList) {
      if (br.getUID() == sampleBorrow.getUID()) {
        hasSampleBorrow = true;
      }
      if (br.getUID() == anotherBorrow.getUID()) {
        hasAnotherBorrow = true;
      }
    }

    assertTrue(hasSampleBorrow);
    assertTrue(hasAnotherBorrow);
  }

  @Test
  public void testBorrowHistory() {
    // Add borrow records for different users
    Borrow anotherUserBorrow = new Borrow(103, 1, Date.valueOf("2024-12-03"), Date.valueOf("2024-12-17"), Date.valueOf("2024-12-13"));
    borrowDAO.addBorrow(sampleBorrow);
    borrowDAO.addBorrow(anotherUserBorrow);

    // Retrieve borrow records for UID=101
    List<Borrow> borrowList = borrowDAO.borrowHistory(101);
    assertNotNull(borrowList);
    assertEquals(1, borrowList.size());

    // Check if the retrieved borrow record matches
    Borrow retrievedBorrow = borrowList.get(0);
    assertEquals(sampleBorrow.getUID(), retrievedBorrow.getUID());
    assertEquals(sampleBorrow.getDocID(), retrievedBorrow.getDocID());
    assertEquals(sampleBorrow.getBorrowingDate(), retrievedBorrow.getBorrowingDate());
    assertEquals(sampleBorrow.getDueDate(), retrievedBorrow.getDueDate());
    assertEquals(sampleBorrow.getReturnDate(), retrievedBorrow.getReturnDate());
  }

  @Test
  public void testDeleteBorrow() {
    // Add a borrow record
    borrowDAO.addBorrow(sampleBorrow);

    // Ensure the borrow record exists before deletion
    List<Borrow> borrowListBefore = borrowDAO.allBorrow();
    assertEquals(1, borrowListBefore.size());

    // Delete the borrow record using its UID
    borrowDAO.deleteBorrow("UID", sampleBorrow.getUID());

    // Check if the borrow record has been deleted
    List<Borrow> borrowListAfter = borrowDAO.allBorrow();
    assertEquals(0, borrowListAfter.size());
  }

}
