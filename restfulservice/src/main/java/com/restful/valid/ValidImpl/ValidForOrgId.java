package com.restful.valid.ValidImpl;


import com.hotent.platform.model.system.SysOrgInfo;
import com.hotent.platform.service.system.SysOrgInfoService;
import com.restful.valid.ValidOrgId;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidForOrgId implements ConstraintValidator<ValidOrgId, Long> {

    @Resource
    private SysOrgInfoService service;

    @Override
    public void initialize(ValidOrgId validOrgId) {

    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }
}
