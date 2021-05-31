package hire.me.model.entity.account;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Objects;

public class User {
    private static final Logger logger = LogManager.getLogger(User.class);

    private Integer id;
    private String login;
    private String password;
    private UserStatus status;
    private Person person;
    private UserRole userRole;
    private BigDecimal subscriberBalance;

    public static Logger getLogger() {
        return logger;
    }

    public Integer getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public UserStatus getUserStatus() {
        return status;
    }

    public Person getPerson() {
        return person;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public BigDecimal getSubscriberBalance() {
        return subscriberBalance;
    }

    public static class Builder {
        private User newUser;

        public Builder() {
            newUser = new User();
        }

        public Builder id(Integer id) {
            newUser.id = id;
            return this;
        }

        public Builder login(String login) {
            newUser.login = login;
            return this;
        }
        public Builder password(String password) {
            newUser.password = password;
            return this;
        }
        public Builder userStatus(UserStatus status) {
            newUser.status = status;
            return this;
        }
        public Builder person(Person person) {
            newUser.person = person;
            return this;
        }
        public Builder role(UserRole userRole) {
            newUser.userRole = userRole;
            return this;
        }

        public Builder balance(BigDecimal balance) {
            newUser.subscriberBalance = balance;
            return this;
        }

        public User build() {
            return newUser;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) && login.equals(user.login) && password.equals(user.password) && status == user.status && person.equals(user.person) && userRole == user.userRole && subscriberBalance.equals(user.subscriberBalance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, status, person, userRole, subscriberBalance);
    }
}

