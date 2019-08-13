package by.gvozdovich.partshop.model.entity;

import java.util.Objects;

/**
 * Representation of account roles
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class Role implements DbEntity {
    private int roleId;
    private String type;

    private Role() {

    }

    public static class Builder {
        private Role newRole;

        public Builder() {
            newRole = new Role();
        }

        public Builder withRoleId(int roleId) {
            newRole.roleId = roleId;
            return this;
        }

        public Builder withType(String type) {
            newRole.type = type;
            return this;
        }

        public Role build() {
            return newRole;
        }
    }

    public int getRoleId() {
        return roleId;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role1 = (Role) o;
        return roleId == role1.roleId &&
                Objects.equals(type, role1.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, type);
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", type='" + type + '\'' +
                '}';
    }
}
