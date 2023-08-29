package pl.javastart.library.io;

import pl.javastart.library.model.Book;
import pl.javastart.library.model.LibraryUser;
import pl.javastart.library.model.Magazine;
import pl.javastart.library.model.Publication;

import java.util.Collection;

public class ConsolePrinter {

    public void printMagazines(Collection<Publication> publications) {

        long count = publications.stream()
                .filter(s -> s instanceof Magazine)
                .map(Publication::toString)
                .peek(this::printLine)
                .count();


        if (count == 0) {
            printLine("Brak magazynów w bibliotece");
        }
    }
    public void printBooks(Collection<Publication> publications) {
        long count = publications.stream()
                .filter(s -> s instanceof Book)
                .map(Publication::toString)
                .peek(this::printLine)
                .count();


        if (count == 0) {
            printLine("Brak ksiazek w bibliotece");
        }
    }
    public void printLine(String text){
        System.out.println(text);
    }
    public void printUsers(Collection<LibraryUser> libraryUsers){
        libraryUsers.stream()
                .map(LibraryUser::toString)
                .forEach(System.out::println);
    }
}
