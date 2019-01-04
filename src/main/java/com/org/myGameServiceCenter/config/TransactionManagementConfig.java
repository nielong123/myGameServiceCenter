package com.org.myGameServiceCenter.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class TransactionManagementConfig {
    @Bean(name = "mainDataSourceTransactionManager")
    @Primary
    public PlatformTransactionManager
    firstDataSourceTransactionManager(
                @Qualifier("mainDataSource") DataSource firstDataSource) {
        return new DataSourceTransactionManager(firstDataSource);
    }
}
