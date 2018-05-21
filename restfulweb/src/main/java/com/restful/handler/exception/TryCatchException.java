package com.restful.handler.exception;

import com.restful.ReturnMessage;

public class TryCatchException extends RuntimeException{

    private String message;

    public TryCatchException() {
        this.message= ReturnMessage.RETURN_FOR_TRY_CATCH.getMessage();
    }

    public TryCatchException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
