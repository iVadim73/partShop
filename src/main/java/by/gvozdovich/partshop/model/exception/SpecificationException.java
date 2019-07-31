package by.gvozdovich.partshop.model.exception;

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
