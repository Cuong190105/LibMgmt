import org.example.libmgmt.DB.Comment;
import org.example.libmgmt.DB.CommentDAO;
import org.example.libmgmt.DB.LibraryDB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CommentTest {

  private CommentDAO commentDAO;
  private Comment sampleComment;

  @BeforeEach
  public void setUp() throws Exception {
    commentDAO = CommentDAO.getInstance();

    // Create a test comment
    sampleComment = new Comment(101, 1, Date.valueOf("2024-12-10"), 5, "Great book, very informative!");

    // Set up a testing environment (like a test database or mock database)
    LibraryDB.setTesting();

    Connection db = LibraryDB.getConnection();
    db.prepareStatement("DROP TABLE IF EXISTS comment;").execute();

    String createTableSQL = """
            CREATE TABLE `comment` (
              `UID` int(11) NOT NULL,
              `DocID` int(11) NOT NULL,
              `Content` text DEFAULT NULL,
              `Rating` int(11) DEFAULT NULL,
              `Date` date DEFAULT NULL
            )""";
    db.prepareStatement(createTableSQL).execute();

    // Clear table data and reset AUTO_INCREMENT to 1
    db.prepareStatement("TRUNCATE TABLE comment").execute();
    db.prepareStatement("ALTER TABLE comment AUTO_INCREMENT = 1").execute();
  }

  @Test
  public void testAddComment() {
    // Add a comment to the database
    commentDAO.addComment(sampleComment);

    // Retrieve the comments for the document with docID=1
    List<Comment> commentList = commentDAO.comments(1);
    assertNotNull(commentList);
    assertTrue(commentList.size() > 0);

    // Check if the added comment is in the list
    Comment addedComment = commentList.get(0);
    assertEquals(sampleComment.getUID(), addedComment.getUID());
    assertEquals(sampleComment.getDocID(), addedComment.getDocID());
    assertEquals(sampleComment.getComment(), addedComment.getComment());
    assertEquals(sampleComment.getRating(), addedComment.getRating());
    assertEquals(sampleComment.getDate(), addedComment.getDate());
  }

  @Test
  public void testComments() {
    // Add multiple comments
    Comment anotherComment = new Comment(102, 1, Date.valueOf("2024-12-11"), 4, "Very useful information, thanks!");
    commentDAO.addComment(sampleComment);
    commentDAO.addComment(anotherComment);

    // Retrieve comments for the document with docID=1
    List<Comment> commentList = commentDAO.comments(1);
    assertNotNull(commentList);
    assertTrue(commentList.size() >= 2);

    // Check if the list contains the expected comments
    boolean hasSampleComment = false;
    boolean hasAnotherComment = false;

    for (Comment cmt : commentList) {
      if (cmt.getUID() == sampleComment.getUID()) {
        hasSampleComment = true;
      }
      if (cmt.getUID() == anotherComment.getUID()) {
        hasAnotherComment = true;
      }
    }

    assertTrue(hasSampleComment);
    assertTrue(hasAnotherComment);
  }

  @Test
  public void testDeleteComment() {
    // Add a comment to the database
    commentDAO.addComment(sampleComment);

    // Ensure the comment exists before deletion
    List<Comment> commentListBefore = commentDAO.comments(1);
    assertEquals(1, commentListBefore.size());

    // Delete the comment using its UID
    commentDAO.deleteComment("UID", sampleComment.getUID());

    // Check if the comment has been deleted
    List<Comment> commentListAfter = commentDAO.comments(1);
    assertEquals(0, commentListAfter.size());
  }


}
