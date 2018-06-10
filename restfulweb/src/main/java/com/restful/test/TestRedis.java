package com.restful.test;

import com.restful.redis.RedisClusterUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class TestRedis {

    /*@Resource
    private RedisClusterUtil redisClusterUtil;*/

    /*@Resource
    private RedisUtil redisUtil;*/

    /*@GetMapping("/testRedisShardUtil_1")
    @ResponseBody
    public String testRedisShardUtil_1(){
       System.out.println("testRedisShardUtil() invoked = = = = ");
        redisClusterUtil.set("test","123456");
        return redisClusterUtil.get("test");
    }*/

    /*@GetMapping("/testredisUtil_1")
    @ResponseBody
    public String testredisUtil_1(){
       // System.out.println("testRedisShardUtil() invoked = = = = " + param);
        redisUtil.set("test","123456");
        return redisUtil.get("test");
    }*/

    @GetMapping("/testCachable")
    @Cacheable(value = "sos",key = "#p0")
    //@CachePut(value = "s1")
    //@CacheEvict(value = "sos",allEntries = true)
   // @Caching(cacheable = {@Cacheable(value = "sos")})
    @ResponseBody
    public  String  get(@RequestBody String param){
        System.out.println("我走这个方法了" + param);

        return "50";
    }
}
