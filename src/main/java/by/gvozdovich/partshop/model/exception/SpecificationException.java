package by.gvozdovich.partshop.model.exception;

/**
 * The exception will be thrown from specification layer
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class SpecificationException extends Exception {
    public SpecificationException() {
        super();
    }

    public SpecificationException(String message) {
        super(message);
    }

    public SpecificationException(String message, Exception e) {
        super(message, e);
    }
}
