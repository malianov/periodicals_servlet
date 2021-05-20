package hire.me.model.entity.periodical;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Periodical {
    private static final Logger logger = LogManager.getLogger(Periodical.class);

    private int number;
    private String title;
    private String description;
    private long pricePerItem;
    private String theme;
    private PeriodicalStatus periodicalStatus;
    private PeriodicalType periodicalType;

    public Periodical(String title, String description, long pricePerItem, String theme, PeriodicalStatus periodicalStatus, PeriodicalType periodicalType) {
        this.title = title;
        this.description = description;
        this.pricePerItem = pricePerItem;
        this.theme = theme;
        this.periodicalStatus = periodicalStatus;
        this.periodicalType = periodicalType;
    }

    public Periodical() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getPricePerItem() {
        return pricePerItem;
    }

    public void setPricePerItem(long pricePerItem) {
        this.pricePerItem = pricePerItem;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public PeriodicalStatus getPeriodicalStatus() {
        return periodicalStatus;
    }

    public void setPeriodicalStatus(PeriodicalStatus periodicalStatus) {
        this.periodicalStatus = periodicalStatus;
    }

    public PeriodicalType getPeriodicalType() {
        return periodicalType;
    }

    public void setPeriodicalType(PeriodicalType periodicalType) {
        this.periodicalType = periodicalType;
    }

    @Override
    public String toString() {
        return "Periodical{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", pricePerItem=" + pricePerItem +
                ", theme='" + theme + '\'' +
                ", periodicalStatus=" + periodicalStatus +
                ", periodicalType=" + periodicalType +
                '}';
    }
}
