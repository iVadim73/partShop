package by.gvozdovich.partshop.controller.command.validator;

public class UserValidator {
    private static final String LOGIN_REGEX = "^[\\w\\-]{3,20}$";
    private static final String PASSWORD_REGEX = "^[\\w\\-]{6,18}$";
    private static final String EMAIL_REGEX = "^(?=.{5,254}$).{1,64}@.{3,255}$";
    private static final String PHONE_REGEX = "^(\\+?\\d{12})|(\\d{11})|(\\d{7})$";
    private static final String NAME_REGEX = "^[\\w\\- ]{2,35}$";
    private static final String DISCOUNT_REGEX = "^\\d{1,2}(\\.\\d{1,2})?$";
    private static final String STAR_REGEX = "^(10)|\\d$";
    private static final String ROLE_ID_REGEX = "^[1234]$";
    private static final String COMMENT_REGEX = "^.{0,300}$";
    private static final String BILL_REGEX = "^\\d{1,5}(\\.\\d{1,2})?$";

    public boolean loginValidate(String login) {
        return login.matches(LOGIN_REGEX);
    }

    public boolean passwordValidate(String password) {
        return password.matches(PASSWORD_REGEX);
    }

    public boolean emailValidate(String email) {
        return email.matches(EMAIL_REGEX);
    }

    public boolean phoneValidate(String phone) {
        return phone.matches(PHONE_REGEX);
    }

    public boolean nameValidate(String name) {
        return name.matches(NAME_REGEX);
    }

    public boolean discountValidate(String discount) {
        return discount.matches(DISCOUNT_REGEX);
    }

    public boolean starValidate(String star) {
        return star.matches(STAR_REGEX);
    }

    public boolean roleIdValidate(String roleId) {
        return roleId.matches(ROLE_ID_REGEX);
    }

    public boolean commentValidate(String comment) {
        return comment.matches(COMMENT_REGEX);
    }

    public boolean billValidate(String bill) {
        return bill.matches(BILL_REGEX);
    }

    public boolean registrationValidate(String login, String password, String email, String phoneStr, String name) {
        return loginValidate(login) &&
                passwordValidate(password) &&
                emailValidate(email) &&
                phoneValidate(phoneStr) &&
                nameValidate(name);
    }

    public boolean signinValidate(String login, String password) {
        return loginValidate(login) &&
                passwordValidate(password);
    }

    public boolean updateValidate(String phone, String name, String discount, String star, String roleId, String comment) {
        return phoneValidate(phone) &&
                nameValidate(name) &&
                discountValidate(discount) &&
                starValidate(star) &&
                roleIdValidate(roleId) &&
                commentValidate(comment);
    }
}
