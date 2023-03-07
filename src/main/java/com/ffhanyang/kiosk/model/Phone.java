package com.ffhanyang.kiosk.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Phone {

    @Column
    private String number;

    public Phone(String number) {
        if (number != null) {
            checkArgument(number.length() >= 11 && number.length() <= 15, "number length must be between 10 and 11 characters.");
            checkArgument(number.matches("[0-9]+"), "number must be numeric.");
        }
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phone phone = (Phone) o;
        return Objects.equals(number, phone.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("number", number)
            .toString();
    }
}
