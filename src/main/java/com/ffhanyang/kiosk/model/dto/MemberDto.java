package com.ffhanyang.kiosk.model.dto;

import com.ffhanyang.kiosk.model.member.Email;
import com.ffhanyang.kiosk.model.member.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;

import static org.springframework.beans.BeanUtils.copyProperties;

public class MemberDto {

    @Schema(description = "PK", required = true)
    private Long id;

    @Schema(description = "사용자명")
    private String name;

    @Schema(description = "이메일", required = true)
    private Email email;

    @Schema(description = "로그인 횟수", required = true)
    private int loginCount;

    @Schema(description = "최종로그인일시")
    private LocalDateTime lastLoginAt;

    @Schema(description = "생성일시", required = true)
    private LocalDateTime createdAt;

    public MemberDto(Member source) {
        copyProperties(source, this);

        this.name = source.getName().orElse(null);
        this.lastLoginAt = source.getLastLoginAt().orElse(null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public int getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(int loginCount) {
        this.loginCount = loginCount;
    }

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(LocalDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    public LocalDateTime getCreateAt() {
        return createdAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createdAt = createAt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("id", id)
            .append("name", name)
            .append("email", email)
            .append("loginCount", loginCount)
            .append("lastLoginAt", lastLoginAt)
            .append("createdAt", createdAt)
            .toString();
    }

}