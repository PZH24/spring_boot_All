package com.example.spring_boot_mybatis_multidatasouce.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.mybatis.spring.annotation.MapperScan;
import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages="com.example.spring_boot_mybatis_multidatasouce.imp.mapper2",sqlSessionTemplateRef = "xmltest2SqlSessionTemplate")
public class xmlTest2DataSourceConfig {
    @Bean(name = "xmlTest2DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.test2")
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
//        return dataSourceProperties().initializeDataSourceBuilder().build();
    }
//    @Primary
//    @Bean(name = "xmltest2DataSourceProperties")
//    @Qualifier("xmltest2DataSourceProperties")
//    @ConfigurationProperties(prefix = "spring.datasource.test2")
//    public DataSourceProperties dataSourceProperties(){
//        return new DataSourceProperties();
//    }

    @Bean(name = "xmltest2sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("xmlTest2DataSource") DataSource dataSource) throws Exception{
        SqlSessionFactoryBean  sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/test2/*.xml"));
        return  sqlSessionFactoryBean.getObject();
    }
    @Bean(name = "xmltest2TransactionManager")
    public DataSourceTransactionManager  dataSourceTransactionManager(@Qualifier("xmlTest2DataSource") DataSource dataSource)
            throws Exception{
        return  new DataSourceTransactionManager(dataSource);
    }
    @Bean(name = "xmltest2SqlSessionTemplate")
    public SqlSessionTemplate  sqlSessionTemplate (@Qualifier("xmltest2sqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception{
        return  new SqlSessionTemplate(sqlSessionFactory);
    }
}
