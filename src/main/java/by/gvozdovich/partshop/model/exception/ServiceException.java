package by.gvozdovich.partshop.model.exception;

/**
 * The exception will be thrown from service layer
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class ServiceException extends Exception {
    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Exception e) {
        super(message, e);
    }
}
