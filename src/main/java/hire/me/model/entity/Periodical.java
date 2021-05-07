package hire.me.model.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Periodical {
    private static final Logger logger = LogManager.getLogger(Periodical.class);

    private String issn;
    private int number;
    private String title;
    private String description;
    private String year;
}
