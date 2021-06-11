package hire.me.model.dao.impl;

import hire.me.model.entity.account.User;
import hire.me.model.entity.account.UserRole;
import hire.me.model.entity.account.UserStatus;
import hire.me.model.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JdbcUserDaoTest {
    private static long id = -1;
    private static String login = "'test'";
    private static String name = "'test'";
    private static String surname = "'test'";
    private static String email = "'email'";
    private static String password = "'1'";
    private static String user_role = UserRole.SUBSCRIBER.name();
    private static BigDecimal balance = new BigDecimal("0.0");
    private static String account_status = UserStatus.ACTIVE.name();

    @BeforeAll
    public static void setUp() throws Exception {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/myPeriodics", "root", "password");
            Statement stmt = con.createStatement();

            stmt.execute("INSERT IGNORE INTO 'myPeriodics'.'users' " +
                    "SET id = " + id +
                    ", login = " + login +
                    ", first_name = " + name +
                    ", surname = " + surname +
                    ", email = " + email +
                    ", password = " + password +
                    ", account_status = " + account_status +
                    ", user_role = " + user_role +
                    ", balance = " + balance + ";");

            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void tearDown() throws Exception {
    }

    @Test
    public void getRoleByLogin() {
        UserService userService = UserService.getInstance();
        User user = userService.getUserByLogin(login);
        UserRole userRole = userService.getRoleByLogin(login);
        assertEquals(user.getUserRole(), userRole);
    }

}
