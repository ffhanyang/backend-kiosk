package com.ffhanyang.kiosk.model.member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Location {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "location_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "street", nullable = true, length = 20)
    private String street;

    @Column(name = "city", nullable = false, length = 10)
    private String city;

    @Column(name = "province", nullable = false, length = 10)
    private String province;

    @Builder
    public Location(UUID id, String street, String city, String province) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.province = province;
    }
}
