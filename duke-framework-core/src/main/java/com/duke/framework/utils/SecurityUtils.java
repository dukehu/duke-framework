package com.duke.framework.utils;

import com.duke.framework.security.AuthUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.ObjectUtils;

import java.util.LinkedHashMap;
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
        LinkedHashMap<String, Object> principalMap = (LinkedHashMap<String, Object>) usernamePasswordAuthenticationToken.getPrincipal();
        Map<String, Object> additionalInformation = (Map<String, Object>) principalMap.get("additionalInformation");

        Object userId = additionalInformation.get("userId");
        Object gender = additionalInformation.get("gender");
        Object realName = additionalInformation.get("realName");
        Object nickName = additionalInformation.get("nickName");
        Object loginName = additionalInformation.get("loginName");
        Object userStatus = additionalInformation.get("userStatus");
        Object avatar = additionalInformation.get("avatar");
        if (!ObjectUtils.isEmpty(userId)) {
            userDetails.setUserId(userId.toString());
        }
        if (!ObjectUtils.isEmpty(gender)) {
            userDetails.setGender(Integer.parseInt(gender.toString()));
        }
        if (!ObjectUtils.isEmpty(realName)) {
            userDetails.setRealName(realName.toString());
        }
        if (!ObjectUtils.isEmpty(nickName)) {
            userDetails.setNickName(nickName.toString());
        }
        if (!ObjectUtils.isEmpty(loginName)) {
            userDetails.setLoginName(loginName.toString());
        }
        if (!ObjectUtils.isEmpty(userStatus)) {
            userDetails.setUserStatus(Integer.parseInt(userStatus.toString()));
        }
        if (!ObjectUtils.isEmpty(avatar)) {
            userDetails.setAvatar(avatar.toString());
        }
        if (authentication != null) {
        }
        return userDetails;
    }
}
