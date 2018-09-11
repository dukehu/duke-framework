package com.duke.framework.utils;

import com.duke.framework.domain.DBProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

/**
 * Created duke on 2018/9/5
 */
@SuppressWarnings("ALL")
public class DbUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(DbUtils.class);

    private static DBProperties dbProperties;

    /**
     * 初始化
     *
     * @param pro 数据库属性对象
     */
    public static void init(DBProperties pro) {
        dbProperties = pro;
    }

    /**
     * 获取数据库连接
     *
     * @return Connection
     */
    private static Connection getConn() {
        Connection conn;
        try {
            // 加载驱动
            Class.forName(dbProperties.getDriverClassName());
            Properties properties = new Properties();
            properties.setProperty("user", dbProperties.getUserName());
            properties.setProperty("password", dbProperties.getPassword());
            // 设置可以获取remarks信息
            properties.setProperty("remarks", "true");
            // 设置可以获取tables
            properties.setProperty("useInformationSchema", "true");

            conn = DriverManager.getConnection(dbProperties.getUrl(), properties);
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.error("获取数据库连接", e);
            return null;
        }
        return conn;
    }

    /**
     * 查询
     *
     * @param sql sql
     * @return 结果集
     */
    static ResultSet query(String sql) {
        try {
            return Objects.requireNonNull(getConn()).prepareStatement(sql).executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
