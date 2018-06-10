package com.restful.pageutil;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;


/** 
* @author 作者 ：walker 
* @time 创建时间：2018年5月24日 上午10:38:09 
* @describe 
*/

@Intercepts({ @Signature(method = "prepare", type = StatementHandler.class, args = { Connection.class }) })
public class PageInterceptor implements Interceptor{
	
	private String dialect ="mysql";                // 数据库方言
	
    private String regSqlId = "*Page*"; 			// mapper.xml中需要拦截的ID(正则匹配)
    
    private int pageSize = 10;						//默认10条
    
    private int currentPage = 1;					//当前页是从第一页开始

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		
		if(invocation.getTarget() instanceof RoutingStatementHandler){
			 RoutingStatementHandler statementHandler = (RoutingStatementHandler) invocation.getTarget();
			 StatementHandler handler = (StatementHandler) ReflectUtil.getFieldValue(statementHandler, "dialect");
			 //获取要执行的sql
			 BoundSql boundSql = handler.getBoundSql();
			 String sql = boundSql.getSql();
			 //校验sql是否是select，只有查询才需要分页
			 if(!checkSqlIsSelect(sql)){
				 return invocation.proceed();
			 }
			 Object param = boundSql.getParameterObject();
			 //获取参数
			 Page<?> page=getPageParam(param);
			 if(page == null){
				 return invocation.proceed();
			 }
			 //初始化每页显示的条数
			 if(page.getPageSize() == null || page.getPageSize() <= 0){
				 page.setPageSize(this.pageSize);
			 }
			 MappedStatement mappedStatement = (MappedStatement) ReflectUtil.getFieldValue(handler,"mappedStatement");
			 Connection connection = (Connection) invocation.getArgs()[0];
			 //设置总页数
			 setTotalRecord(page,param,mappedStatement,connection);
			 //检查传递的页码是否正确，如果不正确，设置成当前页或者最大页
			 checkPage(page);
			 //获取分页用sql
			 String pagesql = getPageSql(page,sql);
			 ReflectUtil.setFieldValue(boundSql, "sql", pagesql);
		}
		return invocation.proceed();
	}


	@Override
	public Object plugin(Object obj) {
		return Plugin.wrap(obj, this);
	}

	@Override
	public void setProperties(Properties properties) {
		
	}
	
	/** 
	* @author 作者 ：walker 
	* @time 创建时间：2018年5月24日下午12:19:53
	* @Describe 描述：检查要执行的sql是否是select，如果不是则没必要分页
	* @class PageInterceptor.java
	* @return Boolean
	* @param sql
	* @return 
	*/ 
	private Boolean checkSqlIsSelect(String sql) {
		String trimSql = sql.trim();
        int index = trimSql.toLowerCase().indexOf("select");
        return index == 0;
	}
	
	/** 
	* @author 作者 ：walker 
	* @time 创建时间：2018年5月24日下午12:19:40
	* @Describe 描述：获取分页对象
	* @class PageInterceptor.java
	* @return Page<?>
	* @param param
	* @return 
	*/ 
	private Page<?> getPageParam(Object param) {
		if (param instanceof Page<?>) {
            return (Page<?>) param;
        } else if (param instanceof Map) {
            for (Object val : ((Map<?, ?>) param).values()) {
                if (val instanceof Page<?>) {
                    return (Page<?>) val;
                }
            }
        }
        return null;
	}
	
	/** 
	* @author 作者 ：walker 
	* @time 创建时间：2018年5月24日下午12:19:16
	* @Describe 描述：给当前对象设置总记录数
	* @class PageInterceptor.java
	* @return void
	* @param page
	* @param param
	* @param mappedStatement
	* @param connection 
	*/ 
	private void setTotalRecord(Page<?> page, Object param, MappedStatement mappedStatement, Connection connection) {
		BoundSql boundSql = mappedStatement.getBoundSql(page);
		String sql = boundSql.getSql();
		String countsql = getCount(sql);
		List<ParameterMapping> mappings = boundSql.getParameterMappings();
		BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(), sql, mappings, param);
		ParameterHandler handler = new DefaultParameterHandler(mappedStatement, param, countBoundSql);
		PreparedStatement prepare = null;
		ResultSet resultSet = null;
		try {
			prepare = connection.prepareStatement(countsql);
			handler.setParameters(prepare);
			resultSet = prepare.executeQuery();
			if(resultSet.next()) {
				page.setTotalRecord(resultSet.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(prepare != null){
					prepare.close();
				}
				if(resultSet != null){
					resultSet.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	/** 
	* @author 作者 ：walker 
	* @time 创建时间：2018年5月24日下午12:22:42
	* @Describe 描述：根据原有的sql生成获取总条数的sql
	* @class PageInterceptor.java
	* @return String
	* @param sql
	* @return 
	*/ 
	private String getCount(String sql) {
        int index = sql.indexOf("from");
        return "select count(1) " + sql.substring(index);
    }
	
	/** 
	* @author 作者 ：walker 
	* @time 创建时间：2018年5月24日下午12:57:30
	* @Describe 描述：检查page的参数是否正确，不正确改成正确的
	* @class PageInterceptor.java
	* @return void
	* @param page 
	*/ 
	private void checkPage(Page<?> page) {
		if(page.getCurrentPage() == null || page.getCurrentPage() <= 1){
			page.setCurrentPage(this.currentPage);
		}
		if(page.getCurrentPage() >= page.getTotalPage()){
			page.setCurrentPage(page.getTotalPage());
		}
	}
	
	/** 
	* @author 作者 ：walker 
	* @time 创建时间：2018年5月24日下午12:50:08
	* @Describe 描述：获取分页sql
	* @class PageInterceptor.java
	* @return String
	* @param page
	* @param sql
	* @return 
	*/ 
	private String getPageSql(Page<?> page, String sql) {
		StringBuffer sqlBuffer = new StringBuffer(sql);
		if ("mysql".equalsIgnoreCase(dialect)) {
            return getMysqlPageSql(page, sqlBuffer);
        } else if ("oracle".equalsIgnoreCase(dialect)) {
            return getOraclePageSql(page, sqlBuffer);
        }
		return sql.toString();
	}

	
	/** 
	* @author 作者 ：walker 
	* @time 创建时间：2018年5月24日下午12:50:19
	* @Describe 描述：获取oracle的分页sql
	* @class PageInterceptor.java
	* @return String
	* @param page
	* @param sqlBuffer
	* @return 
	*/ 
	private String getOraclePageSql(Page<?> page, StringBuffer sqlBuffer) {
		int offset = (page.getCurrentPage() - 1) * page.getPageSize() + 1;
        sqlBuffer.insert(0, "select u.*, rownum r from (").append(") u where rownum < ")
                .append(offset + page.getPageSize());
        sqlBuffer.insert(0, "select * from (").append(") where r >= ").append(offset);
		return sqlBuffer.toString();
	}

	/** 
	* @author 作者 ：walker 
	* @time 创建时间：2018年5月24日下午12:50:33
	* @Describe 描述：获取mysql的分页sql
	* @class PageInterceptor.java
	* @return String
	* @param page
	* @param sqlBuffer
	* @return 
	*/ 
	private String getMysqlPageSql(Page<?> page, StringBuffer sqlBuffer) {
		int offset = (page.getCurrentPage() - 1) * page.getPageSize();
		sqlBuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
		return sqlBuffer.toString();
	}


	public String getDialect() {
		return dialect;
	}


	public void setDialect(String dialect) {
		this.dialect = dialect;
	}


	public String getRegSqlId() {
		return regSqlId;
	}


	public void setRegSqlId(String regSqlId) {
		this.regSqlId = regSqlId;
	}


	public int getPageSize() {
		return pageSize;
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	public int getCurrentPage() {
		return currentPage;
	}


	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
}
