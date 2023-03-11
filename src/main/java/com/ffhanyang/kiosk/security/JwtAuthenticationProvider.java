package com.ffhanyang.kiosk.security;

import com.ffhanyang.kiosk.error.NotFoundException;
import com.ffhanyang.kiosk.model.member.Email;
import com.ffhanyang.kiosk.model.member.Member;
import com.ffhanyang.kiosk.model.member.ROLE;
import com.ffhanyang.kiosk.service.member.MemberService;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.apache.commons.lang3.ClassUtils.isAssignable;
import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final Jwt jwt;

    private final MemberService memberService;

    public JwtAuthenticationProvider(Jwt jwt, MemberService memberService) {
        this.jwt = jwt;
        this.memberService = memberService;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return isAssignable(JwtAuthenticationToken.class, authentication);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) authentication;
        return processUserAuthentication(authenticationToken.authenticationRequest());
    }

    private Authentication processUserAuthentication(AuthenticationRequest request) {
        try {
            Member member = memberService.login(new Email(request.getPrincipal()), request.getCredentials());
            JwtAuthenticationToken authenticated =
                new JwtAuthenticationToken(
                    new JwtAuthentication(member.getId(), member.getEmail()),
                    null,
                    createAuthorityList(member.getRole().value())
                );
            String apiToken = member.newApiToken(jwt, new String[]{ROLE.USER.value()});
            authenticated.setDetails(new AuthenticationResult(apiToken, member));
            return authenticated;
        } catch (NotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException(e.getMessage(), e);
        } catch (DataAccessException e) {
            throw new AuthenticationServiceException(e.getMessage(), e);
        }
    }
}
