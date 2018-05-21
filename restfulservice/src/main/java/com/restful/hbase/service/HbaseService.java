package com.restful.hbase.service;

import com.restful.hbase.query.HbaseQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class HbaseService {

    @Resource
    private HbaseQuery hbaseQuery;

    public Boolean isExist(String tableName){

        return hbaseQuery.isTableExit(tableName);
    }

}
