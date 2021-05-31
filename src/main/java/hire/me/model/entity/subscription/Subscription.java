package hire.me.model.entity.subscription;

import hire.me.model.entity.account.User;
import hire.me.model.entity.periodical.Periodical;

import java.util.Objects;

public class Subscription {
    private long id;
    private User user;
    private Periodical periodical;
    private int quantity;
    private String address;

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Periodical getPeriodical() {
        return periodical;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subscription that = (Subscription) o;
        return id == that.id && quantity == that.quantity && user.equals(that.user) && periodical.equals(that.periodical) && address.equals(that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, periodical, quantity, address);
    }

    public static class Builder {
        private Subscription newSubscription;

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

        public Builder quantity(int quantity) {
            newSubscription.quantity = quantity;
            return this;
        }

        public Builder address(String address) {
            newSubscription.address = address;
            return this;
        }

        public Subscription build() {
            return newSubscription;
        }
    }
}
