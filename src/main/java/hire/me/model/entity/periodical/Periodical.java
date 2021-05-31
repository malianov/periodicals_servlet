package hire.me.model.entity.periodical;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Objects;

public class Periodical {
    private static final Logger logger = LogManager.getLogger(Periodical.class);

    private int id;
    private String title;
    private String description;
    private BigDecimal pricePerItem;
    private String theme;
    private PeriodicalStatus periodicalStatus;
    private PeriodicalType periodicalType;

    public static Logger getLogger() {
        return logger;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPricePerItem() {
        return pricePerItem;
    }

    public String getTheme() {
        return theme;
    }

    public PeriodicalStatus getPeriodicalStatus() {
        return periodicalStatus;
    }

    public PeriodicalType getPeriodicalType() {
        return periodicalType;
    }

    public static class Builder {
        private Periodical newPeriodical;

        public Builder() {
            newPeriodical = new Periodical();
        }

        public Builder id(int id) {
            newPeriodical.id = id;
            return this;
        }

        public Builder title(String title) {
            newPeriodical.title = title;
            return this;
        }

        public Builder description(String description) {
            newPeriodical.description = description;
            return this;
        }

        public Builder pricePerItem(BigDecimal pricePerItem) {
            newPeriodical.pricePerItem = pricePerItem;
            return this;
        }

        public Builder theme(String theme) {
            newPeriodical.theme = theme;
            return this;
        }

        public Builder periodicalStatus(PeriodicalStatus periodicalStatus) {
            newPeriodical.periodicalStatus = periodicalStatus;
            return this;
        }

        public Builder periodicalType(PeriodicalType periodicalType) {
            newPeriodical.periodicalType = periodicalType;
            return this;
        }

        public Periodical build() {
            return newPeriodical;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Periodical that = (Periodical) o;
        return id == that.id && title.equals(that.title) && description.equals(that.description) && pricePerItem.equals(that.pricePerItem) && theme.equals(that.theme) && periodicalStatus == that.periodicalStatus && periodicalType == that.periodicalType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, pricePerItem, theme, periodicalStatus, periodicalType);
    }
}
