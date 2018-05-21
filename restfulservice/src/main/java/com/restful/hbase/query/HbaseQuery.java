package com.restful.hbase.query;


import com.restful.properties.DataBaseProperties;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;

@Component
public class HbaseQuery {

    @Resource
    private DataBaseProperties properties;

    private Configuration configuration;

    private Connection connection;


    /**
    * @Description:    HbaseQuery初始化方法
    * @Author:         walker
    * @CreateDate:     2018/4/13 14:57
    * @Version:        1.0
    */
    @PostConstruct
    private void init(){
        try {
            if(configuration == null){
                configuration=HBaseConfiguration.create();
                configuration.set("hbase.zookeeper.property.clientPort", properties.getHbaseClientPort());
                configuration.set("hbase.zookeeper.quorum", properties.getHbaseZkQuorum());
                configuration.set("zookeeper.znode.parent", properties.getHbaseZkParent());
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("HbaseQuery init() failler " + properties.getHbaseClientPort());
            //logger.error("HbaseQuery init() failler: " + e.getMessage());
        }
    }


    /**
    * @Description:    获取Hbase链接
    * @Author:         walker
    * @CreateDate:     2018/4/13 14:58
    * @Version:        1.0
    */
    private Connection getConnection(){
        try{
            if(connection == null || connection.isClosed()){
                connection=ConnectionFactory.createConnection(configuration);
            }
        }catch (Exception e){
            System.out.println("HbaseQuery getConnection() failler " + e.getMessage());
            //logger.error("HbaseQuery getConnection() failler: " + e.getMessage());
        }
        return connection;
    }
    /**
    * @Description:    获取HbaseAdmin
    * @Author:         walker
    * @CreateDate:     2018/4/13 15:11
    * @Version:        1.0
    */
    private HBaseAdmin getHbaseAdmin(){
        HBaseAdmin admin = null;
        try {
            admin = (HBaseAdmin) getConnection().getAdmin();
        } catch (IOException e) {
            System.out.println("HbaseQuery getConnection() failler " + e.getMessage());
            //logger.error("HbaseQuery getConnection() failler: " + e.getMessage());
        }
        return admin;
    }

    /**
    * @Description:    判断Hbase表是否存在
    * @Author:         walker
    * @CreateDate:     2018/4/13 15:12
    * @Version:        1.0
    */
    public boolean isTableExit(String tableName){
        HBaseAdmin admin = getHbaseAdmin();
        try {
            return admin.tableExists(tableName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }





}
