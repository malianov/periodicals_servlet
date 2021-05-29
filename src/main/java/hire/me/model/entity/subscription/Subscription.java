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

    public Subscription(long id, User user, Periodical periodical, int quantity, String address) {
        this.id = id;
        this.user = user;
        this.periodical = periodical;
        this.quantity = quantity;
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Periodical getPeriodical() {
        return periodical;
    }

    public void setPeriodical(Periodical periodical) {
        this.periodical = periodical;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + id +
                ", user=" + user +
                ", periodical=" + periodical +
                ", quantity=" + quantity +
                ", address='" + address + '\'' +
                '}';
    }
}
