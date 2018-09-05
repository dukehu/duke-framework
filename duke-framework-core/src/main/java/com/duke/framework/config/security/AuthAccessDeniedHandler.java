package com.duke.framework.config.security;

import com.duke.framework.web.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.util.ObjectUtils;

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
                       AccessDeniedException accessDeniedException) throws IOException {
        String uri = request.getRequestURI();
        LOGGER.error("Exception: status[{}], code[{}], uri[{}], message[{}], error[{}]",
                403, "access_denied", !ObjectUtils.isEmpty(uri) ? uri : "", "禁止访问", accessDeniedException);
        if (this.objectMapper == null) {
            throw accessDeniedException;
        } else {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(this.objectMapper.writeValueAsString(Response.error(403, "access_denied", "禁止访问")));
        }
    }
}
