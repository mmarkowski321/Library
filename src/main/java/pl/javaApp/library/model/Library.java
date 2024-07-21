package pl.javaApp.library.model;

import pl.javaApp.library.exceptions.PublicationAlreadyExistsException;
import pl.javaApp.library.exceptions.UserAlreadyExistsException;

import java.io.Serializable;
import java.util.*;

public class Library implements Serializable {

    private Map<String, Publication> publications = new HashMap<>();


    private Map<String, LibraryUser> users = new HashMap<>();

    public Map<String, Publication> getPublications() {
        return publications;
    }

    public void setPublications(Map<String, Publication> publications) {
        this.publications = publications;
    }

    public List<LibraryUser> getUsers() {return new ArrayList<>(users.values());}
    public void setUsers(Map<String, LibraryUser> users) {
        this.users = users;
    }

    public Optional<Publication> findPublicationByBook(String title) {
        return Optional.ofNullable(publications.get(title));
    }

    public Collection<Publication> getSortedPublications(Comparator<Publication> comparator) {
        ArrayList<Publication> list = new ArrayList<>(this.publications.values());
        list.sort(comparator);
        return list;
    }

    public Collection<LibraryUser> getSortedUsers(Comparator<LibraryUser> comparator) {
        ArrayList<LibraryUser> list = new ArrayList<>(this.users.values());
        list.sort(comparator);
        return list;
    }

    public void addPublication(Publication publication) {
        if (publications.containsKey(publication.getTitle())) {
            throw new PublicationAlreadyExistsException("Publikacja o takim tytule juz istenieje " + publication.getTitle());
        }
        publications.put(publication.getTitle(), publication);
    }

    public void addUser(LibraryUser libraryUser) {
        if (users.containsKey(libraryUser.getPesel())) {
            throw new UserAlreadyExistsException("Uzytkownik o takim peselu juz istnieje " + libraryUser);
        }
        users.put(libraryUser.getPesel(), libraryUser);
    }

    public boolean removeUser(LibraryUser libraryUser) {
        if (users.containsValue(libraryUser)) {
            users.remove(libraryUser.getPesel());
            return true;
        } else {
            return false;
        }
    }

    public boolean removePublication(Publication pub) {
        if (publications.containsValue(pub)) {
            publications.remove(pub.getTitle());
            return true;
        } else {
            return false;
        }
    }
}
