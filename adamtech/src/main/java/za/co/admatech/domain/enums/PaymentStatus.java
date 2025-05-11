package za.co.admatech.domain.enums;
/* PaymentStatus.java

     PaymentStatus POJO class

     Author: FN Lukhele (221075127)

     Date: 10 May 2025 */
public enum PaymentStatus {
    PENDING("Pending"),
    COMPLETED("Completed"),
    FAILED("Failed"),
    REFUNDED("Refunded");

    private final String status;

    PaymentStatus(String status) {
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
