package pl.javaApp.library.io.database;

import pl.javaApp.library.exceptions.DataExportException;
import pl.javaApp.library.exceptions.DataImportException;
import pl.javaApp.library.model.*;

import java.sql.SQLException;
import java.util.List;

public interface DataManager {
    void importData() throws DataImportException;
    void exportData(Library library) throws DataExportException, SQLException;
    void saveBooks(List<Book> books) throws DataExportException;
    void saveMagazines(List<Magazine> magazines) throws DataExportException;
    void saveUser(LibraryUser user) throws DataExportException;
    boolean removeUser(LibraryUser user) throws DataExportException;
    boolean removePublication(Publication publication) throws DataExportException;
    List<LibraryUser> loadUsers() throws DataImportException;
    List<Publication> loadBooks() throws DataImportException;
    List<Publication> loadMagazines() throws DataImportException;
    void saveLoan(User user, Book book) throws DataExportException;
}
