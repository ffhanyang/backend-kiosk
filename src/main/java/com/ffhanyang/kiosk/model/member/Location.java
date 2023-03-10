package com.ffhanyang.kiosk.model.member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Location {

    @Id
    @GeneratedValue
    @Column(name = "location_id")
    private Long id;

    @Column(name = "street", nullable = true, length = 20)
    private String street;

    @Column(name = "city", nullable = false, length = 10)
    private String city;

    @Column(name = "province", nullable = false, length = 10)
    private String province;

    @Builder
    public Location(Long id, String street, String city, String province) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.province = province;
    }
}
