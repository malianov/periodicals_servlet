package hire.me.model.entity.periodical;

import hire.me.model.entity.language.Language;
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
    private Theme theme;
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

    public Theme getTheme() {
        return theme;
    }

    public PeriodicalStatus getPeriodicalStatus() {
        return periodicalStatus;
    }

    public PeriodicalType getPeriodicalType() {
        return periodicalType;
    }

    @Override
    public String toString() {
        return "Periodical{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", pricePerItem=" + pricePerItem +
                ", theme=" + theme.getTheme() +
                ", periodicalStatus=" + periodicalStatus.getStatus() +
                ", periodicalType=" + periodicalType.getType() +
                '}';
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

        public Builder theme(Theme theme) {
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
            logger.trace("BUILDER PERIODICAL");
            return newPeriodical;
        }
    }
}
