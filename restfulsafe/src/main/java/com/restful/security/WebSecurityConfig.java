package com.restful.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/login_sigin.html")   //重定向到自己的登录页面
                .loginProcessingUrl("/login")        //告诉spring 此链接需要走登录验证的过程
                .and() // 返回一个 HttpSecurity 对象
                .csrf().disable() //关闭CSRF
                .authorizeRequests()                            //通过权限配置
                .antMatchers("/login_sigin.html")   //匹配此url
                .permitAll()                                //放行
                .and()
                .authorizeRequests()
                .antMatchers("/api")
                .permitAll()
                .anyRequest().authenticated();


                                 //任何request 需要认证
             //   .and().httpBasic();  //基本的登录配置页面

    }
}
