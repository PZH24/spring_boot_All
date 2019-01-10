//package com.example.spring_boot_mybatis_multidatasouce.config;
//
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//
//import javax.sql.DataSource;
//@Configuration
//@MapperScan(basePackages = "com.example.spring_boot_mybatis_multidatasouce.pojo.mapper2",sqlSessionTemplateRef = "test2SqlSessionTemplate")
//public class Test2DataSourceConfig {
//    @Bean(name = "Test2DataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.test2")
//    public DataSource dataSource(){
////        return DataSourceBuilder.create().build();
//        return dataSourceProperties().initializeDataSourceBuilder().build();
//    }
//    @Primary
//    @Bean(name = "test2DataSourceProperties")
//    @Qualifier("test2DataSourceProperties")
//    @ConfigurationProperties(prefix = "spring.datasource.test2")
//    public DataSourceProperties dataSourceProperties(){
//        return new DataSourceProperties();
//    }
//    @Bean(name = "test2sqlSessionFactory")
//    public SqlSessionFactory sqlSessionFactory(@Qualifier("Test2DataSource") DataSource dataSource) throws Exception{
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        sqlSessionFactoryBean.setDataSource(dataSource);
////        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/test1/*.xml"));
//        return  sqlSessionFactoryBean.getObject();
//    }
//    @Bean(name = "test2TransactionManager")
//    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("Test2DataSource") DataSource dataSource)
//            throws Exception{
//        return  new DataSourceTransactionManager(dataSource);
//    }
//    @Bean(name = "test2SqlSessionTemplate")
//    public SqlSessionTemplate sqlSessionTemplate (@Qualifier("test2sqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception{
//        return  new SqlSessionTemplate(sqlSessionFactory);
//    }
//}
