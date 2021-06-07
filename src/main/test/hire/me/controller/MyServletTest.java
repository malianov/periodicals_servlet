package hire.me.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class MyServletTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @BeforeAll
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFullName() throws IOException, ServletException {

        when(request.getParameter("fn")).thenReturn("Vinod");
        when(request.getParameter("ln")).thenReturn("Kashyap");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        when(response.getWriter()).thenReturn(pw);

        Servlet myServlet =new Servlet();
        myServlet.doGet(request, response);
        String result = sw.getBuffer().toString().trim();
        assertEquals(result, new String("Full Name: Vinod Kashyap"));

    }
}