package com.faithbj.oauth.as.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
/**
 *
 * @author xueyongfei01
 *
 */
@Configuration
@EnableTransactionManagement
@MapperScan("com.faithbj.oauth.as.mapper")
public class DBConfig {


    private static final String driverClassName = "com.mysql.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost:3306/oauth3?useUnicode=true&characterEncoding=utf-8";
    private static final String username = "oauth3";
    private static final String password = "oauth-test12#";


    @Bean(destroyMethod = "close", name = "dataSource")
    public DataSource dataSource() {
        System.out.println("-------------------jdbc.url is"+url);
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setMaxTotal(10);
        dataSource.setMinIdle(5);
        dataSource.setMaxIdle(10000);
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public SqlSessionFactoryBean oauthSessionFactory()  throws Exception {
        SqlSessionFactoryBean ssfb = new SqlSessionFactoryBean();
        System.out.println("------------------------------"+dataSource().toString());
        ssfb.setDataSource(dataSource());
        Resource resource = new ClassPathResource("mybatis-config.xml");
        ssfb.setConfigLocation(resource);
        ssfb.setTypeAliasesPackage("com.faithbj.oauth.as.domain");
        return ssfb;
    }


    @Bean
    public MapperScannerConfigurer oauthMapperScanner() {
        MapperScannerConfigurer msc = new MapperScannerConfigurer();
        msc.setSqlSessionFactoryBeanName("oauthSessionFactory");
        msc.setBasePackage("com.faithbj.oauth.as.mapper");
        return msc;
    }

}
