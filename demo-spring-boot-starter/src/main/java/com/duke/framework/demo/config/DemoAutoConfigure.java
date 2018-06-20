package com.duke.framework.demo.config;

import com.duke.framework.demo.DemoProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created duke on 2018/6/20
 */
@Configuration
@ConditionalOnClass(DemoService.class)
@EnableConfigurationProperties(DemoProperties.class)
public class DemoAutoConfigure {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoAutoConfigure.class);

    @Autowired
    private DemoProperties demoProperties;

    public DemoAutoConfigure() {
        LOGGER.info("demo-spring-boot-starter autoconfigure");
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "duke.demo", value = "enabled", havingValue = "true")
    DemoService demoService() {
        LOGGER.info("###########################################################################");
        return new DemoService(demoProperties.getPrefix(), demoProperties.getSuffix());
    }

}
