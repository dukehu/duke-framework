package com.duke.framework.sqllog.config;

import com.duke.framework.sqllog.interceptor.MybatisSqlLogInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created duke on 2018/6/28
 */
@Configuration
@ConditionalOnProperty(value = "duke.sqllog.enabled", havingValue = "true", matchIfMissing = true)
public class SqllogAutoConfiguration {

    @Autowired
    private List<SqlSessionFactory> sqlSessionFactories;

    /**
     * 这里因为在addMybatisSqlLogInterceptor之前是需要SqlSessionFactory依赖进来的，所以要使用@PostConstruct注解
     */
    @PostConstruct
    public void addMybatisSqlLogInterceptor() {
        System.out.println("sqllog starter autoconfig");
        Interceptor interceptor = new MybatisSqlLogInterceptor();

        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactories) {
            sqlSessionFactory.getConfiguration().addInterceptor(interceptor);
        }
    }
}
