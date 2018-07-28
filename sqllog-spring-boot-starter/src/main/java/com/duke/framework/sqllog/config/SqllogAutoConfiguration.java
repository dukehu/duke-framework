package com.duke.framework.sqllog.config;

import com.duke.framework.sqllog.interceptor.MybatisSqlLogInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(SqllogAutoConfiguration.class);

    @Autowired
    private List<SqlSessionFactory> sqlSessionFactories;

    public SqllogAutoConfiguration() {
        LOGGER.info("sqllog starter autoconfig");
    }

    /**
     * 这里因为在addMybatisSqlLogInterceptor之前是需要SqlSessionFactory依赖进来的，所以要使用@PostConstruct注解
     */
    @PostConstruct
    public void addMybatisSqlLogInterceptor() {
        LOGGER.info("add mybatis sqllog interceptor");
        Interceptor interceptor = new MybatisSqlLogInterceptor();

        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactories) {
            sqlSessionFactory.getConfiguration().addInterceptor(interceptor);
        }
    }
}
