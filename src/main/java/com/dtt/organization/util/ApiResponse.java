package com.dtt.organization.util;

import java.io.Serializable;

public class ApiResponse<V> implements Serializable
{
    private boolean success;
    private String message;
    private Object result;

    public ApiResponse() {

    }

    public ApiResponse(boolean success, String message, Object result) {
        this.success = success;
        this.message = message;
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }
}


