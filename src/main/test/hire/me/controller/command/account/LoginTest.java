package hire.me.controller.command.account;

import hire.me.model.entity.account.User;
import hire.me.model.service.UserService;
import hire.me.model.validator.DataValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoginTest {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private DataValidator dataValidator;
    private UserService userService;
    private LoginCommand loginCommand;

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
        when(request.getParameter("email")).thenReturn("igor@kr.net");
        when(request.getParameter("password")).thenReturn("igor12345678");
        when(request.getSession()).thenReturn(session);
       /* when(DataValidator.validateLogin("egor123@mail", "egor123")).thenReturn(true);
        when(userService.login("igor@kr.net", "egor123")).thenReturn(Optional.of(user));
        String expected = "/WEB-INF/view/home.jsp";
        String actual = login.execute(request, response);*/
//        assertEquals(expected, actual);
    }
}
