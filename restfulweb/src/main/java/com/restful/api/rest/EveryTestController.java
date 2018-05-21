package com.restful.api.rest;



import com.restful.hbase.service.HbaseService;
import com.restful.model.common.system.SysUser;
import com.restful.mysql.IUserService;
import com.restful.query.UserQuery;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
public class EveryTestController {

    @Resource
    private IUserService userService;
    @Resource
    private HbaseService hbaseService;

    @GetMapping("/user/{id}")
    public SysUser getUser(@PathVariable String id){
        //int i=1/0;
        System.out.println(id);
        //throw new CustomException(200,"6351");
        /*User user = userService.getUser(Long.parseLong(id));
        return user;*/
        return null;
    }

   @GetMapping("/api")
    public SysUser getUsers(@Valid @RequestBody UserQuery userQuery, BindingResult result){

        System.out.println(ReflectionToStringBuilder.toString(userQuery, ToStringStyle.MULTI_LINE_STYLE));
        return new SysUser();
    }

    @GetMapping("/isExist")
    public Boolean isExist(){
        Boolean exist = hbaseService.isExist("e123");
        System.out.println(exist);
        return exist;
    }

}
