package com.restful.util;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.apache.xerces.dom3.as.ASDataType;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
 
public class HBaseTemplate {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//实际参数作用到毫秒级
    private static Log LOG = LogFactory.getLog(HBaseTemplate.class);
    // 声明静态配置
    private static Configuration conf = null;

    static {
        conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.property.clientPort", "2181");
        conf.set("hbase.zookeeper.quorum", "hdp1,hdp2,hdp3");
        conf.set("zookeeper.znode.parent", "/hbase");
    }


    //判断表是否存在
    private static boolean isExist(String tableName) throws IOException {
        HBaseAdmin hAdmin = new HBaseAdmin(conf);
        return hAdmin.tableExists(tableName);
    }

    // 创建数据库表
    public static void createTable(String tableName, String[] columnFamilys)
            throws Exception {
        // 新建一个数据库管理员
        HBaseAdmin hAdmin = new HBaseAdmin(conf);
        if (hAdmin.tableExists(tableName)) {
            System.out.println("表 " + tableName + " 已存在！");
            System.exit(0);
        } else {
            // 新建一个students表的描述
            HTableDescriptor tableDesc = new HTableDescriptor(tableName);
            // 在描述里添加列族
            for (String columnFamily : columnFamilys) {
                tableDesc.addFamily(new HColumnDescriptor(columnFamily));
            }
            HTableDescriptor htd = hAdmin.getTableDescriptor(tableName.getBytes());
            htd.addCoprocessor("org.apache.hadoop.hbase.coprocessor.AggregateImplementation");
            // 根据配置好的描述建表
            hAdmin.createTable(tableDesc);
            System.out.println("创建表 " + tableName + " 成功!");
        }
    }

    // 删除数据库表
    public static void deleteTable(String tableName) throws Exception {
        // 新建一个数据库管理员
        HBaseAdmin hAdmin = new HBaseAdmin(conf);
        if (hAdmin.tableExists(tableName)) {
            // 关闭一个表
            hAdmin.disableTable(tableName);
            hAdmin.deleteTable(tableName);
            System.out.println("删除表 " + tableName + " 成功！");
        } else {
            System.out.println("删除的表 " + tableName + " 不存在！");
            System.exit(0);
        }
    }

    // 添加一条数据
    public static void addRow(String tableName, String row,
                              String columnFamily, String column, String value) throws Exception {
        HTable table = new HTable(conf, tableName);
        Put put = new Put(Bytes.toBytes(row));// 指定行
        // 参数分别:列族、列、值
        put.add(Bytes.toBytes(columnFamily), Bytes.toBytes(column),
                Bytes.toBytes(value));
        table.put(put);
    }

    // 删除一条(行)数据
    public static void delRow(String tableName, String row) throws Exception {
        HTable table = new HTable(conf, tableName);
        Delete del = new Delete(Bytes.toBytes(row));
        table.delete(del);
    }

    // 删除多条数据
    public static void delMultiRows(String tableName, String[] rows)
            throws Exception {
        HTable table = new HTable(conf, tableName);
        List<Delete> delList = new ArrayList<Delete>();
        for (String row : rows) {
            Delete del = new Delete(Bytes.toBytes(row));
            delList.add(del);
        }
        table.delete(delList);
    }

    // 获取一条数据
    public static void getRow(String tableName, String row) throws Exception {
        HTable table = new HTable(conf, tableName);
        Get get = new Get(Bytes.toBytes(row));
        Result result = table.get(get);
        // 输出结果,raw方法返回所有keyvalue数组
        for (KeyValue rowKV : result.raw()) {
            System.out.print("行名:" + new String(rowKV.getRow()) + " ");
            System.out.print("时间戳:" + rowKV.getTimestamp() + " ");
            System.out.print("列族名:" + new String(rowKV.getFamily()) + " ");
            System.out.print("列名:" + new String(rowKV.getQualifier()) + " ");
            System.out.println("值:" + new String(rowKV.getValue()));
        }
    }

