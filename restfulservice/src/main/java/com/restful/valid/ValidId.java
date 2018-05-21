package com.restful.valid;


import com.restful.valid.ValidImpl.ValidForId;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy={ValidForId.class})
public @interface ValidId {

    String message() default "id不存在";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};



}
