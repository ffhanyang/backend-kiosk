package com.ffhanyang.kiosk.service.member;

import com.ffhanyang.kiosk.model.Phone;
import com.ffhanyang.kiosk.model.member.*;
import com.ffhanyang.kiosk.repository.LocationRepository;
import com.ffhanyang.kiosk.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private LocationRepository locationRepository;

    private Member member;

    private Location location;

    @BeforeEach
    void setUp() {
        location = Location.builder()
            .id(1L)
            .street("street0")
            .city("city0")
            .province("province0")
            .build();

        member = Member.builder()
            .id(1L)
            .name("tester")
            .email(new Email("tester@gamil.com"))
            .age(25)
            .password("$2a$12$8VqIQLRW6s4tsUTwztQLz.Yuayqfn43E5ymUsoWMF.b.Vda5UWyGC")
            .gender(GENDER.MALE)
            .phoneNumber(new Phone("01012345678"))
            .role(ROLE.USER)
            .loginCount(0)
            .createdAt(LocalDateTime.now())
            .location(location)
            .deleted(false)
            .build();
    }

    @Test
    @DisplayName("로그인")
    void login() {
        locationRepository.save(location);
        memberRepository.save(member);
        Member loginMember = memberService.login(member.getEmail(), "tester");
        Assertions.assertThat(loginMember.getId()).isEqualTo(member.getId());
    }


}