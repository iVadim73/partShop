package by.gvozdovich.partshop.model.entity;

import java.util.Objects;

/**
 * Representation of order conditions
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class Condition implements DbEntity {
    private int conditionId;
    private String name;
    private String info;

    private Condition() {

    }

    public static class Builder {
        private Condition newCondition;

        public Builder() {
            newCondition = new Condition();
        }

        public Builder withConditionId(int conditionId) {
            newCondition.conditionId = conditionId;
            return this;
        }

        public Builder withName(String name) {
            newCondition.name = name;
            return this;
        }

        public Builder withInfo(String info) {
            newCondition.info = info;
            return this;
        }

        public Condition build() {
            return newCondition;
        }
    }

    public int getConditionId() {
        return conditionId;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Condition condition = (Condition) o;
        return conditionId == condition.conditionId &&
                Objects.equals(name, condition.name) &&
                Objects.equals(info, condition.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(conditionId, name, info);
    }

    @Override
    public String toString() {
        return "Condition{" +
                "conditionId=" + conditionId +
                ", name='" + name + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}
