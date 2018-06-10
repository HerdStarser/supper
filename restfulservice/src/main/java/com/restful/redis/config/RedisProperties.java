package com.restful.redis.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(
        prefix = "my.config.redis"
)
public class RedisProperties {

    private Boolean isCluster = RedisDefaultProperties.isCluster;

    private String host;

    private Integer port;

    private Integer timeOut = RedisDefaultProperties.timeOut;

    private String hosts;

    private Integer maxRedirects = RedisDefaultProperties.maxRedirects;

    private Integer defaultExpiration = RedisDefaultProperties.defaultExpiration;

    private String password = null;

    private Integer maxTotal = RedisDefaultProperties.maxTotal;

    private Integer minIdle = RedisDefaultProperties.minIdle;

    private Integer maxIdle = RedisDefaultProperties.maxIdle;

    private Integer connectionTimeout = RedisDefaultProperties.connectionTimeout;

    private Integer maxAttempts = RedisDefaultProperties.maxAttempts;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Boolean getIsCluster() {
        return isCluster;
    }

    public void setIsCluster(Boolean isCluster) {
        this.isCluster = isCluster;
    }

    public Integer getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Integer timeOut) {
        this.timeOut = timeOut;
    }

    public String getHosts() {
        return hosts;
    }

    public void setHosts(String hosts) {
        this.hosts = hosts;
    }

    public Integer getMaxRedirects() {
        return maxRedirects;
    }

    public void setMaxRedirects(Integer maxRedirects) {
        this.maxRedirects = maxRedirects;
    }

    public Integer getDefaultExpiration() {
        return defaultExpiration;
    }

    public void setDefaultExpiration(Integer defaultExpiration) {
        this.defaultExpiration = defaultExpiration;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(Integer maxTotal) {
        this.maxTotal = maxTotal;
    }

    public Integer getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(Integer minIdle) {
        this.minIdle = minIdle;
    }

    public Integer getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(Integer maxIdle) {
        this.maxIdle = maxIdle;
    }

    public Integer getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(Integer connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public Integer getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(Integer maxAttempts) {
        this.maxAttempts = maxAttempts;
    }
}
