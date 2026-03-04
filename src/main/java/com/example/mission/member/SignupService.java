package com.example.mission.member;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SignupService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public SignupService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void signup(SignupForm form) {
        String username = (form.username() == null) ? "" : form.username().trim();
        String password = (form.password() == null) ? "" : form.password();
        String confirm = (form.passwordConfirm() == null) ? "" : form.passwordConfirm();

        if (username.isBlank()) {
            throw new IllegalArgumentException("아이디를 입력하세요.");
        }
        if (password.isBlank()) {
            throw new IllegalArgumentException("비밀번호를 입력하세요.");
        }
        if (!password.equals(confirm)) {
            throw new IllegalArgumentException("비밀번호 확인이 일치하지 않습니다.");
        }
        if (memberRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        String encoded = passwordEncoder.encode(password);

        memberRepository.insertUser(username, encoded);
        memberRepository.insertAuthority(username, "ROLE_USER");
    }
}