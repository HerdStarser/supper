package com.restful.api.rest;


import com.restful.model.common.TestModel;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
public class Test {

    /*@Resource
    private RedisUtil redisUtil;*/


   /* @GetMapping("/testCachable")
    //@Cacheable(value = "sos",key = "#root.method.name",condition = "#p0.equals('14')")
    //@CachePut(value = "s1")
    //@CacheEvict(value = "sos",allEntries = true)
    @Caching(cacheable = {@Cacheable(value = "sos")})
    @ResponseBody
    public  String  get(@RequestBody String param){
        System.out.println("我走这个方法了" + param);
        List<TestModel> modelList = new ArrayList<>();
        TestModel model1 = new TestModel(param,13,null);
        TestModel model2 = new TestModel(param,15,null);
        modelList.add(model1);
        modelList.add(model2);
        TestModel model = new TestModel(param,10,modelList);
        return model.toString();
    }*/
}
