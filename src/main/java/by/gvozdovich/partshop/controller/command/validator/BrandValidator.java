package by.gvozdovich.partshop.controller.command.validator;

public class BrandValidator {
    private static final String NAME_REGEX = "^[\\w\\-)( ]{2,45}$";
    private static final String COUNTRY_REGEX = "^[\\w\\-)( ]{2,45}$";
    private static final String INFO_REGEX = "^.{0,300}$";
    private static final String DATA_REGEX = "^[\\w\\-)( ]{1,45}$";

    public boolean nameValidate(String name) {
        return name.matches(NAME_REGEX);
    }

    public boolean countryValidate(String country) {
        return country.matches(COUNTRY_REGEX);
    }

    public boolean infoValidate(String info) {
        return info.matches(INFO_REGEX);
    }

    public boolean dataValidate(String data) {
        return data.matches(DATA_REGEX);
    }
}
