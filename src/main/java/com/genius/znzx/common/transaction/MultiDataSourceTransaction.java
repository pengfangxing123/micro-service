package com.genius.znzx.common.transaction;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.sql.DataSource;

import org.apache.ibatis.transaction.Transaction;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.datasource.DataSourceUtils;

import com.genius.znzx.common.DataSourceSwitch;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * 多数据源切换，支持事务
 * 
 * 我们配置了事物管理器和拦截Service中的方法后，每次执行Service中方法前会开启一个事务，
 * 并且同时会缓存一些东西：DataSource、SqlSessionFactory、Connection等，
 * 所以，我们在外面再怎么设置要求切换数据源也没用，因为Conneciton都是从缓存中拿的，
 * 所以我们要想能够顺利的切换数据源，实际就是能够动态的根据DatabaseType获取不同的Connection，
 * 并且要求不能影响整个事物的特性
 * 这个方法只能实现回滚一个数据源 ，conn.getAutoCommit();第二个数据源是false ，没有被事务处理，是直接提交的
 * 所以该配置意义不大，想实现得用分布式事务管理
 * @author fangxing.peng
 *
 */

@Configuration
public class MultiDataSourceTransaction implements Transaction {
	private static Logger log = Logger.getLogger(MultiDataSourceTransaction.class);
	
	private DataSource dataSource;
	
	private Connection mainConnection;  
	
	private ConcurrentMap<String, Connection> otherConnectionMap; 
	
	private String mainDatabaseIdentification; 
	
    private boolean isConnectionTransactional;  
    
    private boolean autoCommit;  
	
	public MultiDataSourceTransaction(DataSource dataSource){
		notNull(dataSource,"No DataSource specified");
		this.dataSource = dataSource;  
		otherConnectionMap = new ConcurrentHashMap<>();
		mainDatabaseIdentification=DataSourceSwitch.getDataSourceType();  
	}
	

	@Override
	public Connection getConnection() throws SQLException {	
		String databaseIdentification = DataSourceSwitch.getDataSourceType(); 
		if(databaseIdentification==mainDatabaseIdentification||databaseIdentification.equals(mainDatabaseIdentification)){
			if(mainConnection!=null)return mainConnection;
			else{
                openMainConnection();  
                mainDatabaseIdentification =databaseIdentification;  
                return mainConnection; 
			}
		}else{
			if (!otherConnectionMap.containsKey(databaseIdentification)) {  
                try {  
                    Connection conn = dataSource.getConnection();  
                    //boolean autoCommit2 = conn.getAutoCommit();第二个数据源是false ，没有被事务处理
                    //boolean autoCommit1=DataSourceUtils.isConnectionTransactional(conn, dataSource);  
                    otherConnectionMap.put(databaseIdentification, conn);  
                } catch (SQLException ex) {  
                    throw new CannotGetJdbcConnectionException("Could not get JDBC Connection", ex);  
                }   
            }  
            return otherConnectionMap.get(databaseIdentification);  
		}
	}
	
    private void openMainConnection() throws SQLException {  
        this.mainConnection = DataSourceUtils.getConnection(this.dataSource);  
        this.autoCommit = this.mainConnection.getAutoCommit();  
        this.isConnectionTransactional = DataSourceUtils.isConnectionTransactional(this.mainConnection, this.dataSource);  
  
        if (log.isDebugEnabled()) {  
        	log.debug(  
                    "JDBC Connection ["  
                            + this.mainConnection  
                            + "] will"  
                            + (this.isConnectionTransactional ? " " : " not ")  
                            + "be managed by Spring");  
        }  
    } 

	@Override
	public void commit() throws SQLException {
		if (this.mainConnection != null && !this.isConnectionTransactional && !this.autoCommit) {  
            if (log.isDebugEnabled()) {  
            	log.debug("Committing JDBC Connection [" + this.mainConnection + "]");  
            }  
            this.mainConnection.commit();  
            for (Connection connection : otherConnectionMap.values()) {  
                connection.commit();  
            }  
        }  
		
	}

	@Override
	public void rollback() throws SQLException {
        if (this.mainConnection != null && !this.isConnectionTransactional && !this.autoCommit) {  
            if (log.isDebugEnabled()) {  
            	log.debug("Rolling back JDBC Connection [" + this.mainConnection + "]");  
            }  
            this.mainConnection.rollback();  
            for (Connection connection : otherConnectionMap.values()) {  
                connection.rollback();  
            }  
        }  
	}

	@Override
	public void close() throws SQLException {
        DataSourceUtils.releaseConnection(this.mainConnection, this.dataSource);  
        for (Connection connection : otherConnectionMap.values()) {  
            DataSourceUtils.releaseConnection(connection, this.dataSource);  
        } 
	}

	@Override
	public Integer getTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
 
}