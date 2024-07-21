package pl.javaApp.library.app;

import pl.javaApp.library.exceptions.*;
import pl.javaApp.library.io.DataReader;
import pl.javaApp.library.model.*;
import pl.javaApp.library.io.ConsolePrinter;
import pl.javaApp.library.io.database.DataManager;
import pl.javaApp.library.io.database.DatabaseDataManager;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;

public class LibraryControl {

    private final ConsolePrinter printer = new ConsolePrinter();
    private final DataReader dataReader = new DataReader(printer);
    private final DataManager dataManager;

    LibraryControl() {
        dataManager = new DatabaseDataManager();
        try {
            dataManager.importData();
            printer.printLine("Zaimportowano dane z bazy danych");
        } catch (DataImportException | InvalidDataException e) {
            printer.printLine(e.getMessage());
            printer.printLine("Zainicjazowano nowa baze");
        }
    }

    public void controlLoop() {
        Option option;
        do {
            printOptions();
            option = getOption();
            switch (option) {
                case ADD_BOOK:
                    addBook();
                    break;
                case PRINT_BOOK:
                    printBooks();
                    break;
                case REMOVE_BOOK:
                    removeBook();
                    break;
                case ADD_MAGAZINE:
                    addMagazine();
                    break;
                case PRINT_MAGAZINES:
                    printMagazines();
                    break;
                case REMOVE_MAGAZINE:
                    removeMagazine();
                    break;
                case FIND_PUBLICATION_BY_TITLE:
                    findPublicationByTitle();
                    break;
                case ADD_USER:
                    addUser();
                    break;
                case PRINT_USERS:
                    printUsers();
                    break;
                case REMOVE_USER:
                    removeUser();
                    break;
                case ADD_LOAN:
                    addLoan();
                    break;
                case PRINT_LOANED_PUBLICATIONS:
                    printLoanedPublications();
                    break;
                case REMOVE_LOAN:
                    removeLoan();
                    break;
                case EXIT:
                    exit();
                    break;
                default:
                    System.out.println("Nie ma takiej opcji, wprowadz ponownie");
            }
        } while (option != Option.EXIT);
    }

    private void removeLoan() {
        // implementacja usuwania wypożyczenia
    }

    private void printLoanedPublications() {
        // implementacja wyświetlania wypożyczonych publikacji
    }

    private void addLoan() {
        // implementacja dodawania wypożyczenia
    }

    private void findPublicationByTitle() {
        printer.printLine("Wprowadz tytul ksiazki");
        String title = dataReader.getString();
        String notFoundMessage = "Brak publikacji o takim tytule";
        // implementacja wyszukiwania publikacji po tytule
    }

    private void addUser() {
        LibraryUser libraryUser = dataReader.createLibraryUser();
        try {
            dataManager.saveUser(libraryUser);
        } catch (DataExportException e) {
            printer.printLine(e.getMessage());
        }
    }

    private void printUsers() {
        try {
            List<LibraryUser> users = dataManager.loadUsers();
            printer.printUsers(users);
        } catch (DataImportException e) {
            printer.printLine(e.getMessage());
        }
    }

    private void removeUser() {
        try {
            LibraryUser libraryUser = dataReader.createLibraryUser();
            if (dataManager.removeUser(libraryUser)) {
                printer.printLine("Usunieto uzytkownika");
            } else {
                printer.printLine("Nie ma takiego uzytkownika");
            }
        } catch (InputMismatchException | DataExportException e) {
            printer.printLine("Nie udalo sie utworzyc uzytkownika, niepoprawne dane");
        }
    }

    private Option getOption() {
        boolean optionOk = false;
        Option option = null;
        while (!optionOk) {
            try {
                option = Option.createFromInt(dataReader.getInt());
                optionOk = true;
            } catch (NoSuchOptionException e) {
                printer.printLine(e.getMessage() + ", podaj ponowie");
            } catch (InputMismatchException e) {
                printer.printLine("Wprowadzono wartosc ktora nie jest liczba, podaj ponownie");
            }
        }
        return option;
    }

