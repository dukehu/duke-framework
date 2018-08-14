package com.duke.framework.utils;

import org.springframework.util.ObjectUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created duke on 2018/7/29
 */
public class WebUtils {

    /**
     * 清除所有cookie
     *
     * @param request
     * @param response
     */
    public static void clear(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            Cookie tmpCookie = new Cookie(cookie.getName(), null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(tmpCookie);
        }
    }


    /**
     * 从请求中移除cookie
     *
     * @param response    响应
     * @param request     请求
     * @param cookieNames cookieNames
     */
    public static void remove(HttpServletResponse response, HttpServletRequest request, String... cookieNames) {
        if (!ObjectUtils.isEmpty(cookieNames)) {
            Cookie[] cookies = request.getCookies();
            if (!ObjectUtils.isEmpty(cookies)) {
                for (Cookie tmpCookie : cookies) {
                    tmpCookie.setPath("/");
                    tmpCookie.setMaxAge(0);
                    response.addCookie(tmpCookie);
                }
            }
        }
    }

    /**
     * 请求中提取cookie值
     *
     * @param request    请求
     * @param cookieName cookie名称
     * @return String
     */
    public static String extract(HttpServletRequest request, String cookieName) {

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }

    /**
     * 添加cookie
     *
     * @param response   响应
     * @param cookieName cookie 名称
     * @param value      cookie 值
     */
    public static void addCookie(HttpServletResponse response, String cookieName, String value) {
        Cookie cookie = new Cookie(cookieName, value);
        cookie.setPath("/");
        cookie.setMaxAge(3600);
        cookie.setDomain("192.168.2.204");
        response.addCookie(cookie);
    }

    /**
     * 添加cookie
     *
     * @param response   响应
     * @param cookieName cookie 名称
     * @param value      cookie 值
     * @param maxAge     最大存活时间
     * @param path       存储路径
     * @param domain     domain
     */
    public static void addCookie(HttpServletResponse response, String cookieName, String value, Integer maxAge, String path, String domain) {
        Cookie cookie = new Cookie(cookieName, value);
        cookie.setMaxAge(maxAge);
        cookie.setPath(path);
        cookie.setDomain(domain);
        response.addCookie(cookie);
    }

}
