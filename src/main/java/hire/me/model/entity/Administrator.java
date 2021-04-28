package hire.me.model.entity;

public class Administrator extends Account {

    public Administrator() {}

    public Administrator(String login, String password, AccountStatus status, Person person) {
        super(login, password, status, person);
    }

    public boolean addPeriodical(Periodical periodical) {
        return false;
    }

    public boolean deletePeriodical(Periodical periodical) {
        return false;
    }

    public boolean modifyPeriodical(Periodical periodical) {
        return false;
    }
}
