package com.duke.framework.redisson;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created duke on 2018/6/20
 */
@ConfigurationProperties(prefix = "duke.redisson", ignoreInvalidFields = true)
public class RedissonProperties {

    /**
     * 是否开启redisson自动配置
     */
    private Boolean enabled = true;

    /**
     * 超时时间
     */
    private int timeout = 3000;

    /**
     * redis地址
     */
    private String address;

    /**
     * 密码
     */
    private String password;

    /**
     * 数据库编号，默认值：0
     */
    private int database = 0;

    /**
     * 连接池最大容量。默认值：64
     */
    private int connectionPoolSize = 64;

    /**
     * 最小空闲连接数,默认值:10
     */
    private int connectionMinimumIdleSize = 10;

    /**
     *
     */
    private int slaveConnectionPoolSize = 250;

    /**
     *
     */
    private int masterConnectionPoolSize = 250;

    private String[] sentinelAddresses;

    private String masterName;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    public int getConnectionPoolSize() {
        return connectionPoolSize;
    }

    public void setConnectionPoolSize(int connectionPoolSize) {
        this.connectionPoolSize = connectionPoolSize;
    }

    public int getConnectionMinimumIdleSize() {
        return connectionMinimumIdleSize;
    }

    public void setConnectionMinimumIdleSize(int connectionMinimumIdleSize) {
        this.connectionMinimumIdleSize = connectionMinimumIdleSize;
    }

    public int getSlaveConnectionPoolSize() {
        return slaveConnectionPoolSize;
    }

    public void setSlaveConnectionPoolSize(int slaveConnectionPoolSize) {
        this.slaveConnectionPoolSize = slaveConnectionPoolSize;
    }

    public int getMasterConnectionPoolSize() {
        return masterConnectionPoolSize;
    }

    public void setMasterConnectionPoolSize(int masterConnectionPoolSize) {
        this.masterConnectionPoolSize = masterConnectionPoolSize;
    }

    public String[] getSentinelAddresses() {
        return sentinelAddresses;
    }

    public void setSentinelAddresses(String[] sentinelAddresses) {
        this.sentinelAddresses = sentinelAddresses;
    }

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }
}
