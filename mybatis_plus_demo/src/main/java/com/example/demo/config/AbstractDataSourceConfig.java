package com.example.demo.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.annotation.Resource;
import javax.sql.DataSource;

public abstract class AbstractDataSourceConfig {

    @Resource
    private PaginationInterceptor paginationInterceptor;

    public abstract String getMapperLocation();

    public SqlSessionFactory createSqlSessionFactory(DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(getMapperLocation()));
        Interceptor[] plugins = new Interceptor[1];
        plugins[0] = paginationInterceptor;
        bean.setPlugins(plugins);
        return bean.getObject();
    }
}
