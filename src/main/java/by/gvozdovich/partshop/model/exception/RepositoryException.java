package by.gvozdovich.partshop.model.exception;

public class RepositoryException extends Exception{
    public RepositoryException() {
        super();
    }

    public RepositoryException(String message) {
        super(message);
    }

    public RepositoryException(String message, Exception e) {
        super(message, e);
    }
}
