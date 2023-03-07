package com.ffhanyang.kiosk.model.member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
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
}
