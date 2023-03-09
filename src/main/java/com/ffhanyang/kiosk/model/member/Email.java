package com.ffhanyang.kiosk.model.member;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static io.micrometer.common.util.StringUtils.isNotEmpty;
import static java.util.regex.Pattern.matches;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Email {

    @Column
    private String member_email;

    public Email(String member_email) {
        checkArgument(isNotEmpty(member_email), "email must be provided.");
        checkArgument(
            member_email.length() >= 4 && member_email.length() <= 40,
            "email length must be between 4 and 50 characters."
        );
        checkArgument(checkAddress(member_email), "Invalid email address: " + member_email);

        this.member_email = member_email;
    }

    private static boolean checkAddress(String member_email) {
        return matches("[\\w~\\-.+]+@[\\w~\\-]+(\\.[\\w~\\-]+)+", member_email);
    }

    public String getName() {
        String[] tokens = member_email.split("@");
        if (tokens.length == 2)
            return tokens[0];
        return null;
    }

    public String getDomain() {
        String[] tokens = member_email.split("@");
        if (tokens.length == 2)
            return tokens[1];
        return null;
    }

    public String getAddress() {
        return member_email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(member_email, email.member_email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(member_email);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("member_email", member_email)
            .toString();
    }

}