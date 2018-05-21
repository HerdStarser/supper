package com.restful.intercept;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;


/**
* @Description:    对每个方法做时间长度的检查
* @Author:         walker
* @CreateDate:     2018/4/15 12:52
* @Version:        1.0
*/

@Component
public class TimeIntercept implements HandlerInterceptor{

    private  static Logger logger = LoggerFactory.getLogger(TimeIntercept.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        request.setAttribute("requestStart",new Date().getTime());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {
        HandlerMethod method = (HandlerMethod) o;
        Long time=new Date().getTime()-Long.valueOf(request.getAttribute("requestStart").toString());
        //System.out.println("执行 == "+method.getMethod().getName()+" ==,其用时 "+time+" ms");
        logger.info("执行 == "+method.getMethod().getName()+" ==,其用时 "+time+" ms");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        //logger.info("执行 == "+((HandlerMethod) o).getMethod().getName() + " ==,发生异常了！");
    }
}
