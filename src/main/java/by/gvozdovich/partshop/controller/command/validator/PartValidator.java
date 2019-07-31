package by.gvozdovich.partshop.controller.command.validator;

public class PartValidator {
    private static final String CATALOG_NO_REGEX = "^[\\w\\-)( ]{2,45}$";
    private static final String INFO_REGEX = "^.{0,300}$";
    private static final String PRICE_REGEX = "^[\\d]{1,19}(,[\\d]{1,4})?$";
    private static final String WAIT_REGEX = "^\\d{1,3}$";
    private static final String DATA_REGEX = "^[\\w\\-)( ]{1,45}$";

    public boolean catalogNoValidate(String catalogNo) {
        return catalogNo.matches(CATALOG_NO_REGEX);
    }

    public boolean infoValidate(String info) {
        return info.matches(INFO_REGEX);
    }

    public boolean priceValidate(String price) {
        return price.matches(PRICE_REGEX);
    }

    public boolean waitValidate(String wait) {
        return wait.matches(WAIT_REGEX);
    }

    public boolean stockCountValidate(String stockCount) {
        return stockCount.matches(WAIT_REGEX);
    }

    public boolean dataValidate(String data) {
        return data.matches(DATA_REGEX);
    }
}
