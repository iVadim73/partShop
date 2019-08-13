package by.gvozdovich.partshop.model.exception;

/**
 * Representation of account transactions
 * @author Vadim Gvozdovich
 * @version 1.0
 */
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
