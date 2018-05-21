package com.restful.valid.ValidImpl;


import com.restful.model.common.system.SysUser;
import com.restful.mysql.IUserService;
import com.restful.valid.ValidId;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidForId implements ConstraintValidator<ValidId,Long> {

    @Resource
    private IUserService userService;

    @Override
    public void initialize(ValidId validId) {
        System.out.println("校验初始化");
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext constraintValidatorContext) {
        SysUser user = userService.getUser(value+"");
        System.out.println("============ "+ value);
        System.out.println(ReflectionToStringBuilder.toString(user, ToStringStyle.MULTI_LINE_STYLE));
        System.out.println("============");
        if(user!=null){
            return true;
        }
        return false;
    }
}
