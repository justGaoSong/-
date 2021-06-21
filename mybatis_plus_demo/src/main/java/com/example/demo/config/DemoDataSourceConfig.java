package com.example.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;


@Configuration
@MapperScan(basePackages = {"com.example.demo.mapper.demo"}, sqlSessionFactoryRef = "demoSqlSessionFactory")
public class DemoDataSourceConfig extends AbstractDataSourceConfig {


    // 精确到 mapper 目录，以便跟其他数据源隔离
    private static final String MAPPER_LOCATION = "classpath:/mapper/demo/*.xml";


    @Value("jdbc:mysql://${db.mysql.demo.user.host}:${db.mysql.demo.user.port}/demobase?connectTimeout=2000&socketTimeout=600000&characterEncoding=UTF-8")
    private String url;

    @Value("${db.mysql.demo.user}")
    private String user;

    @Value("${db.mysql.demo.password}")
    private String password;

    @Value("${spring.datasource.demo.driver-class-name}")
    private String driverClass;

    @Bean("demoDataSource")
    public DataSource demoDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean(name = "demoTransactionManager")
    public DataSourceTransactionManager demoTransactionManager(@Qualifier("demoDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "demoSqlSessionFactory")
    public SqlSessionFactory demoSqlSessionFactory(@Qualifier("demoDataSource") DataSource dataSource) throws Exception {
        return createSqlSessionFactory(dataSource);
    }

    @Bean(name = "demoSessionTemplate")
    public SqlSessionTemplate wareSessionTemplate(@Qualifier("demoSqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Override
    public String getMapperLocation() {
        return MAPPER_LOCATION;
    }
}
