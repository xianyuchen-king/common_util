package com.chenyqx.util.jdbc.jdbctemplate;

import com.chenyqx.util.jdbc.jdbc.JDBCUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
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
