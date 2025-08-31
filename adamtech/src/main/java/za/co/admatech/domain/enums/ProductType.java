package za.co.admatech.domain.enums;
/* ProductType.java

     ProductType enum class

     Author: FN Lukhele (221075127)

     Date: 10 May 2025 */
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
