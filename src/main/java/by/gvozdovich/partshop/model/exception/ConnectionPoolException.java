package by.gvozdovich.partshop.model.exception;

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
