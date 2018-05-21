package com.restful;

public enum ReturnMessage {

    RETURN_FOR_TRY_CATCH(500,"server internal error"),

    UNDEFIND_ENUM_TYPE(600,"undefined enum type");

    Integer code;

    String message;

    ReturnMessage(Integer code, String message) {
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

    public String getMessageByCode(Integer code){
        for (ReturnMessage message:ReturnMessage.values()){
            if(message.getCode() == code){
                return message.getMessage();
            }
        }
        return UNDEFIND_ENUM_TYPE.getMessage();
    }
}
