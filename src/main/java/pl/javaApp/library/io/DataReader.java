package pl.javaApp.library.io;

import pl.javaApp.library.exceptions.InvalidDataException;
import pl.javaApp.library.model.Book;
import pl.javaApp.library.model.LibraryUser;
import pl.javaApp.library.model.Magazine;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

public class DataReader {
    Scanner sc = new Scanner(System.in);
    private final ConsolePrinter printer;
    private final LocalDate localDate = LocalDate.now();

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
        String isbn = getISBN();
        printer.printLine("Rok wydania: ");
        int releaseDate = getYear();
        printer.printLine("Ilość stron: ");
        int pages = getAmountOfPages();

        return new Book(title, publisher, releaseDate, pages, author, isbn);
    }

    public Magazine readAndCreateMagazine() {
        printer.printLine("Tytuł: ");
        String title = sc.nextLine();
        printer.printLine("Wydawnictwo: ");
        String publisher = sc.nextLine();
        printer.printLine("Język: ");
        String language = sc.nextLine();
        LocalDate date = getCorrectDate();

        return new Magazine(title, publisher, date.getYear(), language, date.getMonthValue(), date.getDayOfMonth());
    }

    public LibraryUser createLibraryUser() {
        printer.printLine("Wpisz imie: ");
        String name = sc.nextLine();
        printer.printLine("Wpisz nazwisko: ");
        String lastname = sc.nextLine();
        printer.printLine("Wpisz pesel: ");
        String pesel = getPesel();
        return new LibraryUser(name, lastname, pesel);
    }

    private int getAmountOfPages() {
        boolean correctAmountPages = true;
        int pages = 0;
        while (correctAmountPages){
            pages = getInt();
            if (pages <= 0){
                System.out.println("Niepoprawna ilość stron, spróbuj ponownie");
            }else{
                correctAmountPages = false;
            }
        }
        return pages;
    }

    private String getPesel() {
        boolean correctPesel = true;
        int lengthOfPesel = 11;
        String pesel = null;
        while (correctPesel){
            pesel = getString();
            if (pesel.length() != lengthOfPesel){
                System.out.println("Niepoprawny pesel, spróbuj jeszcze raz");
            }else{
                correctPesel = false;
            }
        }
        return pesel;
    }

    private LocalDate getCorrectDate() {
        LocalDate date = null;
        boolean correctDate = false;
        while (!correctDate) {
            printer.printLine("Rok wydania: ");
            int year = getInt();
            printer.printLine("Miesiąc: ");
            int month = getInt();
            printer.printLine("Dzień: ");
            int day = getInt();

            try {
                date = LocalDate.of(year, month, day);
                if (date.isAfter(localDate)) {
                    System.out.println("Niepoprawna data, spróbuj ponownie.");
                } else {
                    correctDate = true;
                }
            } catch (Exception e) {
                System.out.println("Niepoprawna data, spróbuj ponownie.");
            }
        }
        return date;
    }

    private int getYear() {
        while (true) {
            int year = localDate.getYear();
            int releaseDate = getInt();
            if (releaseDate > year) {
                System.out.println("Niepoprawny rok wydania, spróbuj jeszcze raz.");
            } else {
                return releaseDate;
            }
        }
    }

    private String getISBN() {
        while (true) {
            String ISBN = getString();
            if (ISBN.length() != 13) {
                System.out.println("Niepoprawny ISBN, spróbuj jeszcze raz.");
            } else {
                return ISBN;
            }
        }
    }

    public int getInt() throws InputMismatchException {
        while (true) {
            try {
                return sc.nextInt();
            } catch (InputMismatchException e) {
                sc.next();
                System.out.println("Niepoprawna wartość, spróbuj ponownie.");
            } finally {
                sc.nextLine();
            }
        }
    }

    public void close() {
        sc.close();
    }

    public String getString() {
        return sc.nextLine();
    }
}
