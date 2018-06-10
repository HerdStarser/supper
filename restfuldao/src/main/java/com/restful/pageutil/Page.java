package com.restful.pageutil;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Page<T> {
	
    private Integer currentPage;         //页码
    
    private Integer pageSize;			 //每页显示的记录数
    
    private int totalRecord;			 //总记录数
    
    private int totalPage;				 //总页数
    
    private List<T> results;			 //对应的当前页记录
    
    private Map<String, Object> params = Collections.emptyMap();      //封装其他参数

    public Integer getCurrentPage() {
       return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
       this.currentPage = currentPage;
    }

    public Integer getPageSize() {
       return pageSize;
    }

    public void setPageSize(Integer pageSize) {
       this.pageSize = pageSize;
    }

    public int getTotalRecord() {
       return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
       this.totalRecord = totalRecord;
       int totalPage = (int) Math.ceil(totalRecord*1.0/pageSize);
       this.setTotalPage(totalPage);
    }

    public int getTotalPage() {
       return totalPage;
    }

    public void setTotalPage(int totalPage) {
       this.totalPage = totalPage;
    }

    public List<T> getResults() {
       return results;
    }

    public void setResults(List<T> results) {
       this.results = results;
    }

    public Map<String, Object> getParams() {
       return params;
    }

    public void setParams(Map<String, Object> params) {
       this.params = params;
    }

    @Override
    public String toString() {
       StringBuilder builder = new StringBuilder();
       builder.append("Page [currentPage=").append(currentPage).append(", pageSize=")
              .append(pageSize).append(", results=").append(results).append(
                     ", totalPage=").append(totalPage).append(
                     ", totalRecord=").append(totalRecord).append("]");
       return builder.toString();
    }



}