package com.ffhanyang.kiosk.controller.authentication;

import com.ffhanyang.kiosk.error.UnauthorizedException;
import com.ffhanyang.kiosk.controller.commons.ApiResult;
import com.ffhanyang.kiosk.security.AuthenticationResult;
import com.ffhanyang.kiosk.security.AuthenticationRequest;
import com.ffhanyang.kiosk.security.JwtAuthenticationToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
@Tag(name = "인증 APIs")
public class AuthenticationRestController {

    private final AuthenticationManager authenticationManager;

    @PostMapping
    @Operation(summary = "사용자 로그인 (API 토큰 필요없음)")
    public ApiResult<AuthenticationResultDto> authentication(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            JwtAuthenticationToken authToken = new JwtAuthenticationToken(authenticationRequest.getPrincipal(), authenticationRequest.getCredentials());
            Authentication authentication = authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ApiResult.OK(new AuthenticationResultDto((AuthenticationResult) authentication.getDetails()));
        } catch (AuthenticationException e) {
            throw new UnauthorizedException(e.getMessage());
        }
    }
}
