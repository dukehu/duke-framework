package com.duke.framework.swagger.config;

import com.duke.framework.swagger.SwaggerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Created duke on 2018/6/20
 */
@Configuration
@EnableSwagger2
@Import({
        BeanValidatorPluginsConfiguration.class
})
@ConditionalOnClass({ApiInfo.class, BeanValidatorPluginsConfiguration.class})
@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerAutoConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(SwaggerAutoConfiguration.class);

    public SwaggerAutoConfiguration() {
        LOGGER.info("swagger-spring-boot-starter autoconfigure");
    }

    /**
     * matchIfMissing = true，表示如果没有在application.properties设置该属性，则默认为条件符合
     */
    @Bean
    @ConditionalOnProperty(value = "duke.swagger.enabled", havingValue = "true", matchIfMissing = true)
    public Docket swaggerDocket(SwaggerProperties swaggerProperties) {
        System.out.println("swagger starter autoconfig");
        ApiInfo apiInfo = new ApiInfo(
                swaggerProperties.getTitle(),
                swaggerProperties.getDescription(),
                swaggerProperties.getVersion(),
                swaggerProperties.getTermsOfServiceUrl(),
                new Contact("", "", ""),
                swaggerProperties.getLicense(),
                swaggerProperties.getLicenseUrl()
        );

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .forCodeGeneration(true)
                .directModelSubstitute(java.nio.ByteBuffer.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .select()
                .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()))
                .paths(regex(".*"))
                .build();
    }

}
