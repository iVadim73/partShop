package by.gvozdovich.partshop.model.entity;

import java.util.Objects;

/**
 * Representation of account carts
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class Cart implements DbEntity {
    private int cartId;
    private User user;
    private Part part;
    private int count;

    private Cart() {
    }

    public static class Builder {
        private Cart newCart;

        public Builder() {
            newCart = new Cart();
        }

        public Builder withCartId(int cartId) {
            newCart.cartId = cartId;
            return this;
        }

        public Builder withUser(User user) {
            newCart.user = user;
            return this;
        }

        public Builder withPart(Part part) {
            newCart.part = part;
            return this;
        }

        public Builder withCount(int count) {
            newCart.count = count;
            return this;
        }

        public Cart build() {
            return newCart;
        }
    }

    public int getCartId() {
        return cartId;
    }

    public User getUser() {
        return user;
    }

    public Part getPart() {
        return part;
    }

    public int getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return cartId == cart.cartId &&
                count == cart.count &&
                Objects.equals(user, cart.user) &&
                Objects.equals(part, cart.part);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartId, user, part, count);
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartId=" + cartId +
                ", user=" + user +
                ", part=" + part +
                ", count=" + count +
                '}';
    }
}
