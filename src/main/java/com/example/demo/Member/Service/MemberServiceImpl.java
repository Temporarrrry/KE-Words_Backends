package com.example.demo.Member.Service;

import com.example.demo.Member.Entity.Member;
import com.example.demo.Member.Entity.SessionMember;
import com.example.demo.Member.Repository.MemberRepository;
import com.example.demo.Member.dto.MemberRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean register(MemberRequestDTO memberRequestDTO) {
        if (duplicateUserEmail(memberRequestDTO.getUserEmail())) return false;

        memberRequestDTO.setPassword(passwordEncoder.encode(memberRequestDTO.getPassword()));
        memberRepository.save(memberRequestDTO.toEntity());
        return true;
    }

    @Override
    public SessionMember loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUserEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 userName이 없습니다."));
        return new SessionMember(member);
    }

    @Override
    public boolean resign(MemberRequestDTO memberRequestDTO) {
        memberRepository.delete(memberRequestDTO.toEntity());
        return true;
    }

    @Override
    public boolean duplicateUserEmail(String userEmail) {
        return memberRepository.existsByUserEmail(userEmail);
    }
}