    // 获取所有数据
    public static void getAllRows(String tableName) throws Exception {
        HTable table = new HTable(conf, tableName);
        Scan scan = new Scan();
        ResultScanner results = table.getScanner(scan);
        // 输出结果
        for (Result result : results) {
            for (KeyValue rowKV : result.raw()) {
                System.out.print("行名:" + new String(rowKV.getRow()) + " ");
                System.out.print("时间戳:" + rowKV.getTimestamp() + " ");
                System.out.print("列族名:" + new String(rowKV.getFamily()) + " ");
                System.out
                        .print("列名:" + new String(rowKV.getQualifier()) + " ");
                System.out.println("值:" + new String(rowKV.getValue()));
            }
        }
    }

    /**
     * 获取历史数据接口
     *
     * @param tableName 表名
     * @param value     列值
     * @return
     * @throws Exception
     * @author barry.Yu
     */
    public static Map<String, Object> getIotDevice(String tableName, String col, String value) throws Exception {

        boolean exist = isExist(tableName);
        Map<String, Object> map = new HashMap<>();

        if (!exist) {
            LOG.info("查询----" + tableName + "不存在！");
            System.out.println("查询----" + tableName + "不存在！");

            return map;
        }

        HTable table = new HTable(conf, tableName);
        Scan scan = new Scan();
        scan.setReversed(true);
        try {


            FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);
            //Filter eqFilter = setFilter(value);
            //设置取的条数
            Filter pageFilter = new PageFilter(1);
            //filterList.addFilter(eqFilter);
            filterList.addFilter(pageFilter);
            scan.setFilter(filterList);
            ResultScanner scanner = table.getScanner(scan);


            for (Result result : scanner) {
                for (KeyValue rowKV : result.raw()) {
                /*System.out.print("行名:" + new String(rowKV.getRow()) + " ");
                System.out.print("时间戳:" + rowKV.getTimestamp() + " ");
                System.out.print("列族名:" + new String(rowKV.getFamily()) + " ");
                System.out
                        .print("列名:" + new String(rowKV.getQualifier()) + " ");
                System.out.println("值:" + new String(rowKV.getValue()));*/
                    map.put(new String(rowKV.getQualifier()), new String(rowKV.getValue()));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return map;
        }

        return map;
    }


    /**
     * @param tableName 表名
     * @param startRow 开始时间
     * @param endRow   结束时间
     * @return
     * @throws Exception
     * @author 作者 ：walker
     * @time 创建时间：2018年2月27日上午10:16:22
     * @Describe 描述：
     * @class HBaseTemplate.java
     */
    @SuppressWarnings("deprecation")
    public static List<Map<String, Object>> getListMap(String tableName, String startRow, String endRow) throws Exception {
       // HttpSolrClient solrClient = SolrUtil.solrClient;
        boolean exist = isExist(tableName);
        List<Map<String, Object>> mapList = new ArrayList<>();
        if (!exist) {
            LOG.info("查询----" + tableName + "不存在！");
            System.out.println("查询----" + tableName + "不存在！");
            return mapList;
        }

        Table table = new HTable(conf, tableName);
        Scan scan = new Scan();
        SingleColumnValueFilter columFilter=new SingleColumnValueFilter(Bytes.toBytes("IOT"),Bytes.toBytes("k"),CompareOp.EQUAL,Bytes.toBytes("feed"));
        columFilter.setFilterIfMissing(true);
        scan.setFilter(columFilter);
        ResultScanner scanner = null;
        try {
            scanner = table.getScanner(scan);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<String> keys=new ArrayList<>();
        keys.add("k");
        keys.add("v");
        keys.add("t");
        keys.add("orgId");
        keys.add("createTime");
        for (Result result : scanner) {
            Map<String, Object> map1 = new HashMap<>();
            SolrInputDocument document=new SolrInputDocument();
            String row= null;
            for (KeyValue rowKV : result.raw()) {
                String key = new String(rowKV.getQualifier());
                row=new String(rowKV.getRow());
                String value=new String(rowKV.getValue());
                map1.put("rowKey",row );
                map1.put(key, new String(rowKV.getValue()));
                if(keys.contains(key)){
                    System.out.println(value +" ===  " +key);
                    document.addField(key,value);
                }
            }
            if(row != null){
                document.addField("rowkey",row);
            }

           // solrClient.add(document);
          //  solrClient.commit();
            mapList.add(map1);
        }

       // solrClient.close();
        scanner.close();
        return mapList;
    }

}