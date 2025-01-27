package datasecurity.Enum;

import java.io.Serializable;

public enum Role implements Serializable {

    admin("admin"),
    serviceTechnician("serviceTechnician"),
    powerUser("powerUser"),
    user("user");
    private final String role;

    Role(String role) {
        this.role=role;
    }

    public String getRole() {
        return role;
    }

    public static Role fromString(String roleType) {
        for (Role role : Role.values()) {
            if (role.role.equalsIgnoreCase(roleType)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Not found");
    }

}
