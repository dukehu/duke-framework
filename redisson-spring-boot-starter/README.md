# redisson-spring-boot-starter

参考：  
* [spring boot redisson starter的封装和使用](https://my.oschina.net/dengfuwei/blog/1603130)  
* [SpringBoot集成redisson分布式锁](https://www.cnblogs.com/yangzhilong/p/7605807.html)

```
/**
* redis链接地址
*/
private String address;

/**
* 最小空闲连接数,默认值:10,最小保持连接数（长连接）
*/
private int connectionMinimumIdleSize = 10;

/**
* 连接空闲超时，单位：毫秒 默认10000;当前连接池里的连接数量超过了最小空闲连接数，
* 而连接空闲时间超过了该数值，这些连接将会自动被关闭，并从连接池里去掉
*/
private int idleConnectionTimeout = 10000;

/**
* ping节点超时,单位：毫秒,默认1000
*/
private int pingTimeout = 1000;

/**
* 连接等待超时,单位：毫秒,默认10000
*/
private int connectTimeout = 10000;

/**
* 命令等待超时,单位：毫秒,默认3000；等待节点回复命令的时间。该时间从命令发送成功时开始计时
*/
private int timeout = 3000;

/**
* 命令失败重试次数，默认值:3
*/
private int retryAttempts = 3;

/**
* 命令重试发送时间间隔，单位：毫秒,默认值:1500
*/
private int retryInterval = 1500;

/**
* 重新连接时间间隔，单位：毫秒,默认值：3000;连接断开时，等待与其重新建立连接的时间间隔
*/
private int reconnectionTimeout = 3000;

/**
* 执行失败最大次数, 默认值：3；失败后直到 reconnectionTimeout超时以后再次尝试。
*/
private int failedAttempts = 3;

/**
* 密码
*/
private String password = null;

/**
* 单个连接最大订阅数量，默认值：5
*/
private int subscriptionsPerConnection = 5;

/**
* 客户端名称
*/
private String clientName = null;

/**
* 发布和订阅连接的最小空闲连接数，默认值：1；Redisson内部经常通过发布和订阅来实现许多功能。
* 长期保持一定数量的发布订阅连接是必须的
*/
private int subscriptionConnectionMinimumIdleSize = 1;

/**
* 发布和订阅连接池大小，默认值：50
*/
private int subscriptionConnectionPoolSize = 50;

/**
* 连接池最大容量。默认值：64；连接池的连接数量自动弹性伸缩
*/
private int connectionPoolSize = 64;

/**
* 数据库编号，默认值：0
*/
private int database = 0;

/**
* 是否启用DNS监测，默认值：false
*/
private boolean dnsMonitoring = false;

/**
* DNS监测时间间隔，单位：毫秒，默认值：5000
*/
private int dnsMonitoringInterval = 5000;
```
