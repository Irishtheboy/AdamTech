package za.co.admatech.domain.enums;

public enum OrderStatus {

    PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED, RETURNED
}


    PENDING("Pending"),
    CONFIRMED("Confirmed"),
    SHIPPED("Shipped"),
    DELIVERED("Delivered"),
    COMPLETED("Completed"),

    PAID("Paid"),        // ← added

    CANCELLED("Cancelled"),
    RETURNED("Returned");

    private final String status;
    OrderStatus(String status) {
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

