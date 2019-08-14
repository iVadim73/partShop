package by.gvozdovich.partshop.controller.command.validator;

/**
 * validator for User
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class UserValidator {
    private static final String ID_REGEX = "^\\d+$";
    private static final String LOGIN_REGEX = "^[\\w\\-]{3,20}$";
    private static final String PART_LOGIN_REGEX = "^[\\w\\-]{1,20}$";
    private static final String PASSWORD_REGEX = "^[\\w\\-]{6,18}$";
    private static final String EMAIL_REGEX = "^(?=.{5,254}$).{1,64}@.{3,255}$";
    private static final String PHONE_REGEX = "^(\\+?\\d{12})|(\\d{11})|(\\d{7})$";
    private static final String NAME_REGEX = "^[\\p{L}\\s\\.\\-]{2,35}$";
    private static final String DISCOUNT_REGEX = "^\\d{1,2}(\\.\\d{1,2})?$";
    private static final String STAR_REGEX = "^(10)|\\d$";
    private static final String ROLE_ID_REGEX = "^[1234]$";
    private static final String COMMENT_REGEX = "^[\\p{L}\\s\\.\\-]{0,300}$";

    public boolean idValidate(String id) {
        return id.matches(ID_REGEX);
    }

    public boolean loginValidate(String login) {
        return login.matches(LOGIN_REGEX);
    }

    public boolean partLoginValidate(String partLogin) {
        return partLogin.matches(PART_LOGIN_REGEX);
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

    public String registrationValidate(String login, String password, String email, String phoneStr, String name) {
        String result = "";
        if (!loginValidate(login)) {
            result = result + "wrong login\n";
        }
        if (!passwordValidate(password)) {
            result = result + "wrong password\n";
        }
        if (!emailValidate(email)) {
            result = result + "wrong email\n";
        }
        if (!phoneValidate(phoneStr)) {
            result = result + "wrong phone\n";
        }
        if (!nameValidate(name)) {
            result = result + "wrong name\n";
        }
        return result;
    }

    public boolean signinValidate(String login, String password) {
        return loginValidate(login) &&
                passwordValidate(password);
    }

    public boolean updateValidateForAdmin(String phone, String name, String discount, String star, String roleId, String comment) {
        return updateValidateForSeller(phone, name, discount, star, comment) &&
                roleIdValidate(roleId);
    }

    public boolean updateValidateForSeller(String phone, String name, String discount, String star, String comment) {
        return phoneValidate(phone) &&
                nameValidate(name) &&
                discountValidate(discount) &&
                starValidate(star) &&
                commentValidate(comment);
    }
}
