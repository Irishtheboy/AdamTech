package za.co.admatech.DTO;

import za.co.admatech.domain.Customer;

public class LoginResponse {
    private Customer user;
    private String message;
    private boolean success = true;

    public LoginResponse(Customer user, String message) {
        this.user = user;
        this.message = message;
    }

    public Customer getUser() {
        return user;
    }

    public void setUser(Customer user) {
        this.user = user;
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
