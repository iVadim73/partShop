package by.gvozdovich.partshop.controller.command.validator;

/**
 * reg ex validator for Bill
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class BillValidator {
    private static final String PRICE_REGEX = "^-?\\d{1,5}(\\.\\d{1,2})?$";

    public boolean sumValidate(String count) {
        return count.matches(PRICE_REGEX);
    }
}
