package com.ffhanyang.kiosk.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Phone {

    @Column
    private String member_phone_number;

    public Phone(String member_phone_number) {
        if (member_phone_number != null) {
            checkArgument(member_phone_number.length() >= 11 && member_phone_number.length() <= 15, "member_phone_number length must be between 10 and 11 characters.");
            checkArgument(member_phone_number.matches("[0-9]+"), "member_phone_number must be numeric.");
        }
        this.member_phone_number = member_phone_number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phone phone = (Phone) o;
        return Objects.equals(member_phone_number, phone.member_phone_number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(member_phone_number);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("member_phone_number", member_phone_number)
            .toString();
    }
}
