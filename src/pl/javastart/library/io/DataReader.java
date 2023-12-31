package pl.javastart.library.io;

import pl.javastart.library.model.Book;
import pl.javastart.library.model.LibraryUser;
import pl.javastart.library.model.Magazine;

import java.util.Scanner;

public class DataReader {
    Scanner sc = new Scanner(System.in);
    private ConsolePrinter printer;

    public DataReader(ConsolePrinter printer) {
        this.printer = printer;
    }

    public Book readAndCreateBook() {
        printer.printLine("Tytuł: ");
        String title = sc.nextLine();
        printer.printLine("Autor: ");
        String author = sc.nextLine();
        printer.printLine("Wydawnictwo: ");
        String publisher = sc.nextLine();
        printer.printLine("ISBN: ");
        String isbn = sc.nextLine();
        printer.printLine("Rok wydania: ");
        int releaseDate = getInt();
        printer.printLine("Ilość stron: ");
        int pages = getInt();

        return new Book(title, publisher, releaseDate, pages, author, isbn);
    }
    public Magazine readAndCreateMagazine() {
        printer.printLine("Tytuł: ");
        String title = sc.nextLine();
        printer.printLine("Wydawnictwo: ");
        String publisher = sc.nextLine();
        printer.printLine("Język: ");
        String language = sc.nextLine();
        printer.printLine("Rok wydania: ");
        int year = getInt();
        printer.printLine("Miesiąc: ");
        int month = getInt();
        printer.printLine("Dzień: ");
        int day = getInt();

        return new Magazine(title, publisher,year, language,  month, day);
    }
    public LibraryUser createLibraryUser(){
        printer.printLine("Wpisz imie");
        String name = sc.nextLine();
        printer.printLine("Wpisz nazwisko");
        String lastname = sc.nextLine();
        printer.printLine("Wpisz pesel");
        String pesel = sc.nextLine();
        return new LibraryUser(name,lastname,pesel);
    }
    public int getInt(){
        try{
            return sc.nextInt();
        }finally {
            sc.nextLine();
        }
    }
    public void close(){
        sc.close();
    }
    public String getString(){
        return sc.nextLine();
    }

}
