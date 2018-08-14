package com.duke.framework.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created duke on 2018/8/10
 */
public class AuthAccessDeniedHandler implements AccessDeniedHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(AuthAccessDeniedHandler.class);

    private ObjectMapper objectMapper;

    public AuthAccessDeniedHandler() {
    }

    public AuthAccessDeniedHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        LOGGER.error("AuthAccessDeniedHandler！");
        // 返回json形式的错误信息
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(this.objectMapper.writeValueAsString("AuthAccessDeniedHandler"));
    }
}
