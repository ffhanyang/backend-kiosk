package com.ffhanyang.kiosk.security;

import com.ffhanyang.kiosk.model.member.Email;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static java.lang.Thread.sleep;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JwtTest {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private Jwt jwt;

    @Value("${jwt.token.issuer}")
    private String issuer;

    @Value("${jwt.token.client-secret}")
    private String clientSecret;

    @BeforeAll
    void setUp() {
        int expirySeconds = 10;
        jwt = new Jwt(issuer, clientSecret, expirySeconds);
    }

    @Test
    @DisplayName("JWT 토큰을 생성하고 복호화 할 수 있다.")
    void jwtCreateDecodeTest() {
        Jwt.Claims claims = Jwt.Claims.of(1L, new Email("tester00@gmail.com"), new String[]{"ROLE_USER"});
        String encodedJWT = jwt.newToken(claims);
        log.info("encodedJWT: {}", encodedJWT);

        Jwt.Claims decodedJWT = jwt.verify(encodedJWT);
        log.info("decodedJWT: {}", decodedJWT);

        assertEquals(claims.memberKey, decodedJWT.memberKey);
        assertArrayEquals(claims.roles, decodedJWT.roles);
    }

    @Test
    @DisplayName("JWT 토큰을 리프레시 할 수 있다.")
    void jwtRefresh() throws Exception {
        if (jwt.getExpirySeconds() > 0) {
            Jwt.Claims claims = Jwt.Claims.of(1L, new Email("test@gmail.com"), new String[]{"ROLE_USER"});
            String encodedJWT = jwt.newToken(claims);
            log.info("encodedJWT: {}", encodedJWT);

            // 1초 대기 후 토큰 갱신
            sleep(1_000L);

            String encodedRefreshedJWT = jwt.refreshToken(encodedJWT);
            log.info("encodedRefreshedJWT: {}", encodedRefreshedJWT);

            assertThat(encodedJWT, not(encodedRefreshedJWT));

            Jwt.Claims oldJwt = jwt.verify(encodedJWT);
            Jwt.Claims newJwt = jwt.verify(encodedRefreshedJWT);

            long oldExp = oldJwt.exp();
            long newExp = newJwt.exp();

            // 1초 후에 토큰을 갱신했으므로, 새로운 토큰의 만료시각이 1초 이후임
            assertThat(newExp >= oldExp + 1_000L, is(true));

            log.info("oldJwt: {}", oldJwt);
            log.info("newJwt: {}", newJwt);
        }
    }
}