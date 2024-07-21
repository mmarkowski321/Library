package pl.javaApp.library.io.database;

import pl.javaApp.library.io.database.DataManager;
import pl.javaApp.library.exceptions.DataExportException;
import pl.javaApp.library.exceptions.DataImportException;
import pl.javaApp.library.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DatabaseDataManager implements DataManager {

    @Override
    public void importData() throws DataImportException {
        try {
            loadPublications();
            loadUsers();
        } catch (Exception e) {
            throw new DataImportException("Error loading data from database", e);
        }
    }


    @Override
    public void exportData(Library library) throws SQLException {
        try {
            savePublications(library.getPublications());
            saveUsers(library.getUsers());
        } catch (SQLException e) {
            throw new SQLException();

        }
    }

    private void saveUsers(List<LibraryUser> users) {

    }

    private void savePublications(Map<String, Publication> publications) throws SQLException {
        for (Publication publication : publications.values()) {
            if (publication instanceof Book) {
                saveBook((Book) publication);
            } else if (publication instanceof Magazine) {
                saveMagazine((Magazine) publication);
            }
        }
    }

    @Override
    public void saveBooks(List<Book> books) throws DataExportException {
        try {
            for (Book book : books) {
                saveBook(book);
            }
        } catch (SQLException e) {
            throw new DataExportException("Error saving books to database", e);
        }
    }

    @Override
    public void saveMagazines(List<Magazine> magazines) throws DataExportException {
        try {
            for (Magazine magazine : magazines) {
                saveMagazine(magazine);
            }
        } catch (SQLException e) {
            throw new DataExportException("Error saving magazines to database", e);
        }
    }

    @Override
    public List<Publication> loadBooks() throws DataImportException {
        List<Publication> books = new ArrayList<>();
        String query = "SELECT title, publisher, year, pages, author, isbn FROM books";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String title = rs.getString("title");
                String publisher = rs.getString("publisher");
                int year = rs.getInt("year");
                int pages = rs.getInt("pages");
                String author = rs.getString("author");
                String isbn = rs.getString("isbn");
                books.add(new Book(title, publisher, year, pages, author, isbn));
            }
        } catch (SQLException e) {
            throw new DataImportException("Error loading books from database", e);
        }
        return books;
    }

    @Override
    public List<Publication> loadMagazines() throws DataImportException {
        List<Publication> magazines = new ArrayList<>();
        String query = "SELECT title, publisher, year, language, month, day FROM magazines";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String title = rs.getString("title");
                String publisher = rs.getString("publisher");
                int year = rs.getInt("year");
                String language = rs.getString("language");
                int month = rs.getInt("month");
                int day = rs.getInt("day");
                magazines.add(new Magazine(title, publisher, year, language, month, day));
            }
        } catch (SQLException e) {
            throw new DataImportException("Error loading magazines from database", e);
        }
        return magazines;
    }

    @Override
    public List<LibraryUser> loadUsers() throws DataImportException {
        List<LibraryUser> users = new ArrayList<>();
        String query = "SELECT name, lastname, pesel FROM users";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String name = rs.getString("name");
                String lastname = rs.getString("lastname");
                String pesel = rs.getString("pesel");
                users.add(new LibraryUser(name, lastname, pesel));
            }
        } catch (SQLException e) {
            throw new DataImportException("Error loading users from database", e);
        }
        return users;
    }

    @Override
    public void saveLoan(User user, Book book) throws DataExportException {
        String query = "INSERT INTO loans (user_name, user_lastname, user_pesel, book_title, loan_date) VALUES (?, ?, ?, ?, CURDATE())";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getLastname());
            pstmt.setString(3, user.getPesel());
            pstmt.setString(4, book.getTitle());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataExportException("Error saving loan to database", e);
        }
    }

    private void loadPublications() throws SQLException {
        String queryBooks = "SELECT title, publisher, year, pages, author, isbn FROM books";
        String queryMagazines = "SELECT title, publisher, year, language, month, day FROM magazines";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmtBooks = conn.prepareStatement(queryBooks);
             PreparedStatement pstmtMagazines = conn.prepareStatement(queryMagazines);
             ResultSet rsBooks = pstmtBooks.executeQuery();
             ResultSet rsMagazines = pstmtMagazines.executeQuery()) {

            while (rsBooks.next()) {
                String title = rsBooks.getString("title");
                String publisher = rsBooks.getString("publisher");
                int year = rsBooks.getInt("year");
                int pages = rsBooks.getInt("pages");
                String author = rsBooks.getString("author");
                String isbn = rsBooks.getString("isbn");
                // Dodaj książkę do kolekcji
            }

            while (rsMagazines.next()) {
                String title = rsMagazines.getString("title");
                String publisher = rsMagazines.getString("publisher");
                int year = rsMagazines.getInt("year");
                String language = rsMagazines.getString("language");
                int month = rsMagazines.getInt("month");
                int day = rsMagazines.getInt("day");
                // Dodaj magazyn do kolekcji
            }
        }
    }

    private void saveBook(Book book) throws SQLException {
        String query = "INSERT INTO books (title, publisher, year, pages, author, isbn) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getPublisher());
            pstmt.setInt(3, book.getYear());
            pstmt.setInt(4, book.getPages());
            pstmt.setString(5, book.getAuthor());
            pstmt.setString(6, book.getIsbn());
            pstmt.executeUpdate();
        }
    }
    private void saveMagazine(Magazine magazine) throws SQLException {
        String query = "INSERT INTO magazines (title, publisher, year, language, month, day) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, magazine.getTitle());
            pstmt.setString(2, magazine.getPublisher());
            pstmt.setInt(3, magazine.getYear());
            pstmt.setString(4, magazine.getLanguage());
            pstmt.setInt(5, magazine.getMonthDay().getMonthValue());
            pstmt.setInt(6, magazine.getMonthDay().getDayOfMonth());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void saveUser(LibraryUser user) throws DataExportException {
        String query = "INSERT INTO users (name, lastname, pesel) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getLastname());
            pstmt.setString(3, user.getPesel());
            pstmt.addBatch();

            pstmt.executeBatch();
        } catch (SQLException e) {
            throw new DataExportException("Error saving users to database", e);
        }
    }

    @Override
    public boolean removeUser(LibraryUser user) throws DataExportException {
        String query = "DELETE FROM users WHERE pesel = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, user.getPesel());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new DataExportException("Error removing user from database", e);
        }
    }

    @Override
    public boolean removePublication(Publication publication) throws DataExportException {
        String query = "DELETE FROM " + (publication instanceof Book ? "books" : "magazines") + " WHERE title = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, publication.getTitle());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new DataExportException("Error removing publication from database", e);
        }
    }
}
