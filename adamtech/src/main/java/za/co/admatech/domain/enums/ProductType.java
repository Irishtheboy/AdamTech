package za.co.admatech.domain.enums;

public enum ProductType {
    LAPTOP("Laptop"),
    DESKTOP("Desktop"),
    PERIPHERAL("Peripheral");

    private final String type;
    ProductType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }
    @Override
    public String toString() {
        return type;
    }

}
