package za.co.admatech.domain.enums;

public enum Permission {
    CUSTOMER_READ("customer:read"),
    CUSTOMER_UPDATE("customer:update"),
    CUSTOMER_CREATE("customer:create"),
    CUSTOMER_DELETE("customer:delete"),
    
    PRODUCT_READ("product:read"),
    PRODUCT_UPDATE("product:update"),
    PRODUCT_CREATE("product:create"),
    PRODUCT_DELETE("product:delete"),
    
    ORDER_READ("order:read"),
    ORDER_UPDATE("order:update"),
    ORDER_CREATE("order:create"),
    ORDER_DELETE("order:delete"),
    
    PAYMENT_READ("payment:read"),
    PAYMENT_UPDATE("payment:update"),
    PAYMENT_CREATE("payment:create"),
    PAYMENT_DELETE("payment:delete");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
