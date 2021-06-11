package hire.me.model.entity.account;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public class Person {
    private static final Logger logger = LogManager.getLogger(Person.class);

    private String name;
    private String surname;
    private String email;

    public Person(String name, String surname, String email) {
        logger.trace("Object Person with name {} is created", name);
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public Person() {
        logger.trace("Empty object Person created");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return name.equals(person.name) && surname.equals(person.surname) && email.equals(person.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, email);
    }
}