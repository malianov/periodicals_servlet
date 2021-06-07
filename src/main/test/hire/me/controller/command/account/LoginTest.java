package hire.me.controller.command.account;

import hire.me.controller.Servlet;
import hire.me.model.entity.account.User;
import hire.me.model.entity.account.UserRole;
import hire.me.model.service.UserService;
import hire.me.model.validator.DataValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoginTest extends Mockito {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private DataValidator dataValidator;
    private UserService userService;
    private LoginCommand loginCommand;


    @Test
    public void testServlet() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("username")).thenReturn("me");
        when(request.getParameter("password")).thenReturn("secret");

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        new Servlet().doPost(request, response);

        verify(request, atLeast((1)), getParameter("username"));
        writer.flush();
        assertTrue(stringWriter.toString().contains("My expected string"));
    }


    @BeforeEach
    public void init() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dataValidator = mock(DataValidator.class);
        userService = mock(UserService.class);

//        loginCommand = new LoginCommand(userService, dataValidator);
    }

    @Test
    public void tesLoginSuccessful() {
        User user = new User();
        when(request.getParameter("login")).thenReturn("Igor");
        when(request.getParameter("password")).thenReturn("1234");
        when(request.getSession()).thenReturn(session);
        when(DataValidator.validateLogin("Igor")).thenReturn(true);
        when(DataValidator.validatePassword("1234")).thenReturn(true);
        when(userService.isPasswordCorrectForLogin("login", "password", UserRole.SUBSCRIBER)).thenReturn();
        String expected = "/WEB-INF/view/home.jsp";
        String actual = login.execute(request, response);
        assertEquals(expected, actual);
    }
}
