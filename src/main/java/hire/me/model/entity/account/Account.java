package hire.me.model.entity.account;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Account {
    private static final Logger logger = LogManager.getLogger(Account.class);

    String login;
    String password;
    UserStatus status;
    Person person;
    UserRole userRole;

    public Account(String login, String password, UserStatus status, Person person, UserRole userRole) {
        logger.trace("constructor({},{},{},{}",login, password, status, person, userRole);
        this.login = login;
        this.password = password;
        this.status = status;
        this.person = person;
        this.userRole = userRole;
    }

    public Account() {
        logger.trace("constructor(empty)");
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

    public UserStatus getAccountStatus() {
        return status;
    }

    public void setAccountStatus(UserStatus status) {
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

