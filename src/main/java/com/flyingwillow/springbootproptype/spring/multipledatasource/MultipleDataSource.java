package com.flyingwillow.springbootproptype.spring.multipledatasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Created by liuxuhui on 2016/6/14.\
 * 多数据源配置
 */
public class MultipleDataSource extends AbstractRoutingDataSource {

    private final static ThreadLocal<String> dataSourceKey = new InheritableThreadLocal<String>();

    public static void setDataSourceKey(String dataSource){
        dataSourceKey.set(dataSource);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return dataSourceKey.get();
    }
}
