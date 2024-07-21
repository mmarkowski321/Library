package pl.javaApp.library.model;

import java.io.Serializable;
import java.util.Objects;

public abstract class User implements Serializable{
    private String name;
    private String lastname;
    private String pesel;

    public User(String name, String lastname, String pesel) {
        this.name = name;
        this.lastname = lastname;
        this.pesel = pesel;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPesel() {
        return pesel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(name, user.name) && Objects.equals(lastname, user.lastname) && Objects.equals(pesel, user.pesel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lastname, pesel);
    }

    @Override
    public String toString() {
        return name + " " + lastname + " " + pesel;
    }
}
