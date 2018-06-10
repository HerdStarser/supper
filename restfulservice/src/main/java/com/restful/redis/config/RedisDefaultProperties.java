package com.restful.redis.config;

public class RedisDefaultProperties {

    public static final Boolean isCluster = false;

    public static final Integer timeOut = 15000;

    public static final Integer maxRedirects = 5;

    public static final Integer defaultExpiration = 300;

    public static final Integer maxTotal = 120;

    public static final Integer minIdle = 10;

    public static final Integer maxIdle = 20;

    public static final Integer connectionTimeout = 20000;

    public static final Integer maxAttempts = 20;
}
