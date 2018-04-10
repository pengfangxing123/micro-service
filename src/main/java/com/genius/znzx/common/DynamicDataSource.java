package com.genius.znzx.common;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource   
{  		
    @Override  
    protected Object determineCurrentLookupKey()  
    {  
        return DataSourceSwitch.getDataSourceType();  
    }

}