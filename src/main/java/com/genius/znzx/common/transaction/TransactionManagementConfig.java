package com.genius.znzx.common.transaction;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 事物控制
 * @author fangxing.peng
 *
 */
@EnableTransactionManagement
@Configuration
public class TransactionManagementConfig  {
 
    @Bean(name="mysqlTransactionManager")
    public PlatformTransactionManager mysqlTransactionManager(DataSource myqlDataSource)
    {
        return new DataSourceTransactionManager(myqlDataSource);
    }
}