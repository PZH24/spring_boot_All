//package com.example.spring_boot_mybatis_multidatasouce.config;
//
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.mybatis.spring.annotation.MapperScan;
//import javax.sql.DataSource;
//
//@Configuration
//@MapperScan(basePackages="com.example.spring_boot_mybatis_multidatasouce.pojo.mapper",sqlSessionTemplateRef = "test1SqlSessionTemplate")
//public class Test1DataSourceConfig {
//    @Bean(name = "Test1DataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.test1")
//    @Primary
//    public DataSource dataSource(){
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean(name = "test1sqlSessionFactory")
//    @Primary
//    public SqlSessionFactory sqlSessionFactory(@Qualifier("Test1DataSource") DataSource dataSource) throws Exception{
//        SqlSessionFactoryBean  sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        sqlSessionFactoryBean.setDataSource(dataSource);
////        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/test1/*.xml"));
//        return  sqlSessionFactoryBean.getObject();
//    }
//    @Bean(name = "test1TransactionManager")
//    @Primary
//    public DataSourceTransactionManager  dataSourceTransactionManager(@Qualifier("Test1DataSource") DataSource dataSource)
//        throws Exception{
//        return  new DataSourceTransactionManager(dataSource);
//    }
//    @Bean(name = "test1SqlSessionTemplate")
//    @Primary
//    public SqlSessionTemplate  sqlSessionTemplate (@Qualifier("test1sqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception{
//        return  new SqlSessionTemplate(sqlSessionFactory);
//    }
//}
