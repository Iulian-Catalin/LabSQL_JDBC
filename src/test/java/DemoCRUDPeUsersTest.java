import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.AssertionsKt;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DemoCRUDPeUsersTest {

    @Test
    void testReadAllUsersV2() throws SQLException {
        // Arrange

        DemoCRUDPeUsers u = new DemoCRUDPeUsers();
        User expectedUser1 = new User("ion", "password1");
//        User expectedUser2 = new User("bob", "password2");
//        User expectedUser3 = new User("charlie", "password3");
        //List<User> expectedUsers = List.of(expectedUser1, expectedUser2, expectedUser3);
        List<User> expectedUsers = List.of(expectedUser1);

        // Act
        List<User> actualUsers = u.readAllUsersV2();

        // Assert
        assertEquals(expectedUsers.size(), actualUsers.size(), "The number of users is not correct.");

        for (int i = 0; i < expectedUsers.size(); i++) {
            User expectedUser = expectedUsers.get(i);
            User actualUser = actualUsers.get(i);

            assertEquals(expectedUser.getUsername(), actualUser.getUsername(), "The username of user " + (i+1) + " is not correct.");
            assertEquals(expectedUser.getPassword(), actualUser.getPassword(), "The password of user " + (i+1) + " is not correct.");
        }
    }


    @Test
    public void testInsert() throws SQLException {
        // Arrange
        User u = new User("testuser", "testpassword");
        DemoCRUDPeUsers obj = new DemoCRUDPeUsers();

        // Act
        boolean result = obj.insert(u);

        // Assert
        assertTrue(result);
    }


    @Test
    public void testUpdateFailure() throws SQLException {
        // Arrange
        User u = new User("test'use", "testpassword");
        DemoCRUDPeUsers obj = new DemoCRUDPeUsers();

        // Act
        boolean result = obj.insert(u);

        // Assert
        assertFalse(result);
    }



    @BeforeAll
    public static void setUp() throws SQLException {
        // create test data
        final String URLDB = "jdbc:postgresql://localhost:5432/grupajava";
        final String USERNAMEDB ="postgres";
        final String PWDDB ="postgres";
        Connection conn = DriverManager.getConnection(URLDB, USERNAMEDB, PWDDB);
        PreparedStatement pSt = conn.prepareStatement("insert into users(username, password) values (?, ?)");
        pSt.setString(1, "testuserupdate");
        pSt.setString(2, "testpassword");
        pSt.executeUpdate();
    }

    @Test
    public void testUpdateUser() throws SQLException {
        // given
        User testUser = new User("testuserupdate", "newpassword");
        DemoCRUDPeUsers obj = new DemoCRUDPeUsers();
        // when
        boolean result = obj.update(testUser);

        // then
        Assertions.assertTrue(result);

        // clean up
        // create test data
        final String URLDB = "jdbc:postgresql://localhost:5432/grupajava";
        final String USERNAMEDB ="postgres";
        final String PWDDB ="postgres";
        Connection conn = DriverManager.getConnection(URLDB, USERNAMEDB, PWDDB);
        PreparedStatement pSt = conn.prepareStatement("delete from users where username = ?");
        pSt.setString(1, "testuserupdate");
        pSt.executeUpdate();
    }
}