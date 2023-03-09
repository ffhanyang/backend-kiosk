package com.ffhanyang.kiosk.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ffhanyang.kiosk.controller.commons.ApiResult;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.ffhanyang.kiosk.controller.commons.ApiResult.ERROR;

@Component
public class EntryPointUnauthorizedHandler implements AuthenticationEntryPoint {

    static ApiResult<?> E401 = ERROR("Authentication error (cause: unauthorized)", HttpStatus.UNAUTHORIZED);

    private final ObjectMapper om;

    public EntryPointUnauthorizedHandler(ObjectMapper om) {
        this.om = om;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
        throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader("content-type", "application/json");
        response.getWriter().write(om.writeValueAsString(E401));
        response.getWriter().flush();
        response.getWriter().close();
    }
}
