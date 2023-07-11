package pl.javastart.library.app;

import pl.javastart.library.exceptions.DataExportException;
import pl.javastart.library.exceptions.DataImportException;
import pl.javastart.library.exceptions.InvalidDataException;
import pl.javastart.library.exceptions.NoSuchOptionException;
import pl.javastart.library.io.ConsolePrinter;
import pl.javastart.library.io.DataReader;
import pl.javastart.library.io.file.FileManager;
import pl.javastart.library.io.file.FileManagerBuilder;
import pl.javastart.library.model.Book;
import pl.javastart.library.model.Library;
import pl.javastart.library.model.Magazine;
import pl.javastart.library.model.Publication;

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
                case EXIT:
                    exit();
                    break;
                case ADD_MAGAZINES:
                    addMagazine();
                    break;
                case PRINT_MAGAZINES :
                    printMagazines();
                    break;
                default :
                    System.out.println("Nie ma takiej opcji, wprowadz ponownie");
            }
        }while(option != Option.EXIT);
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

    private void printMagazines() {
        Publication[] publications = library.getPublications();
        printer.printMagazines(publications);
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
    private void printBooks() {
        Publication[] publications = library.getPublications();
        printer.printBooks(publications);
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
    private enum Option {
        EXIT(0,"wyjscie z programu"),
        ADD_BOOK(1,"dodanie nowej ksiazki"),
        PRINT_BOOK(2,"wyswietelnie dostepnych ksiazek"),
        ADD_MAGAZINES(3,"dodanie nowego magazynu"),
        PRINT_MAGAZINES(4,"wyswietelnie dostepnych magazynow");
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
