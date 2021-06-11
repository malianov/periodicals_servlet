package hire.me.model.entity.subscription;

import hire.me.model.entity.account.User;
import hire.me.model.entity.periodical.Periodical;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

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
        return id;
    }

    public User getUser() {
        return user;
    }

    public Periodical getPeriodical() {
        return periodical;
    }

    public String getPeriodicItem() {
        return periodicItem;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getAddress() {
        return address;
    }

    public String getPeriodicYear() {
        return periodic_year;
    }

    public BigDecimal getItemPrice() {
        return item_price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subscription that = (Subscription) o;
        return id == that.id && Objects.equals(user, that.user) && Objects.equals(periodical, that.periodical) && Objects.equals(periodicItem, that.periodicItem) && Objects.equals(date, that.date) && Objects.equals(address, that.address) && Objects.equals(periodic_year, that.periodic_year) && Objects.equals(item_price, that.item_price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, periodical, periodicItem, date, address, periodic_year, item_price);
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + id +
                ", user=" + user +
                ", periodical=" + periodical +
                ", periodicItem='" + periodicItem + '\'' +
                ", date=" + date +
                ", address='" + address + '\'' +
                ", periodic_year='" + periodic_year + '\'' +
                ", item_price=" + item_price +
                '}';
    }

    public static class Builder {
        private final Subscription newSubscription;

        public Builder() {
            newSubscription = new Subscription();
        }

        public Builder id(long id) {
            newSubscription.id = id;
            return this;
        }

        public Builder user(User user) {
            newSubscription.user = user;
            return this;
        }

        public Builder periodical(Periodical periodical) {
            newSubscription.periodical = periodical;
            return this;
        }

        public Builder periodicItem(String periodicItem) {
            newSubscription.periodicItem = periodicItem;
            return this;
        }

        public Builder date(LocalDate date) {
            newSubscription.date = date;
            return this;
        }

        public Builder address(String address) {
            newSubscription.address = address;
            return this;
        }

        public Builder periodic_year(String periodic_year) {
            newSubscription.periodic_year = periodic_year;
            return this;
        }

        public Builder item_price(BigDecimal item_price) {
            newSubscription.item_price = item_price;
            return this;
        }

        public Subscription build() {
            logger.trace("New Periodic Subscription created");
            return newSubscription;
        }
    }
}
