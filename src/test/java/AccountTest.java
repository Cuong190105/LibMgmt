
import org.example.libmgmt.DB.Account;
import org.example.libmgmt.DB.AccountDAO;
import org.example.libmgmt.DB.AccountDB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;
public class AccountTest {
    private AccountDAO accDao;

    @BeforeEach
    public void reset() throws Exception{
        accDao = AccountDAO.getInstance();
        AccountDB.setTesting();

        Connection db = AccountDB.getConnection();
        String createTableSQL = "CREATE TABLE IF NOT EXISTS account ("
                            + "UID INT,"
                            + "username VARCHAR(255) NOT NULL,"
                            + "hashedPassword VARCHAR(255) NOT NULL)";
        db.prepareStatement(createTableSQL).execute();

        // Clear table data and reset AUTO_INCREMENT to 1
        db.prepareStatement("TRUNCATE TABLE account").execute();
        db.prepareStatement("ALTER TABLE account AUTO_INCREMENT = 1").execute();
    }

    @Test
    public void testAddAccount1() throws Exception {
        Account test1 = new Account(1, "Tester1", "Password1");
        Account test2 = new Account(2, "tester2", "111111111");
        Account test3 = new Account(3, "1MASTER3", "!@#$%^&*");
        Account ret = null;

        accDao.addAccount(test1);
        accDao.addAccount(test2);
        accDao.addAccount(test3);

        Connection db = AccountDB.getConnection();
        String sql = "SELECT * FROM account ORDER BY username ASC";
        PreparedStatement ps = db.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        assertTrue(rs.next(), "Account not exist");
        ret = accDao.extractAccount(rs);
        assertEquals(test3, ret);
//        assertEquals("1MASTER3", rs.getString("username"));
//        assertEquals("!@#$%^&*", rs.getString("hashedPassword"));


        assertTrue(rs.next(), "Account not exist");
        assertEquals("Tester1", rs.getString("username"));
        assertEquals("Password1", rs.getString("hashedPassword"));

        assertTrue(rs.next(), "Account not exist");
        assertEquals("tester2", rs.getString("username"));
        assertEquals("111111111", rs.getString("hashedPassword"));

        assertFalse(rs.next(), "Redundant item");
    }

    @Test
    public void testGetAccount() {
        Account test1 = new Account(1, "Tester1", "Password1");
        accDao.addAccount(test1);
        Account ret = accDao.getAccountFromUsername("Tester1");
        assertEquals(test1, ret);
    }

    @Test
    public void testChangePassword() {
        Account test1 = new Account(1, "Tester1", "Password1");
        accDao.addAccount(test1);
        test1.setPassword("idontknow");
        accDao.changePassword(test1);
        Account ret = accDao.getAccountFromUsername("Tester1");
        assertEquals("idontknow", ret.getPassword());
    }
}
