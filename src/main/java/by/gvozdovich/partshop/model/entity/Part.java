package by.gvozdovich.partshop.model.entity;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Representation of parts
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class Part implements DbEntity {
    private int partId;
    private String catalogNo;
    private String originalCatalogNo;
    private String info;
    private BigDecimal price;
    private String pictureUrl;
    private int wait;
    private Brand brand;
    private int stockCount;
    private boolean isActive;

    private Part() {

    }

    public static class Builder {
        private Part newPart;

        public Builder() {
            newPart = new Part();
        }

        public Builder withPartId(int partId) {
            newPart.partId = partId;
            return this;
        }

        public Builder withCatalogNo(String catalogNo) {
            newPart.catalogNo = catalogNo;
            return this;
        }

        public Builder withOriginalCatalogNo(String originalCatalogNo) {
            newPart.originalCatalogNo = originalCatalogNo;
            return this;
        }

        public Builder withInfo(String info) {
            newPart.info = info;
            return this;
        }

        public Builder withPrice(BigDecimal price) {
            newPart.price = price;
            return this;
        }

        public Builder withPictureUrl(String pictureUrl) {
            newPart.pictureUrl = pictureUrl;
            return this;
        }

        public Builder withWait(int wait) {
            newPart.wait = wait;
            return this;
        }

        public Builder withBrand(Brand brand) {
            newPart.brand = brand;
            return this;
        }

        public Builder withStockCount(int stockCount) {
            newPart.stockCount = stockCount;
            return this;
        }

        public Builder withIsActive(boolean isActive) {
            newPart.isActive = isActive;
            return this;
        }

        public Part build() {
            return newPart;
        }
    }

    public int getPartId() {
        return partId;
    }

    public String getCatalogNo() {
        return catalogNo;
    }

    public String getOriginalCatalogNo() {
        return originalCatalogNo;
    }

    public String getInfo() {
        return info;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public int getWait() {
        return wait;
    }

    public Brand getBrand() {
        return brand;
    }

    public int getStockCount() {
        return stockCount;
    }

    public boolean getIsActive() {
        return isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Part part = (Part) o;
        return partId == part.partId &&
                wait == part.wait &&
                stockCount == part.stockCount &&
                isActive == part.isActive &&
                Objects.equals(catalogNo, part.catalogNo) &&
                Objects.equals(originalCatalogNo, part.originalCatalogNo) &&
                Objects.equals(info, part.info) &&
                Objects.equals(price, part.price) &&
                Objects.equals(pictureUrl, part.pictureUrl) &&
                Objects.equals(brand, part.brand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(partId, catalogNo, originalCatalogNo, info, price, pictureUrl, wait, brand, stockCount, isActive);
    }

    @Override
    public String toString() {
        return "Part{" +
                "partId=" + partId +
                ", catalogNo='" + catalogNo + '\'' +
                ", originalCatalogNo='" + originalCatalogNo + '\'' +
                ", info='" + info + '\'' +
                ", price=" + price +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", wait=" + wait +
                ", brand=" + brand +
                ", stockCount=" + stockCount +
                ", isActive=" + isActive +
                '}';
    }
}
