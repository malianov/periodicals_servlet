package hire.me.model.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NotFoundCommandException extends Exception {
    private static final Logger logger = LogManager.getLogger(NotFoundCommandException.class);
}
