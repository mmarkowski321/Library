package pl.javaApp.library.io;

import pl.javaApp.library.model.*;

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
                .map(User::toString)
                .forEach(System.out::println);
    }
}
