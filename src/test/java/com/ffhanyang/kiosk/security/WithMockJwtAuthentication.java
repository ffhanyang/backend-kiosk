package com.ffhanyang.kiosk.security;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockJwtAuthenticationSecurityContextFactory.class)
public @interface WithMockJwtAuthentication {
    String id() default "5204b969-83b7-42a8-95ba-32efe2f61df9";

    String email() default "tester0@gmail.com";

    String role() default "ROLE_USER";
}
