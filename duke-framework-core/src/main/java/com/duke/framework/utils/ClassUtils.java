package com.duke.framework.utils;

import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * Created duke on 2018/9/5
 */
class ClassUtils {

    /**
     * 通过包名获取包内所有类
     *
     * @param pkg 包
     * @return Set
     */
    static Set<Class<?>> getClasses(Package pkg) {
        String packageName = pkg.getName();

        Set<Class<?>> classes = new HashSet<>();
        // 获取包的名字 并进行替换
        String packageDirName = pkg.getName().replace('.', '/');
        // 定义一个枚举的集合 并进行循环来处理这个目录下的things
        Enumeration<URL> dirs;

        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);

            while (dirs.hasMoreElements()) {

                // 获取下一个子元素
                URL url = dirs.nextElement();

                // 获取包的物理路径
                String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                findAndAddClassesInPackage(packageName, filePath, classes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return classes;
    }

    /**
     * 找某一个包下面的class文件并添加到集合中
     *
     * @param packageName 包名
     * @param packagePath 文件路径
     * @param classes     类集合
     */
    private static void findAndAddClassesInPackage(String packageName, String packagePath, Set<Class<?>> classes) {

        // 获取此包的目录，建立一个File
        File dir = new File(packagePath);
        // 如果不存在或者不是目录直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        // 如果存在叫获取包下面的所有文件 包括目录
        File[] dirFiles = dir.listFiles(file -> (file.isDirectory()) || (file.getName().endsWith(".class")));
        if (!ObjectUtils.isEmpty(dirFiles) && dirFiles.length > 0) {
            // 循环所有文件
            for (File file : dirFiles) {
                // 如果是目录，继续扫面
                if (file.isDirectory()) {
                    findAndAddClassesInPackage(packageName + "." + file.getName(), file.getAbsolutePath(), classes);
                } else {
                    // 是文件
                    String className = file.getName().substring(0, file.getName().length() - 6);
                    try {
                        // 添加到集合中去
                        classes.add(Class.forName(packageName + '.' + className));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
