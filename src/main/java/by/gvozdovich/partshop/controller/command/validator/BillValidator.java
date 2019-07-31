package by.gvozdovich.partshop.controller.command.validator;

public class BillValidator {
    private static final String PRICE_REGEX = "^[\\d]{1,19}(,[\\d]{1,4})?$";

    public boolean sumValidate(String count) {
        return count.matches(PRICE_REGEX);
    }
}
