package com.restful.mysql;

import com.restful.model.common.system.SysUser;

public interface IUserService {

    SysUser getUser(String account);


}
