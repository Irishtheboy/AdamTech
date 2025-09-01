package za.co.admatech.DTO;

import za.co.admatech.domain.Customer;

public class RegistrationResponse {
    private Customer data;
    private String message;
    private boolean success = true;

    public RegistrationResponse(Customer data, String message) {
        this.data = data;
        this.message = message;
    }

    public Customer getData() {
        return data;
    }

    public void setData(Customer data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
