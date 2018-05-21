
package com.restful.api.rest;

import com.restful.model.common.system.SysUser;
import com.restful.query.UserQuery;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ThScurityController {


   /* @RequestMapping(value = "/user",method = RequestMethod.GET)
    public List<SysUser> user(UserQuery query, Pageable pageable){
        System.out.println(ReflectionToStringBuilder.toString(query, ToStringStyle.MULTI_LINE_STYLE));
        //System.out.println(name);
        List<SysUser> users = new ArrayList<>();
        *//*users.add(new User("张三",18));
        users.add(new User("李四",16));
        users.add(new User("jetty",22));*//*
        return users;
    }*/



/*@RequestMapping(value = "/user/{id:[1-9]\\d*}",method = RequestMethod.GET)
    public User getUser(@PathVariable String id){
        System.out.println(id);
        return new User("张三",18);
    }*/


    @PostMapping("/user")
    public SysUser createUser(@Valid @RequestBody SysUser user, BindingResult errs){
       errs.getAllErrors().stream().forEach(e-> System.out.println(e.getDefaultMessage()));
       if(errs.hasErrors()){
           return null;
       }
        System.out.println(ReflectionToStringBuilder.toString(user,ToStringStyle.MULTI_LINE_STYLE));
        return user;
    }

    @PutMapping("/user/{id}")
    public SysUser updateUser(@Valid @RequestBody SysUser user, BindingResult errs){
       errs.getAllErrors().stream().forEach(e-> System.out.println(e.getDefaultMessage()));
        System.out.println(ReflectionToStringBuilder.toString(user,ToStringStyle.MULTI_LINE_STYLE));
        return user;
    }

    @DeleteMapping("/user/{id}")
    public boolean deleteUser(@PathVariable(name = "id") String ids) throws Exception{
        System.out.println(ids);
        return true;
    }

}

