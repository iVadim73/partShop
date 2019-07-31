package by.gvozdovich.partshop.model.entity;

import java.util.Objects;

public class Brand implements DbEntity {
    private int brandId;
    private String name;
    private String country;
    private String info;
    private boolean isActive;

    private Brand() {

    }

    public static class Builder {
        private Brand newBrand;

        public Builder() {
            newBrand = new Brand();
        }

        public Builder withBrandId(int brandId) {
            newBrand.brandId = brandId;
            return this;
        }

        public Builder withName(String name) {
            newBrand.name = name;
            return this;
        }

        public Builder withCountry(String country) {
            newBrand.country = country;
            return this;
        }

        public Builder withInfo(String info) {
            newBrand.info = info;
            return this;
        }

        public Builder withIsActive(boolean isActive) {
            newBrand.isActive = isActive;
            return this;
        }

        public Brand build() {
            return newBrand;
        }
    }

    public int getBrandId() {
        return brandId;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getInfo() {
        return info;
    }

    public boolean getIsActive() {
        return isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Brand brand = (Brand) o;
        return brandId == brand.brandId &&
                Objects.equals(name, brand.name) &&
                Objects.equals(country, brand.country) &&
                Objects.equals(info, brand.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brandId, name, country, info);
    }

    @Override
    public String toString() {
        return "Brand{" +
                "brandId=" + brandId +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}
