package pl.javaApp.library.app;

import pl.javaApp.library.exceptions.*;
import pl.javaApp.library.io.DataReader;
import pl.javaApp.library.model.*;
import pl.javaApp.library.io.ConsolePrinter;
import pl.javaApp.library.io.file.FileManager;
import pl.javaApp.library.io.file.FileManagerBuilder;



import java.util.Comparator;
import java.util.InputMismatchException;

public class LibraryControl {

    private ConsolePrinter printer = new ConsolePrinter();
    private DataReader dataReader = new DataReader(printer);
    private Library library;
    private FileManager fileManager;

    LibraryControl() {
        fileManager = new FileManagerBuilder(dataReader,printer).build();
        try{
            library = fileManager.importData();
            printer.printLine("Zaimportowano dane z pliku");
        }catch (DataImportException | InvalidDataException e){
            printer.printLine(e.getMessage());
            printer.printLine("Zainicjazowano nowa baze");
            library = new Library();
        }
    }

    public void controlLoop(){
        Option option;
        do {
            printOptions();
            option = getOption();
            switch (option){
                case ADD_BOOK:
                    addBook();
                    break;
                case PRINT_BOOK:
                    printBooks();
                    break;
                case REMOVE_BOOK:
                    removeBook();
                    break;
                case ADD_MAGAZINES:
                    addMagazine();
                    break;
                case PRINT_MAGAZINES :
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
                case EXIT:
                    exit();
                    break;
                default :
                    System.out.println("Nie ma takiej opcji, wprowadz ponownie");
            }
        }while(option != Option.EXIT);
    }

    private void findPublicationByTitle() {
        printer.printLine("Wprowadz tytul ksiazki");
        String title = dataReader.getString();
        String notFoundMessage = "Brak publikacji o takim tytule";
        library.findPublicationByBook(title)
                .map(Publication::toString)
                .ifPresentOrElse(System.out::println,() -> printer.printLine(notFoundMessage));
    }

    private void addUser() {
        LibraryUser libraryUser = dataReader.createLibraryUser();
        try {
            library.addUser(libraryUser);
        }catch (UserAlreadyExistsException e){
            printer.printLine(e.getMessage());
        }
    }

    private void printUsers() {
        printer.printUsers(library.getSortedUsers(
                Comparator.comparing(User::getLastname,String.CASE_INSENSITIVE_ORDER)
        ));
    }

    private void removeUser() {

        try{
            LibraryUser libraryUser = dataReader.createLibraryUser();
            if (library.removeUser(libraryUser)){
                printer.printLine("Usunieto uzytkownika");
            }else {
                printer.printLine("Nie ma takiego uzytkownika");
            }
        }catch (InputMismatchException e){
            printer.printLine("Nie udalo sie utworzyc uzytkownika, niepoprawne dane");
        }
    }

    private Option getOption() {
        boolean optionOk = false;
        Option option = null;
        while (!optionOk){
            try {
                option = Option.createFromInt(dataReader.getInt());
                optionOk = true;
            }catch (NoSuchOptionException e){
                printer.printLine(e.getMessage() + ",podaj ponowie");
            }catch (InputMismatchException e){
                printer.printLine("Wprowadzono wartosc ktora nie jest liczba podaj ponownie");
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
            library.addPublication(magazine);
        }catch (InputMismatchException e){
            printer.printLine("Nie udalo sie utworzyc magazynu, niepoprawne dane");
        }catch (ArrayIndexOutOfBoundsException e){
            printer.printLine("Osiagnieto limit pojemnosci, nie mozna dodac kolejnego magazynu");
        }
    }

    private void printMagazines() {
        printer.printMagazines(library.getSortedPublications(
                Comparator.comparing(Publication::getTitle,String.CASE_INSENSITIVE_ORDER)
        ));
    }

    private void removeMagazine() {
        try{
            Magazine magazine = dataReader.readAndCreateMagazine();
            if (library.removePublication(magazine))
                printer.printLine("Usunieto magazyn");
            else
                printer.printLine("Nie znaleziono magazynu");
        }catch (InputMismatchException e){
            printer.printLine("Nie udalo sie utworzyc magazynu, niepoprawne dane");
        }
    }


    private void addBook() {
        try{
            Book book = dataReader.readAndCreateBook();
            library.addPublication(book);
        }catch (InputMismatchException e){
            printer.printLine("Nie udalo sie utworzyc ksiazki niepoprawne dane");
        }catch (ArrayIndexOutOfBoundsException e){
            printer.printLine("Osiagnieto limit pojemnosci, nie mozna dodac kolejnej ksiazki");
        }
    }

    private void printBooks() {
        printer.printBooks(library.getSortedPublications(
                Comparator.comparing(Publication::getTitle,String.CASE_INSENSITIVE_ORDER)
        ));
    }
    private void removeBook() {
        try{
            Book book = dataReader.readAndCreateBook();
            if (library.removePublication(book))
                printer.printLine("Usunieto ksiazke");
            else
                printer.printLine("Nie znaleziono ksiazki");
        }catch (InputMismatchException e){
            printer.printLine("Nie udalo sie utworzyc ksiazki, niepoprawne dane");
        }
    }

    private void exit(){
        try{
            fileManager.exportData(library);
            printer.printLine("Export danych do pliku zakonczony powodzeniem");
        }catch (DataExportException e){
            printer.printLine(e.getMessage());
        }
        System.out.println("Koniec programu");
        dataReader.close();
    }

    private enum Option {
        EXIT(0,"wyjscie z programu"),
        ADD_BOOK(1,"dodanie nowej ksiazki"),
        PRINT_BOOK(2,"wyswietelnie dostepnych ksiazek"),
        REMOVE_BOOK(3,"usun ksiazke"),
        ADD_MAGAZINES(4,"dodanie nowego magazynu"),
        PRINT_MAGAZINES(5,"wyswietelnie dostepnych magazynow"),
        REMOVE_MAGAZINE(6,"usun magazyn"),
        FIND_PUBLICATION_BY_TITLE(7,"znajdz ksiazke za pomoca tytulu"),
        ADD_USER(8,"dodaj czytelnika"),
        PRINT_USERS(9,"wyswietl czytelnikow"),
        REMOVE_USER(10,"usun uzytkownika");

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
            try{
                return Option.values()[option];
            }catch (ArrayIndexOutOfBoundsException e){
                throw new NoSuchOptionException("Brak opcji o id " + option);
            }
        }
    }



}
