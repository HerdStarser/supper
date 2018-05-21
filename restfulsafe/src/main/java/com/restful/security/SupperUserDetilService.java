package com.restful.security;

import com.restful.dao.system.IUserDao;
import com.restful.model.common.system.SysUser;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SupperUserDetilService implements UserDetailsService{

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private IUserDao dao;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        SysUser sysUser = dao.getUser(name);
        System.out.println(ReflectionToStringBuilder.toString(sysUser, ToStringStyle.MULTI_LINE_STYLE));
        User user = new User(sysUser.getAccount(),"123456",
                sysUser.isEnabled(),sysUser.isAccountNonExpired(),sysUser.isCredentialsNonExpired(),
                sysUser.isAccountNonLocked(), sysUser.getAuthorities());
        return user;
    }
}
