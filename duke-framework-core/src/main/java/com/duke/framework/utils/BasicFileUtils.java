package com.duke.framework.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * Created duke on 2018/10/25
 */
public class BasicFileUtils {

    /**
     * 判断地址是否为绝对地址
     *
     * @param fileName 文件地址
     * @return boolean
     */
    public static boolean isAbsFile(String fileName) {
        if (OSUtils.isWinOS()) {
            // windows 操作系统时，绝对地址形如  c:\descktop
            return fileName.contains(":") || fileName.startsWith("\\");
        } else {
            // mac or linux
            return fileName.startsWith("/");
        }
    }

    /**
     * 将用户目录下地址~/xxx 转换为绝对地址
     *
     * @param path 地址
     * @return String
     */
    public static String parseHomeDir2AbsDir(String path) {
        String homeDir = System.getProperties().getProperty("user.home");
        return StringUtils.replace(path, "~", homeDir);
    }
}
