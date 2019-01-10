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
@MapperScan(basePackages="com.example.spring_boot_mybatis_multidatasouce.imp.mapper1",sqlSessionTemplateRef = "xmltest1SqlSessionTemplate")
public class xmlTest1DataSourceConfig {
    @Bean(name = "xmlTest1DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.test1")
    @Primary
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
//        return dataSourceProperties().initializeDataSourceBuilder().build();
    }
//    @Primary
//    @Bean(name = "xmltest1DataSourceProperties")
//    @Qualifier("xmltest1DataSourceProperties")
//    @ConfigurationProperties(prefix = "spring.datasource.test1")
//    public DataSourceProperties dataSourceProperties(){
//        return new DataSourceProperties();
//    }
    @Bean(name = "xmltest1sqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("xmlTest1DataSource") DataSource dataSource) throws Exception{
        SqlSessionFactoryBean  sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        //xml需备注引入映射的xml文件setMapperLocations
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/test1/*.xml"));
        return  sqlSessionFactoryBean.getObject();
    }
    @Bean(name = "xmltest1TransactionManager")
    @Primary
    public DataSourceTransactionManager  dataSourceTransactionManager(@Qualifier("xmlTest1DataSource") DataSource dataSource)
            throws Exception{
        return  new DataSourceTransactionManager(dataSource);
    }
    @Bean(name = "xmltest1SqlSessionTemplate")
    @Primary
    public SqlSessionTemplate  sqlSessionTemplate (@Qualifier("xmltest1sqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception{
        return  new SqlSessionTemplate(sqlSessionFactory);
    }
}
