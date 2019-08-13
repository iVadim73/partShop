package by.gvozdovich.partshop.model.exception;

/**
 * The exception will be thrown from connection pool layer
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class ConnectionPoolException extends Exception{
    public ConnectionPoolException() {
        super();
    }

    public ConnectionPoolException(String message) {
        super(message);
    }

    public ConnectionPoolException(String message, Exception e) {
        super(message, e);
    }
}
