package com.ffhanyang.kiosk.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ffhanyang.kiosk.model.dto.ApiResult;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.ffhanyang.kiosk.model.dto.ApiResult.ERROR;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    static ApiResult<?> E403 = ERROR("Authentication error (cause: forbidden)", HttpStatus.FORBIDDEN);

    private final ObjectMapper om;

    public JwtAccessDeniedHandler(ObjectMapper om) {
        this.om = om;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setHeader("content-type", "application/json");
        response.getWriter().write(om.writeValueAsString(E403));
        response.getWriter().flush();
        response.getWriter().close();
    }
}
