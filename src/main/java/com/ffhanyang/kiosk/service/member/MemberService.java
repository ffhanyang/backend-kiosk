package com.ffhanyang.kiosk.service.member;

import com.ffhanyang.kiosk.error.NotFoundException;
import com.ffhanyang.kiosk.model.member.Email;
import com.ffhanyang.kiosk.model.member.Member;
import com.ffhanyang.kiosk.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;

    @Transactional
    public Member login(Email email, String credentials) {
        checkArgument(credentials != null, "credentials must be provided.");

        Member member = memberRepository.findByEmail(email.getAddress())
            .orElseThrow(() -> new NotFoundException(Member.class, email));
        member.login(passwordEncoder, credentials);
        return member;
    }
}
