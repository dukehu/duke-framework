package com.duke.framework.utils;

import com.duke.framework.security.AuthUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import sun.security.util.SecurityConstants;

import java.util.Map;


/**
 * Created by ZHANGWEI5095@QQ.COM on 19:08 2017/5/14.
 */
public final class SecurityUtils {

    private SecurityUtils() {
    }

    public static String getUserName() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String userName = null;
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof org.springframework.security.core.userdetails.UserDetails) {
                org.springframework.security.core.userdetails.UserDetails springSecurityUser = (org.springframework.security.core.userdetails.UserDetails) authentication.getPrincipal();
                userName = springSecurityUser.getUsername();
            } else if (authentication.getPrincipal() instanceof String) {
                userName = (String) authentication.getPrincipal();
            }
        }
        return userName;
    }

    public static AuthUserDetails getCurrentUserInfo() {
        AuthUserDetails userDetails = new AuthUserDetails();
        SecurityContext securityContext = SecurityContextHolder.getContext();
        OAuth2Authentication authentication = (OAuth2Authentication) securityContext.getAuthentication();
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) authentication.getUserAuthentication();
        Map<String, Object> map = (Map<String, Object>) usernamePasswordAuthenticationToken.getPrincipal();
        Map<String, Object> userDetailsMap = (Map<String, Object>) map.get("principal");
        userDetails.setUserId(userDetailsMap.get("userId").toString());
        userDetails.setGender(Integer.parseInt(userDetailsMap.get("gender").toString()));
        userDetails.setRealName(userDetailsMap.get("realName").toString());
        userDetails.setNickName(userDetailsMap.get("nickName").toString());
        userDetails.setLoginName(userDetailsMap.get("loginName").toString());
        userDetails.setUserStatus(Integer.parseInt(userDetailsMap.get("userStatus").toString()));
        userDetails.setAvatar(userDetailsMap.get("avatar").toString());
        if (authentication != null) {
        }
        return userDetails;
    }
}
