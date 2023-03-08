package com.ffhanyang.kiosk.security;

import com.ffhanyang.kiosk.model.commons.Id;
import com.ffhanyang.kiosk.model.member.Email;
import com.ffhanyang.kiosk.model.member.Member;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


import static com.google.common.base.Preconditions.checkArgument;

public class JwtAuthentication {

    private final Id<Member, Long> id;

    public final Email email;

    public JwtAuthentication(Long id, Email email) {
        checkArgument(id != null, "id must be provided.");
        checkArgument(email != null, "email must be provided.");

        this.id = Id.of(Member.class, id);
        this.email = email;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("id", id)
            .append("email", email)
            .toString();
    }
}
