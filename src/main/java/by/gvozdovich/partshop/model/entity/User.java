package by.gvozdovich.partshop.model.entity;

import javax.xml.bind.DatatypeConverter;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Objects;
import java.security.MessageDigest;
import java.util.ResourceBundle;

public class User implements DbEntity {
    private int userId;
    private String login;
    private String password;
    private String email;
    private long phone;
    private String name;
    private LocalDate registrationDate;
    private double discount = 0.0;
    private int star = 10;
    private String comment;
    private BigDecimal bill = BigDecimal.ZERO;
    private Role role;
    private static final String MD5 = "MD5";
    private boolean isActive;

    private User() {

    }

    public static String hashPassword (String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(MD5);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace(); // FIXME: 2019-07-09
        }
        md.update(password.getBytes());
        String md5Pass = DatatypeConverter.printHexBinary(md.digest());
        ResourceBundle resourceBundle = ResourceBundle.getBundle("database");
        String salt = resourceBundle.getString("salt");
        md.update((salt + md5Pass).getBytes());
        String md5Pass2 =  DatatypeConverter.printHexBinary(md.digest());
        return md5Pass2;
    }

    public static class Builder {
        private User newUser;

        public Builder() {
            newUser = new User();
        }

        public Builder withUserId(int userId) {
            newUser.userId = userId;
            return this;
        }

        public Builder withLogin(String login) {
            newUser.login = login;
            return this;
        }

        public Builder withNewPassword(String password) {
            String hashPassword = hashPassword(password);
            newUser.password = hashPassword;
            return this;
        }

        public Builder withPassword(String password) {
            newUser.password = password;
            return this;
        }

        public Builder withEmail(String email) {
            newUser.email = email;
            return this;
        }

        public Builder withPhone(long phone) {
            newUser.phone = phone;
            return this;
        }

        public Builder withName(String name) {
            newUser.name = name;
            return this;
        }

        public Builder withRegistrationDate(LocalDate registrationDate) {
            newUser.registrationDate = registrationDate;
            return this;
        }

        public Builder withDiscount(double discount) {
            newUser.discount = discount;
            return this;
        }

        public Builder withStar(int star) {
            newUser.star = star;
            return this;
        }

        public Builder withComment(String comment) {
            newUser.comment = comment;
            return this;
        }

        public Builder withBill(BigDecimal bill) {
            newUser.bill = bill;
            return this;
        }

        public Builder withType(Role role) {
            newUser.role = role;
            return this;
        }

        public Builder withIsActive(boolean isActive) {
            newUser.isActive = isActive;
            return this;
        }

        public User build() {
            return newUser;
        }
    }

    public int getUserId() {
        return userId;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public long getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public double getDiscount() {
        return discount;
    }

    public int getStar() {
        return star;
    }

    public String getComment() {
        return comment;
    }

    public BigDecimal getBill() {
        return bill;
    }

    public Role getRole() {
        return role;
    }

    public boolean getIsActive() {
        return isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId &&
                phone == user.phone &&
                Double.compare(user.discount, discount) == 0 &&
                star == user.star &&
                isActive == user.isActive &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                Objects.equals(email, user.email) &&
                Objects.equals(name, user.name) &&
                Objects.equals(registrationDate, user.registrationDate) &&
                Objects.equals(comment, user.comment) &&
                Objects.equals(bill, user.bill) &&
                Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, login, password, email, phone, name, registrationDate, discount, star, comment, bill, role, isActive);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                ", name='" + name + '\'' +
                ", registrationDate=" + registrationDate +
                ", discount=" + discount +
                ", star=" + star +
                ", comment='" + comment + '\'' +
                ", bill=" + bill +
                ", role=" + role +
                ", isActive=" + isActive +
                '}';
    }
}
