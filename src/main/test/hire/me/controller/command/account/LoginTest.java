package hire.me.controller.command.account;

import hire.me.controller.command.Command;
import hire.me.model.entity.account.User;
import hire.me.model.entity.account.UserRole;
import hire.me.model.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;

import static hire.me.connection.ConnectionPool.getConnection;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginTest extends Mockito {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private LoginCommand loginCommand;
    private UserService userService;
    private User user;
    private String login;
    private String password;
    private long userId;
    private UserRole role;
    private Command LoginCommand;


    @BeforeAll
    public void setUp() throws Exception {

        Connection connection = getConnection();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        userService = mock(UserService.class);
        LoginCommand = new LoginCommand();
        user = mock(User.class);
        session = mock(HttpSession.class);
        login = "GreatReader";
        password = "123";
        userId = 14;
        role = UserRole.SUBSCRIBER;
    }

    @Test
    public void tesLoginSuccessful() throws ServletException, IOException {
        when(user.getId()).thenReturn(userId);
        when(user.getLogin()).thenReturn(login);
        when(user.getPassword()).thenReturn(password);
        when(user.getUserRole()).thenReturn(role);
        when(session.getAttribute("user_id")).thenReturn(userId);
        when(session.getAttribute("login")).thenReturn(login);
        when(session.getAttribute("role")).thenReturn(role);
        when(session.getAttribute("password")).thenReturn(password);
        when(request.getSession()).thenReturn(session);

        when(UserService.getInstance().getUserByLogin(anyString())).thenReturn(user);
        String page = loginCommand.execute(request, response);
        assertNotNull(page);
        assertEquals(page, "/app/to_home_page");

    }
}
