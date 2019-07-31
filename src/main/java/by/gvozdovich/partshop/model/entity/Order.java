package by.gvozdovich.partshop.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Order implements DbEntity {
    private int orderId;
    private User user;
    private Part part;
    private LocalDate dateOrder;
    private LocalDate dateCondition;
    private BigDecimal cost;
    private Condition condition;
    private int partCount;
    private boolean isActive;
    private Bill bill;

    private Order() {

    }

    public static class Builder {
        private Order newOrder;

        public Builder() {
            newOrder = new Order();
        }

        public Builder withOrderId(int orderId) {
            newOrder.orderId = orderId;
            return this;
        }

        public Builder withUser(User user) {
            newOrder.user = user;
            return this;
        }

        public Builder withPart(Part part) {
            newOrder.part = part;
            return this;
        }

        public Builder withDateOrder(LocalDate dateOrder) {
            newOrder.dateOrder = dateOrder;
            return this;
        }

        public Builder withDateCondition(LocalDate dateCondition) {
            newOrder.dateCondition = dateCondition;
            return this;
        }

        public Builder withCost(BigDecimal cost) {
            newOrder.cost = cost;
            return this;
        }

        public Builder withCondition(Condition condition) {
            newOrder.condition = condition;
            return this;
        }

        public Builder withPartCount(int partCount) {
            newOrder.partCount = partCount;
            return this;
        }

        public Builder withIsActive(boolean isActive) {
            newOrder.isActive = isActive;
            return this;
        }

        public Builder withBill(Bill bill) {
            newOrder.bill = bill;
            return this;
        }

        public Order build() {
            return newOrder;
        }
    }

    public int getOrderId() {
        return orderId;
    }

    public User getUser() {
        return user;
    }

    public Part getPart() {
        return part;
    }

    public LocalDate getDateOrder() {
        return dateOrder;
    }

    public LocalDate getDateCondition() {
        return dateCondition;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public Condition getCondition() {
        return condition;
    }

    public int getPartCount() {
        return partCount;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public Bill getBill() {
        return bill;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId == order.orderId &&
                user == order.user &&
                part == order.part &&
                condition == order.condition &&
                Objects.equals(dateOrder, order.dateOrder) &&
                Objects.equals(dateCondition, order.dateCondition) &&
                Objects.equals(cost, order.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, user, part, dateOrder, dateCondition, cost, condition);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", user=" + user +
                ", part=" + part +
                ", dateOrder=" + dateOrder +
                ", dateCondition=" + dateCondition +
                ", cost=" + cost +
                ", condition=" + condition +
                '}';
    }
}
