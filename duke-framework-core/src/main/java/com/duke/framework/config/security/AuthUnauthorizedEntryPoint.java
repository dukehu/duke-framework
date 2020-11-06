package com.duke.framework.config.security;

import com.duke.framework.CoreConstants;
import com.duke.framework.utils.WebUtils;
import com.duke.framework.web.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created duke on 2018/8/10
 * <p>
 * 当访问的资源没有权限，会调用这里
 */
public class AuthUnauthorizedEntryPoint implements AuthenticationEntryPoint {

    private final static Logger LOGGER = LoggerFactory.getLogger(AuthUnauthorizedEntryPoint.class);

    private ObjectMapper objectMapper;

    public AuthUnauthorizedEntryPoint() {
    }

    public AuthUnauthorizedEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        String uri = request.getRequestURI();
        WebUtils.remove(response, request, CoreConstants.ACCESS_TOKEN, CoreConstants.REFRESH_TOKEN, CoreConstants.AVATAR, CoreConstants.LOGIN_NAME, CoreConstants.USER_ID);
        // 返回json形式的错误信息
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        if (authException instanceof UsernameNotFoundException) {
            LOGGER.error("Exception: status[{}], code[{}], uri[{}], message[{}], error[{}]",
                    401, "username_not_found", !ObjectUtils.isEmpty(uri) ? uri : "", "v", authException);

            response.getWriter().write(this.objectMapper.writeValueAsString(Response.error(HttpStatus.UNAUTHORIZED.value(), "username_not_found", authException.getMessage())));
        } else {
            LOGGER.error("Exception: status[{}], code[{}], uri[{}], message[{}], error[{}]",
                    401, "invalid_token", !ObjectUtils.isEmpty(uri) ? uri : "", "v", authException);
            response.getWriter().write(this.objectMapper.writeValueAsString(Response.error(HttpStatus.UNAUTHORIZED.value(), "invalid_token", "无效的令牌或令牌已经过期")));
        }
    }
}
