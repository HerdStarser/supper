package com.restful.mysql.impl;


import com.restful.dao.system.IUserDao;

import com.restful.model.common.system.SysUser;
import com.restful.mysql.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService implements IUserService {

    @Resource
    private IUserDao userDao;

    @Override
    public SysUser getUser(String account) {
        return userDao.getUser(account);
    }
}
