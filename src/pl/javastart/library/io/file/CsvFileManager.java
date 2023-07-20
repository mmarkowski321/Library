package pl.javastart.library.io.file;

import pl.javastart.library.exceptions.DataExportException;
import pl.javastart.library.exceptions.DataImportException;
import pl.javastart.library.exceptions.InvalidDataException;
import pl.javastart.library.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;



public class CsvFileManager implements FileManager{
    public static final String PUBLICATIONS_FILE_NAME = "Library.csv";
    public static final String USERS_FILE_NAME = "Library_users.csv";

    @Override
    public Library importData() {
        Library library = new Library();
        importPublications(library);
        importUsers(library);
        return library;
    }

    private void importUsers(Library library) {
        try(Scanner scanner = new Scanner(new File(USERS_FILE_NAME))){
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                LibraryUser libraryUser = createUserFromString(line);
                library.addUser(libraryUser);
            }
        }catch (FileNotFoundException e){
            throw new DataImportException("Brak pliku " + USERS_FILE_NAME);
        }
    }

    private void importPublications(Library library) {
        try(Scanner scanner = new Scanner(new File(PUBLICATIONS_FILE_NAME))){
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                Publication publication = createObjectFromString(line);
                library.addPublication(publication);
            }
        }catch (FileNotFoundException e){
            throw new DataImportException("Brak pliku " + PUBLICATIONS_FILE_NAME);
        }
    }

    private LibraryUser createUserFromString(String line) {
        String[] split = line.split(";");
        String name = split[0];
        String lastname = split[1];
        String pesel = split[2];
        return new LibraryUser(name,lastname,pesel);
    }

    private Publication createObjectFromString(String line) {
        String[] split = line.split(";");
        String type = split[0];
        if (Book.TYPE.equals(type)){
            return createBook(split);
        } else if (Magazine.TYPE.equals(type)) {
            return createMagazine(split);
        }
        throw new InvalidDataException("Nieznany typ publikacji " + PUBLICATIONS_FILE_NAME);
    }

    private Publication createMagazine(String[] split) {
        String title = split[1];
        String publisher = split[2];
        int year = Integer.valueOf(split[3]);
        String language = split[4];
        int month = Integer.valueOf(split[5]);
        int day = Integer.valueOf(split[6]);
        return new Magazine(title,publisher,year,language,month,day);
    }

    private Publication createBook(String[] split) {
        String title = split[1];
        String publisher = split[2];
        int year = Integer.valueOf(split[3]);
        int pages = Integer.valueOf(split[5]);
        String author = split[4];
        String isbn = split[6];
        return new Book(title,publisher,year,pages,author,isbn);
    }

    @Override
    public void exportData(Library library) {
        exportPublications(library);
        exportUsers(library);
    }

    private void exportUsers(Library library) {
        Collection<LibraryUser> users = library.getUsers().values();
        exportToCsv(users,USERS_FILE_NAME);
    }

    private void exportPublications(Library library) {
        Collection<Publication> publications = library.getPublications().values();
        exportToCsv(publications,PUBLICATIONS_FILE_NAME);
    }
    private<T extends CsvConvertible> void exportToCsv(Collection<T> collection,String filePath){
        try(FileWriter fileWriter = new FileWriter(filePath);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)){
            for (T element : collection) {
                bufferedWriter.write(element.toCsv());
                bufferedWriter.newLine();
            }
        }catch (IOException e){
            throw new DataExportException("Błąd zapisu danych do pliku " + filePath);
        }
    }
}
