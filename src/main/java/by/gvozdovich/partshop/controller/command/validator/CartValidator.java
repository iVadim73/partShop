package by.gvozdovich.partshop.controller.command.validator;

/**
 * reg ex validator for Cart
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class CartValidator {
    private static final String COUNT_REGEX = "^\\d{1,3}$";

    public boolean countValidate(String count) {
        return count.matches(COUNT_REGEX);
    }
}
