package com.duke.framework.domain;

/**
 * Created duke on 2018/9/5
 */
public class DBProperties {

    private String url = "jdbc:mysql://%s/%s?useUnicode=true&characterEncoding=utf8&useSSL=false";
    private String userName;
    private String password;

    public DBProperties(String host, String dbName, String userName, String password) {
        this.url = String.format(this.url, host, dbName);
        this.userName = userName;
        this.password = password;
    }

    public String getDriverClassName() {
        return "com.mysql.jdbc.Driver";
    }

    public String getUrl() {
        return url;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
