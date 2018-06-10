package com.restful;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(exclude = {MongoAutoConfiguration.class,MongoDataAutoConfiguration.class})
@ComponentScan(basePackages = {"com.restful"})
//@EnableAutoConfiguration
@EnableSwagger2
@EnableConfigurationProperties
@EnableCaching
public class ApplicationStart {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationStart.class,args);


    }

}
