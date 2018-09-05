package com.duke.framework.utils;

import com.duke.framework.domain.DBProperties;
import org.springframework.util.CollectionUtils;

import java.sql.*;
import java.util.List;

/**
 * Created duke on 2018/9/5
 */
class DbUtils {

    /**
     * 获取数据库连接
     *
     * @param dbProperties 数据库信息配置类
     * @return Connection
     */
    private static Connection getConn(DBProperties dbProperties) {
        Connection conn = null;
        try {
            Class.forName(dbProperties.getDriverClassName()); //classLoader,加载对应驱动
            conn = DriverManager.getConnection(dbProperties.getUrl(), dbProperties.getUserName(), dbProperties.getPassword());
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 查询
     *
     * @param sql          sql
     * @param dbProperties 数据库属性对象
     * @return 结果集
     */
    static ResultSet query(String sql, DBProperties dbProperties) {
        try {
            return getConn(dbProperties).prepareStatement(sql).executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
