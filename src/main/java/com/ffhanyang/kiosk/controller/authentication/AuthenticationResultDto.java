package com.ffhanyang.kiosk.controller.authentication;

import com.ffhanyang.kiosk.controller.member.MemberDto;
import com.ffhanyang.kiosk.security.AuthenticationResult;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import static org.springframework.beans.BeanUtils.copyProperties;

@Getter
public class AuthenticationResultDto {

    @Schema(description = "API 토큰", required = true)
    private String apiToken;

    @Schema(description = "사용자 정보", required = true)
    private MemberDto member;

    public AuthenticationResultDto(AuthenticationResult source) {
        copyProperties(source, this);

        this.member = new MemberDto(source.getMember());
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public void setMember(MemberDto member) {
        this.member = member;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("apiToken", apiToken)
                .append("member", member)
                .toString();
    }
}
