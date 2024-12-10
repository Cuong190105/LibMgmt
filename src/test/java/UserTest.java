
import org.example.libmgmt.DB.LibraryDB;
import org.example.libmgmt.DB.User;
import org.example.libmgmt.DB.UserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
  private UserDAO userDAO;
  private List<User> sampleUsers;

  @BeforeEach
  public void reset() throws Exception {
    sampleUsers = new ArrayList<>();
    sampleUsers.add(new User(0, "29 Cau Giay", Date.valueOf("2000-05-05"), "abs@gmail.com", "Do Trung", "Male", "0987666321", "23020085", false));
    sampleUsers.add(new User(0, "29 Xuan Thuy", Date.valueOf("2004-05-05"), "absddsd@gmail.com", "Akiko", "Female", "0987666321", "23020185", false));userDAO = UserDAO.getInstance();
    LibraryDB.setTesting();

    Connection db = LibraryDB.getConnection();
    db.prepareStatement("DROP TABLE IF EXISTS user;").execute();

    String createTableSQL = """
            CREATE TABLE `user` (
              `UID` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
              `Name` varchar(255) DEFAULT NULL,
              `Sex` varchar(10) DEFAULT NULL,
              `DOB` date DEFAULT NULL,
              `Address` text DEFAULT NULL,
              `PhoneNumber` varchar(20) DEFAULT NULL,
              `Email` varchar(255) DEFAULT NULL,
              `SSN` varchar(50) DEFAULT NULL,
              `isLibrarian` tinyint(1) DEFAULT NULL,
              `avatar` mediumblob DEFAULT NULL
            )""";

    db.prepareStatement(createTableSQL).execute();

    // Clear table data and reset AUTO_INCREMENT to 1
    db.prepareStatement("TRUNCATE TABLE user").execute();
    db.prepareStatement("ALTER TABLE user AUTO_INCREMENT = 1").execute();
  }

  @Test
  public void testAddUser() throws Exception {
    for (int i = 0; i < 2; ++i) {
      int newUID = userDAO.addUser(sampleUsers.get(i));
      sampleUsers.get(i).setUid(newUID);
      assertEquals(i + 1, newUID);
    }

    Connection db = LibraryDB.getConnection();
    String sql = "SELECT * FROM user";
    PreparedStatement ps = db.prepareStatement(sql);
    ResultSet rs = ps.executeQuery();

    for (User expected : sampleUsers) {
      assertTrue(rs.next());
      User actual = userDAO.extract(rs);
      assertEquals(expected.getName(), actual.getName());
      assertEquals(expected.getEmail(), actual.getEmail());
      assertEquals(expected.getDob(), actual.getDob());
    }
    assertFalse(rs.next());
  }


  @Test
  public void testGetUser() {
    for (int i = 0; i < 2; ++i) {
      int newUID = userDAO.addUser(sampleUsers.get(i));
      sampleUsers.get(i).setUid(newUID);
      assertEquals(i + 1, newUID);
    }

    for (int i = 1; i <= 2; ++i) {
      User actual = userDAO.getUserFromUID(i);
      User expected = sampleUsers.get(i - 1);
      assertNotNull(actual);
      assertEquals(expected.getName(), actual.getName());
      assertEquals(expected.getEmail(), actual.getEmail());
    }
  }


  @Test
  public void testDeleteUser() {
    for (int i = 0; i < 2; ++i) {
      int newUID = userDAO.addUser(sampleUsers.get(i));
      sampleUsers.get(i).setUid(newUID);
      assertEquals(i + 1, newUID);
    }
    userDAO.deleteUser(2);
    User actual = userDAO.getUserFromUID(2);
    assertNull(actual, "User should not be found");

    actual = userDAO.getUserFromUID(1);
    User expected = sampleUsers.get(0);
    assertNotNull(actual, "User should not be null");
    assertEquals(expected.getName(), actual.getName());
  }


  @Test
  public void testUpdateUser() {
    for (int i = 0; i < 2; ++i) {
      int newUID = userDAO.addUser(sampleUsers.get(i));
      sampleUsers.get(i).setUid(newUID);
      assertEquals(i + 1, newUID);
    }

    User user1 = userDAO.getUserFromUID(1);
    user1.setName("Mama Mia");
    user1.setSex("Female");
    userDAO.updateUser(user1);

    User get1 = userDAO.getUserFromUID(1);
    assertEquals(user1.getName(), get1.getName());
    assertEquals(user1.getSex(), get1.getSex());
  }

}
