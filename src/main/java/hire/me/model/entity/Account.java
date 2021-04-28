package hire.me.model.entity;

public abstract class Account {
    String login;
    String password;
    AccountStatus status;
    Person person;

    public Account(String login, String password, AccountStatus status, Person person) {
        this.login = login;
        this.password = password;
        this.status = status;
        this.person = person;
    }

    public Account() {
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

    public AccountStatus getAccountStatus() {
        return status;
    }

    public void setAccountStatus(AccountStatus status) {
        this.status = status;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "Account{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", person=" + person +
                '}';
    }
}

