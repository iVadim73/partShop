package by.gvozdovich.partshop.controller.command.validator;

/**
 * reg ex validator for Order
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class OrderValidator {
    private static final String ORDER_ID_REGEX = "^\\d{1,6}$";

    public boolean orderIdValidate(String orderId) {
        return orderId.matches(ORDER_ID_REGEX);
    }
}
