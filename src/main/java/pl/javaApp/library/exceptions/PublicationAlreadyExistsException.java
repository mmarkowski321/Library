package pl.javaApp.library.exceptions;

public class PublicationAlreadyExistsException extends RuntimeException{
    public PublicationAlreadyExistsException(String message) {
        super(message);
    }
}
