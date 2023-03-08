package com.ffhanyang.kiosk.security;

import com.ffhanyang.kiosk.model.member.Member;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import static com.google.common.base.Preconditions.checkArgument;

public class AuthenticationResult {

    private final String apiToken;

    private final Member member;

    public AuthenticationResult(String apiToken, Member member) {
        checkArgument(apiToken != null, "apiToken must be provided.");
        checkArgument(member != null, "member must be provided.");

        this.apiToken = apiToken;
        this.member = member;
    }

    public String getApiToken() {
        return apiToken;
    }

    public Member getMember() {
        return member;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("apiToken", apiToken)
            .append("member", member)
            .toString();
    }
}
