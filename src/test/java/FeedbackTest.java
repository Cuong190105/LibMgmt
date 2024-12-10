import org.example.libmgmt.DB.Feedback;
import org.example.libmgmt.DB.FeedbackDAO;
import org.example.libmgmt.DB.LibraryDB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FeedbackTest {

  private FeedbackDAO feedbackDAO;
  private Feedback sampleFeedback;

  @BeforeEach
  public void setUp() throws Exception {
    feedbackDAO = FeedbackDAO.getInstance();

    // Create a test feedback
    sampleFeedback = new Feedback(123, Date.valueOf("2024-12-10"), "John Doe",
            "Great product, easy to use.", true);

    // Set up a testing environment (like a test database or mock database)
    LibraryDB.setTesting();

    Connection db = LibraryDB.getConnection();
    db.prepareStatement("DROP TABLE IF EXISTS feedback;").execute();

    String createTableSQL = """
            CREATE TABLE `feedback` (
               `number` int(11) NOT NULL,
               `date` date DEFAULT NULL,
               `sender` varchar(255) DEFAULT NULL,
               `content` varchar(9999) DEFAULT NULL,
               `important` tinyint(1) DEFAULT NULL
            )""";
    db.prepareStatement(createTableSQL).execute();

    // Clear table data and reset AUTO_INCREMENT to 1
    db.prepareStatement("TRUNCATE TABLE feedback").execute();
    db.prepareStatement("ALTER TABLE feedback AUTO_INCREMENT = 1").execute();
  }

  @Test
  public void testAddFeedback() {
    // Add feedback to the database
    feedbackDAO.addFeedback(sampleFeedback);

    // Retrieve all feedback from the database and check if the sample feedback was added
    List<Feedback> feedbackList = feedbackDAO.allFeedback();
    assertNotNull(feedbackList);
    assertTrue(feedbackList.size() > 0);

    // Check if the added feedback is in the list
    Feedback addedFeedback = feedbackList.get(0);
    assertEquals(sampleFeedback.getNumber(), addedFeedback.getNumber());
    assertEquals(sampleFeedback.getSender(), addedFeedback.getSender());
    assertEquals(sampleFeedback.getContent(), addedFeedback.getContent());
    assertEquals(sampleFeedback.isImportant(), addedFeedback.isImportant());
  }

  @Test
  public void testAllFeedback() {
    // Add multiple feedback entries
    Feedback anotherFeedback = new Feedback(124, Date.valueOf("2024-12-11"), "Jane Doe",
            "I like the new features.", false);
    feedbackDAO.addFeedback(sampleFeedback);
    feedbackDAO.addFeedback(anotherFeedback);

    // Retrieve all feedback from the database
    List<Feedback> feedbackList = feedbackDAO.allFeedback();
    assertNotNull(feedbackList);
    assertTrue(feedbackList.size() >= 2);

    // Check if the list contains the expected feedbacks
    boolean hasSampleFeedback = false;
    boolean hasAnotherFeedback = false;

    for (Feedback fb : feedbackList) {
      if (fb.getNumber() == sampleFeedback.getNumber()) {
        hasSampleFeedback = true;
      }
      if (fb.getNumber() == anotherFeedback.getNumber()) {
        hasAnotherFeedback = true;
      }
    }

    assertTrue(hasSampleFeedback);
    assertTrue(hasAnotherFeedback);
  }

  @Test
  public void testDeleteFeedback() {
    // Add feedback to the database
    feedbackDAO.addFeedback(sampleFeedback);

    // Ensure the feedback exists before deletion
    List<Feedback> feedbackListBefore = feedbackDAO.allFeedback();
    assertEquals(1, feedbackListBefore.size());

    // Delete the feedback using its number
    feedbackDAO.deleteFeedback("number", sampleFeedback.getNumber());

    // Check if the feedback has been deleted
    List<Feedback> feedbackListAfter = feedbackDAO.allFeedback();
    assertEquals(0, feedbackListAfter.size());
  }
}
