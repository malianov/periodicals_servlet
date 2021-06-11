package hire.me.model.entity.periodical;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Theme theme1 = (Theme) o;
        return id == theme1.id && Objects.equals(theme, theme1.theme);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, theme);
    }

    @Override
    public String toString() {
        return "Theme{" +
                "id=" + id +
                ", theme='" + theme + '\'' +
                '}';
    }
}
