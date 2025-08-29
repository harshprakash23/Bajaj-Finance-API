package com.example.bfhl_api;

public class BfhlErrorResponse {
    private boolean isSuccess;
    private String error;

    public boolean isIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}