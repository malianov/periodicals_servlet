package hire.me.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface Command {
    static final Logger logger = LogManager.getLogger(Command.class);

    String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
}
