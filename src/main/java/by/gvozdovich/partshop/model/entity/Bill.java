package by.gvozdovich.partshop.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Representation of account transactions
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class Bill implements DbEntity {
    private int billId;
    private User user;
    private BigDecimal sum;
    private BillInfo billInfo;
    private LocalDate date;

    private Bill() {

    }

    public static class Builder {
        private Bill newBill;

        public Builder() {
            newBill = new Bill();
        }

        public Builder withBillId(int billId) {
            newBill.billId = billId;
            return this;
        }

        public Builder withUser(User user) {
            newBill.user = user;
            return this;
        }

        public Builder withSum(BigDecimal sum) {
            newBill.sum = sum;
            return this;
        }

        public Builder withBillInfo(BillInfo billInfo) {
            newBill.billInfo = billInfo;
            return this;
        }

        public Builder withDate(LocalDate date) {
            newBill.date = date;
            return this;
        }

        public Bill build() {
            return newBill;
        }
    }

    public int getBillId() {
        return billId;
    }

    public User getUser() {
        return user;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public BillInfo getBillInfo() {
        return billInfo;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bill bill = (Bill) o;
        return billId == bill.billId &&
                Objects.equals(user, bill.user) &&
                Objects.equals(sum, bill.sum) &&
                Objects.equals(billInfo, bill.billInfo) &&
                Objects.equals(date, bill.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(billId, user, sum, billInfo, date);
    }

    @Override
    public String toString() {
        return "Bill{" +
                "billId=" + billId +
                ", user=" + user +
                ", sum=" + sum +
                ", billInfo=" + billInfo +
                ", date=" + date +
                '}';
    }
}
