package com.duke.framework.utils;

/**
 * Created duke on 2018/9/6
 */
@SuppressWarnings("ALL")
public class StringUtil {


    /**
     * 下滑线转驼峰
     *
     * @param underLineName 下划线名称
     * @return String
     */
    public static String underLine2CamelCase(String underLineName) {
        StringBuilder camelCaseName = new StringBuilder();
        if (underLineName != null && underLineName.length() > 0) {
            boolean flag = false;
            for (int i = 0; i < underLineName.length(); i++) {
                char c = underLineName.charAt(i);
                if ("_".charAt(0) == c) {
                    flag = true;
                } else {
                    if (flag) {
                        camelCaseName.append(Character.toUpperCase(c));
                        flag = false;
                    } else {
                        camelCaseName.append(c);
                    }
                }
            }
        }
        return camelCaseName.toString();
    }

    /**
     * 驼峰转下滑线
     *
     * @param camelCaseName 驼峰名称
     * @return String
     */
    public static String camelCase2UnderLine(String camelCaseName) {
        StringBuilder underLineName = new StringBuilder();
        if (camelCaseName != null && camelCaseName.length() > 0) {
            underLineName.append(camelCaseName.substring(0, 1).toLowerCase());
            for (int i = 1; i < camelCaseName.length(); i++) {
                char ch = camelCaseName.charAt(i);
                if (Character.isUpperCase(ch)) {
                    underLineName.append("_");
                    underLineName.append(Character.toLowerCase(ch));
                } else {
                    underLineName.append(ch);
                }
            }
        }
        return underLineName.toString();
    }
}