    private void printOptions() {
        System.out.println("Wybierz opcje:");
        for (Option option : Option.values()) {
            System.out.println(option);
        }
    }

    private void addMagazine() {
        try {
            Magazine magazine = dataReader.readAndCreateMagazine();
            dataManager.saveMagazines(List.of(magazine));
        } catch (InputMismatchException | DataExportException e) {
            printer.printLine("Nie udalo sie utworzyc magazynu, niepoprawne dane");
        }
    }

    private void printMagazines() {
        try {
            List<Publication> magazines = dataManager.loadMagazines();
            printer.printMagazines(magazines);
        } catch (DataImportException e) {
            printer.printLine(e.getMessage());
        }
    }

    private void removeMagazine() {
        try {
            Magazine magazine = dataReader.readAndCreateMagazine();
            if (dataManager.removePublication(magazine))
                printer.printLine("Usunieto magazyn");
            else
                printer.printLine("Nie znaleziono magazynu");
        } catch (InputMismatchException | DataExportException e) {
            printer.printLine("Nie udalo sie utworzyc magazynu, niepoprawne dane");
        }
    }

    private void addBook() {
        try {
            Book book = dataReader.readAndCreateBook();
            dataManager.saveBooks(List.of(book));
        } catch (InputMismatchException | DataExportException e) {
            printer.printLine("Nie udalo sie utworzyc ksiazki, niepoprawne dane");
        }
    }

    private void printBooks() {
        try {
            List<Publication> books = dataManager.loadBooks();
            printer.printBooks(books);
        } catch (DataImportException e) {
            printer.printLine(e.getMessage());
        }
    }

    private void removeBook() {
        try {
            Book book = dataReader.readAndCreateBook();
            if (dataManager.removePublication(book))
                printer.printLine("Usunieto ksiazke");
            else
                printer.printLine("Nie znaleziono ksiazki");
        } catch (InputMismatchException | DataExportException e) {
            printer.printLine("Nie udalo sie utworzyc ksiazki, niepoprawne dane");
        }
    }

    private void exit() {
        try {
            dataManager.exportData(new Library());
            printer.printLine("Export danych do bazy zakonczony powodzeniem");
        } catch (DataExportException e) {
            printer.printLine(e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Koniec programu");
        dataReader.close();
    }

    private enum Option {
        EXIT(0, "wyjscie z programu"),
        ADD_BOOK(1, "dodanie nowej ksiazki"),
        PRINT_BOOK(2, "wyswietelnie dostepnych ksiazek"),
        REMOVE_BOOK(3, "usun ksiazke"),
        ADD_MAGAZINE(4, "dodanie nowego magazynu"),
        PRINT_MAGAZINES(5, "wyswietelnie dostepnych magazynow"),
        REMOVE_MAGAZINE(6, "usun magazyn"),
        FIND_PUBLICATION_BY_TITLE(7, "znajdz ksiazke za pomoca tytulu"),
        ADD_USER(8, "dodaj czytelnika"),
        PRINT_USERS(9, "wyswietl czytelnikow"),
        REMOVE_USER(10, "usun uzytkownika"),
        ADD_LOAN(11, "wypozycz ksiazke"),
        PRINT_LOANED_PUBLICATIONS(12, "pokaz wypozyczone publikacje"),
        REMOVE_LOAN(13, "usun wypozyczonie ksiazki");


        private final int value;
        private final String description;

        Option(int option, String description) {
            this.value = option;
            this.description = description;
        }

        public int getValue() {
            return value;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return value + " - " + description;
        }

        static Option createFromInt(int option) throws NoSuchOptionException {
            try {
                return Option.values()[option];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new NoSuchOptionException("Brak opcji o id " + option);
            }
        }
    }
}
