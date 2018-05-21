package com.restful.api.rest;


import com.restful.properties.DataBaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;

@Controller
@EnableConfigurationProperties(DataBaseProperties.class)
public class Test {

    @Resource
    private DataBaseProperties properties;

    @GetMapping("/getPro")
    public String  get(){
        String port = properties.getHbaseClientPort();
        System.out.println(port);
        return properties.getHbaseZkQuorum();
    }
}
