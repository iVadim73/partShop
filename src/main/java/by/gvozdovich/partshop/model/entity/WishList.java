package by.gvozdovich.partshop.model.entity;

import java.util.Objects;

public class WishList implements DbEntity {
    private int wishListId;
    private User user;
    private Part part;

    private WishList() {

    }

    public static class Builder {
        private WishList newWishList;

        public Builder() {
            newWishList = new WishList();
        }

        public Builder withWishListId(int wishListId) {
            newWishList.wishListId = wishListId;
            return this;
        }

        public Builder withUser(User user) {
            newWishList.user = user;
            return this;
        }

        public Builder withPart(Part part) {
            newWishList.part = part;
            return this;
        }

        public WishList build() {
            return newWishList;
        }
    }

    public int getWishListId() {
        return wishListId;
    }

    public User getUser() {
        return user;
    }

    public Part getPart() {
        return part;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WishList wishList = (WishList) o;
        return wishListId == wishList.wishListId &&
                user == wishList.user &&
                part == wishList.part;
    }

    @Override
    public int hashCode() {
        return Objects.hash(wishListId, user, part);
    }

    @Override
    public String toString() {
        return "WishList{" +
                "wishListId=" + wishListId +
                ", user=" + user +
                ", part=" + part +
                '}';
    }
}
