package com.restful.handler.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException{

    private Integer code;

    private String message;

    public CustomException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
