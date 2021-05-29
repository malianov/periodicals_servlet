package hire.me.model.entity.account;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

public class User {
    private static final Logger logger = LogManager.getLogger(User.class);

    Integer id;
    String login;
    String password;
    UserStatus status;
    Person person;
    UserRole userRole;
    BigDecimal subscriberBalance;

    public User(Integer id, String login, String password, UserStatus status, Person person, UserRole userRole, BigDecimal subscriberBalance) {
        logger.trace("constructor({}, {},{},{},{}",id, login, password, status, person, userRole, subscriberBalance);
        this.id = id;
        this.login = login;
        this.password = password;
        this.status = status;
        this.person = person;
        this.userRole = userRole;
        this.subscriberBalance = subscriberBalance;
    }

    public User(String login, String password, UserStatus status, Person person, UserRole userRole) {
        logger.trace("constructor({},{},{},{}",login, password, status, person, userRole);
        this.login = login;
        this.password = password;
        this.status = status;
        this.person = person;
        this.userRole = userRole;
    }

    public User() {
        logger.trace("constructor(empty)");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserStatus getUserStatus() {
        return status;
    }

    public void setUserStatus(UserStatus status) {
        this.status = status;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }


    public BigDecimal getSubscriberBalance() {
        return subscriberBalance;
    }

    public void setSubscriberBalance(BigDecimal balance) {
        this.subscriberBalance = subscriberBalance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", person=" + person +
                ", user role=" + userRole +
                '}';
    }
}

