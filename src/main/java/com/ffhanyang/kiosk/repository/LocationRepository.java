package com.ffhanyang.kiosk.repository;

import com.ffhanyang.kiosk.model.member.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
