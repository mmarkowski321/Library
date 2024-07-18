package pl.javaApp.library.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LibraryUser extends User{
    private List<Publication> publicationHistory = new ArrayList<>();
    private List<Publication> borrowedPublications = new ArrayList<>();

    public LibraryUser(String name, String lastname, String pesel) {
        super(name, lastname, pesel);
    }

    public List<Publication> getPublicationHistory() {
        return publicationHistory;
    }

    public List<Publication> getBorrowedPublications() {
        return borrowedPublications;
    }

    public void addPublicationToHistory(Publication pub){
        publicationHistory.add(pub);
    }
    public void borrowPublication(Publication pub){
        borrowedPublications.add(pub);
    }
    public boolean returnPublication(Publication pub){
        boolean result = false;
        if (borrowedPublications.remove(pub)){
            result = true;
            addPublicationToHistory(pub);
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LibraryUser that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(publicationHistory, that.publicationHistory) && Objects.equals(borrowedPublications, that.borrowedPublications);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), publicationHistory, borrowedPublications);
    }

    @Override
    public String toCsv() {
        return getName() + ";" + getLastname() + ";" + getPesel();
    }

}
