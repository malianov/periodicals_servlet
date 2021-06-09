package hire.me.controller.command.account;

import hire.me.model.entity.account.User;
import hire.me.model.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RegisterUserTest {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private UserService userService;

    @BeforeAll
    public void init() {
//        request = mock(HttpServletRequest.class);
//        response = mock(HttpServletResponse.class);
//        checkingService = mock(InputCheckingService.class);
//        userService = mock(UserService.class);
//        registrationUser = new AddRegistrationUser(userService, checkingService);
    }

    @Test
    public void registration_successful() throws Exception {
//        User user = new User();
//        when(checkingService.checkRegistrationForm(user)).thenReturn(true);
//        when(userService.addUser(user)).thenReturn(true);
//        String page =  registrationUser.execute(request, response);
//        String expected = "/WEB-INF/view/home.jsp";
//        assertEquals(expected, page);
    }
}
