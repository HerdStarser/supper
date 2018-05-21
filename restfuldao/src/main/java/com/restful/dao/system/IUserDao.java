package com.restful.dao.system;

import com.restful.model.common.system.SysUser;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserDao {

    //SysUser getUser(Long id);

    SysUser getUser(String accound);
}
