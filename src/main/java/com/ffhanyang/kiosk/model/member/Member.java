package com.ffhanyang.kiosk.model.member;

import com.ffhanyang.kiosk.model.Phone;
import com.ffhanyang.kiosk.security.Jwt;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    /**
     * TODO: Member ID 나중에 UUID로 바꿔야 함(현재는 Long)
     */
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_name", nullable = true, length = 20)
    private String name;

    @Column(name = "member_email", nullable = false, length = 40, unique = true)
    @Embedded
    private Email email;

    @Column(name = "member_age", nullable = false)
    private int age;

    @Column(name = "member_password", nullable = false)
    private String password;

    @Column(name = "member_gender", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private GENDER gender;

    @Column(name = "member_phone_number", nullable = false, length = 20)
    @Embedded
    private Phone phoneNumber;

    @Column(name = "member_role", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private ROLE role;

    @Column(name = "member_login_count", nullable = false)
    private int loginCount;

    @Column(name = "member_created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @Column(name = "member_last_login_at", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime lastLoginAt;

    /**
     * TODO: 추천 모델 구현 후 추가
     *
     * private Recommendation recommendation;
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(name = "member_deleted", nullable = false, columnDefinition = "boolean default false")
    private Boolean deleted;

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public Optional<LocalDateTime> getLastLoginAt() {
        return Optional.ofNullable(lastLoginAt);
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Builder
    public Member(Long id, String name, Email email, int age, String password,
                  GENDER gender, Phone phoneNumber, ROLE role, int loginCount,
                  LocalDateTime createdAt, LocalDateTime lastLoginAt,
                  Location location, Boolean deleted) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.password = password;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.loginCount = loginCount;
        this.createdAt = createdAt;
        this.lastLoginAt = lastLoginAt;
        this.location = location;
        this.deleted = deleted;
    }

    public String newApiToken(Jwt jwt, String[] roles) {
        Jwt.Claims claims = Jwt.Claims.of(id, email, roles);
        return jwt.newToken(claims);
    }

    public void login(PasswordEncoder passwordEncoder, String credentials) {
        if (!passwordEncoder.matches(credentials, password)) {
            throw new IllegalArgumentException("Invalid credentials.");
        }
        loginCount++;
        lastLoginAt = LocalDateTime.now();
    }
}
