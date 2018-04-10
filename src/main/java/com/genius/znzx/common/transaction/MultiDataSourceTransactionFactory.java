package com.genius.znzx.common.transaction;

import javax.sql.DataSource;

import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.transaction.Transaction;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * 支持Service内多数据源切换的Factory
 * @author fangxing.peng
 *
 */
public class MultiDataSourceTransactionFactory extends SpringManagedTransactionFactory {
    @Override  
    public Transaction newTransaction(@Qualifier("dynamicDataSource")DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit) {  
        return new MultiDataSourceTransaction(dataSource);  
    } 
}
