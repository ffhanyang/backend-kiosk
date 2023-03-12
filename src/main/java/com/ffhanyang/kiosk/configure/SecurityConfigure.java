package com.ffhanyang.kiosk.configure;

import com.ffhanyang.kiosk.model.member.ROLE;
import com.ffhanyang.kiosk.security.*;
import com.ffhanyang.kiosk.service.member.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigure {

    private final Jwt jwt;

    private final JwtTokenConfigure jwtTokenConfigure;

    private final JwtAccessDeniedHandler accessDeniedHandler;

    private final EntryPointUnauthorizedHandler unauthorizedHandler;

    private static final String[] WHITE_LIST = {
        "/swagger-resources",
        "/api/auth",
        "/api/member/join",
        "/api/member/exists"
    };

    public SecurityConfigure(Jwt jwt, JwtTokenConfigure jwtTokenConfigure, JwtAccessDeniedHandler accessDeniedHandler, EntryPointUnauthorizedHandler unauthorizedHandler) {
        this.jwt = jwt;
        this.jwtTokenConfigure = jwtTokenConfigure;
        this.accessDeniedHandler = accessDeniedHandler;
        this.unauthorizedHandler = unauthorizedHandler;
    }

    @Bean
    public JwtAuthenticationProvider jwtAuthenticationProvider(Jwt jwt, MemberService memberService) {
        return new JwtAuthenticationProvider(jwt, memberService);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter(jwtTokenConfigure.getHeader(), jwt);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().disable();
        http.exceptionHandling()
            .accessDeniedHandler(accessDeniedHandler)
            .authenticationEntryPoint(unauthorizedHandler);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeHttpRequests(authorize -> authorize
            .requestMatchers(WHITE_LIST).permitAll()
            .requestMatchers("/api/**").hasRole(ROLE.USER.name())
            .anyRequest().permitAll()
        );
        http.formLogin().disable();
        http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
