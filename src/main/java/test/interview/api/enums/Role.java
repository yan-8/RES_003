package test.interview.api.enums;

public enum Role {
    SUPERVISOR("supervisor"),
    ADMIN("admin"),
    USER("user");

    String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
