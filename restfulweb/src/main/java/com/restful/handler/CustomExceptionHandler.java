package com.restful.handler;


import com.restful.ReturnMessage;
import com.restful.handler.exception.CustomException;
import com.restful.handler.exception.TryCatchException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler{

    @ExceptionHandler({CustomException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Map<String,Object> ExceptionForValidData(CustomException customException){
        Map<String,Object> map=new HashMap<>();
        map.put("code",customException.getCode());
        map.put("message",customException.getMessage());
        return map;
    }

    @ExceptionHandler(value = {TryCatchException.class})
    @ResponseStatus(value= HttpStatus.OK)
    @ResponseBody
    public Map<String,Object> ExceptionForTryCatch(TryCatchException tryCatchException){
        Map<String,Object> map=new HashMap<>();
        map.put("code", ReturnMessage.RETURN_FOR_TRY_CATCH.getCode());
        map.put("message",tryCatchException.getMessage());
        return map;
    }




}
