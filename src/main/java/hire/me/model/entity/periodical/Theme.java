package hire.me.model.entity.periodical;

import hire.me.model.entity.language.Language;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Theme {
    private static final Logger logger = LogManager.getLogger(Theme.class);

    private long id;
    private String theme;


    public Theme(long id, String theme) {
        this.id = id;
        this.theme = theme;
    }

    public Theme() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}
