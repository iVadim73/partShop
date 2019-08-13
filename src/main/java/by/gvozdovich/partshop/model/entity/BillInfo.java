package by.gvozdovich.partshop.model.entity;

import java.util.Objects;

/**
 * Representation of account transactions info
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class BillInfo implements DbEntity {

    private int billInfoId;
    private String info;

    private BillInfo() {

    }

    public static class Builder {
        private BillInfo newBillInfo;

        public Builder() {
            newBillInfo = new BillInfo();
        }

        public Builder withBillInfoId(int billInfoId) {
            newBillInfo.billInfoId = billInfoId;
            return this;
        }

        public Builder withInfo(String info) {
            newBillInfo.info = info;
            return this;
        }

        public BillInfo build() {
            return newBillInfo;
        }
    }

    public int getBillInfoId() {
        return billInfoId;
    }

    public String getInfo() {
        return info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BillInfo billInfo = (BillInfo) o;
        return billInfoId == billInfo.billInfoId &&
                Objects.equals(info, billInfo.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(billInfoId, info);
    }

    @Override
    public String toString() {
        return "BillInfo{" +
                "billInfoId=" + billInfoId +
                ", info='" + info + '\'' +
                '}';
    }
}
