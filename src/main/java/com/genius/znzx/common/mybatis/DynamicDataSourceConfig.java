package com.genius.znzx.common.mybatis;

import java.util.HashMap;
import java.util.Map;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.genius.znzx.common.DataSourceInstances;
import com.genius.znzx.common.DynamicDataSource;

@Configuration
public class DynamicDataSourceConfig {

    /**
     * @Primary 该注解表示在同一个接口有多个实现类可以注入的时候，默认选择哪一个，而不是让@autowire注解报错
     * @Qualifier 根据名称进行注入，通常是在具有相同的多个类型的实例的一个注入（例如有多个DataSource类型的实例）
     */
    @Bean(name="dynamicDataSource")
    @Primary
    public DynamicDataSource  dataSource(@Qualifier("datasource1") DataSource datasource1,
            @Qualifier("datasource2") DataSource datasource2) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceInstances.DATASOURCE1, datasource1);
        targetDataSources.put(DataSourceInstances.DATASOURCE2, datasource2);

        DynamicDataSource  dataSource = new DynamicDataSource ();
        dataSource.setTargetDataSources(targetDataSources);// 该方法是AbstractRoutingDataSource的方法
        dataSource.setDefaultTargetDataSource(datasource1);// 默认的datasource设置为datasource1

        return dataSource;
    }
}
