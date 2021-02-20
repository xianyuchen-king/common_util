package com.chenyqx.jdbc.example.jdbc_util.jdbctemplate;

import com.alibaba.druid.pool.DruidDataSource;
import com.chenyqx.jdbc.example.jdbc_util.jdbc.JDBCUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

/**
 * @ClassName : DBConfig
 * @Description :
 * @Author : chenyqx
 * @Date: 2021-02-07 21:20
 */
@Configuration
public class DBConfig {
    @Bean
    public DataSource dataSource(){
        return JDBCUtils.getDataSource();
    }

    @Bean
    public DataSourceTransactionManager transactionManager(){
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public TransactionTemplate transactionTemplate(){
        return new TransactionTemplate(transactionManager());
    }
}
