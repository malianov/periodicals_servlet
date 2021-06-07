package hire.me.model.entity.periodical;

import hire.me.model.entity.language.Language;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Theme {
    private static final Logger logger = LogManager.getLogger(Theme.class);

    private int id;
    private int themeId;
    private String theme;
    private Language language;

    public Theme(int id, int themeId, String theme, Language language) {
        this.id = id;
        this.themeId = themeId;
        this.theme = theme;
        this.language = language;
    }

    public Theme(int themeId, String theme, Language language) {
        logger.trace("inside constructor THEME");
        this.themeId = themeId;
        this.theme = theme;
        this.language = language;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getThemeId() {
        return themeId;
    }

    public void setThemeId(int themeId) {
        this.themeId = themeId;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}
