package com.duke.framework.redisson.config;

import com.duke.framework.redisson.RedissonProperties;
import com.duke.framework.redisson.distributed.locker.DistributedLocker;
import com.duke.framework.redisson.distributed.locker.impl.DistributedLockerImpl;
import com.duke.framework.redisson.util.RedissLockUtil;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * Created duke on 2018/7/27
 */
@Configuration
@ConditionalOnClass(RedissonProperties.class)
@EnableConfigurationProperties(RedissonProperties.class)
@ConditionalOnProperty(value = "duke.redisson.enabled", havingValue = "true", matchIfMissing = true)
public class RedissonAutoConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedissonAutoConfiguration.class);

    @Autowired
    private RedissonProperties redissonProperties;

    public RedissonAutoConfiguration() {
        LOGGER.info("redisson starter autoconfig");
    }

    /**
     * 哨兵模式自动装配
     *
     * @return RedissonClient
     */
    @Bean
    @ConditionalOnProperty(name = "redisson.master-name")
    RedissonClient redissonSentinel() {
        Config config = new Config();
        SentinelServersConfig serverConfig = config.useSentinelServers().addSentinelAddress(redissonProperties.getSentinelAddresses())
                .setMasterName(redissonProperties.getMasterName())
                .setTimeout(redissonProperties.getTimeout())
                .setMasterConnectionPoolSize(redissonProperties.getMasterConnectionPoolSize())
                .setSlaveConnectionPoolSize(redissonProperties.getSlaveConnectionPoolSize());

        if (StringUtils.isEmpty(redissonProperties.getPassword())) {
            serverConfig.setPassword(redissonProperties.getPassword());
        }
        return Redisson.create(config);
    }

    /**
     * 单机模式自动装配
     *
     * @return RedissonClient
     */
    @Bean
    @ConditionalOnProperty(name = "duke.redisson.address")
    RedissonClient redissonSingle() {
        Config config = new Config();
        SingleServerConfig serverConfig = config.useSingleServer()
                .setAddress(redissonProperties.getAddress())
                .setTimeout(redissonProperties.getTimeout())
                .setConnectionPoolSize(redissonProperties.getConnectionPoolSize())
                .setConnectionMinimumIdleSize(redissonProperties.getConnectionMinimumIdleSize());

        if (StringUtils.isEmpty(redissonProperties.getPassword())) {
            serverConfig.setPassword(redissonProperties.getPassword());
        }

        return Redisson.create(config);
    }

    /**
     * 装配locker类，并将实例注入到RedissLockUtil中
     *
     * @return
     */
    @Bean
    DistributedLocker distributedLocker(RedissonClient redissonClient) {
        DistributedLocker locker = new DistributedLockerImpl(redissonClient);
        RedissLockUtil.setLocker(locker);
        return locker;
    }
}
