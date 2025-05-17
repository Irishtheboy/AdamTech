/*InventoryStatus enum Class
  Seymour Lawrence (230185991)
  11 May 2025*/
package za.co.admatech.domain.enums;

public enum InventoryStatus {
    IN_STOCK("In Stock"),
    LOW_STOCK("Low Stock"),
    OUT_OF_STOCK("Out of Stock");

    private final String status;

    InventoryStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return status;
    }
}
