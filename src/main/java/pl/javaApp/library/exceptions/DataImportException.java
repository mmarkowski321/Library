package pl.javaApp.library.exceptions;

public class DataImportException extends Exception {
    public DataImportException(String message) {
        super(message);
    }

    public DataImportException(String message, Throwable cause) {
        super(message, cause);
    }
}
