package hire.me.model.entity.subscription;

import hire.me.model.entity.account.User;
import hire.me.model.entity.periodical.Periodical;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Subscription {
    private static final Logger logger = LogManager.getLogger(Subscription.class);

    private long id;
    private User user;
    private Periodical periodical;
    private String periodicItem;
    private LocalDate date;
    private String address;
    private String periodic_year;
    private BigDecimal item_price;

    public long getId() {
        logger.trace("id = {}", id);
        return id;
    }

    public User getUser() {
        logger.trace("getUser = {}", user);
        return user;
    }

    public Periodical getPeriodical() {
        logger.trace("getPeriodical() = {}", periodical);
        return periodical;
    }

    public String getPeriodicItem() {
        logger.trace("getPeriodicItem()", periodicItem);
        return periodicItem;
    }

    public LocalDate getDate() {
        logger.trace("getDate() = {}", date );
        return date;
    }

    public String getAddress() {
        logger.trace("getAddress() = {}", address);
        return address;
    }

    public String getPeriodicYear() {
        return periodic_year;
    }

    public BigDecimal getItemPrice() {
        return item_price;
    }


    public static class Builder {
        private final Subscription newSubscription;

        public Builder() {
            newSubscription = new Subscription();
        }

        public Builder id(long id) {
            logger.trace("builder id {}", id);
            newSubscription.id = id;
            return this;
        }

        public Builder user(User user) {
            logger.trace("builder user {}", user);
            newSubscription.user = user;
            return this;
        }

        public Builder periodical(Periodical periodical) {
            logger.trace("builder periodical {}", periodical);
            newSubscription.periodical = periodical;
            return this;
        }

        public Builder periodicItem(String periodicItem) {
            logger.trace("builder periodicItem {}", periodicItem);
            newSubscription.periodicItem = periodicItem;
            return this;
        }

        public Builder date(LocalDate date) {
            logger.trace("builder date {}", date);
            newSubscription.date = date;
            return this;
        }

        public Builder address(String address) {
            logger.trace("builder address {}", address);
            newSubscription.address = address;
            return this;
        }

        public Builder periodic_year(String periodic_year) {
            logger.trace("builder address {}", periodic_year);
            newSubscription.periodic_year = periodic_year;
            return this;
        }

        public Builder item_price(BigDecimal item_price) {
            logger.trace("builder address {}", item_price);
            newSubscription.item_price = item_price;
            return this;
        }

        public Subscription build() {
            logger.trace("builder id  Subscription build()");
            return newSubscription;
        }
    }
}
