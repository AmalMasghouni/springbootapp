package com.spring.backproject.Controller;

public class AuthResponse {
    private Boolean success;
    private String message;
    public AuthResponse(boolean success, String message) {
        this.success=success;
        this.message=message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
