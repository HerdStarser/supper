package com.restful.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
@Component
@Configuration
@Primary
@ConfigurationProperties(prefix = "conf")
@PropertySource(value = "classpath:/conf/database.properties")
public class DataBaseProperties {

    private String hbaseClientPort;

    private String hbaseZkQuorum;

    private String hbaseDataDir;

    private String hbaseZkParent;

    public String getHbaseClientPort() {
        return hbaseClientPort;
    }

    public void setHbaseClientPort(String hbaseClientPort) {
        this.hbaseClientPort = hbaseClientPort;
    }

    public String getHbaseZkQuorum() {
        return hbaseZkQuorum;
    }

    public void setHbaseZkQuorum(String hbaseZkQuorum) {
        this.hbaseZkQuorum = hbaseZkQuorum;
    }

    public String getHbaseDataDir() {
        return hbaseDataDir;
    }

    public void setHbaseDataDir(String hbaseDataDir) {
        this.hbaseDataDir = hbaseDataDir;
    }

    public String getHbaseZkParent() {
        return hbaseZkParent;
    }

    public void setHbaseZkParent(String hbaseZkParent) {
        this.hbaseZkParent = hbaseZkParent;
    }
}
