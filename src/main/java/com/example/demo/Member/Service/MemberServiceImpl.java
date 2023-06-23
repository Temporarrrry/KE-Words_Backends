package com.example.demo.Member.Service;

import com.example.demo.Jwt.Service.RefreshTokenService;
import com.example.demo.Member.Entity.Member;
import com.example.demo.Member.Entity.PrincipalDetails;
import com.example.demo.Member.Entity.Role;
import com.example.demo.Member.Exception.MemberNotExistException;
import com.example.demo.Member.Repository.MemberRepository;
import com.example.demo.Member.dto.MemberRequestDTO;
import com.example.demo.Member.dto.MemberResponseDTO;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;

    public MemberServiceImpl(MemberRepository memberRepository, RefreshTokenService refreshTokenService) {
        this.memberRepository = memberRepository;
        this.refreshTokenService = refreshTokenService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        this.passwordEncoder = new BCryptPasswordEncoder();
        return this.passwordEncoder;
    }


    //CREATE

    @Override
    public boolean register(MemberRequestDTO memberRequestDTO) {
        if (userEmailDupCheck(memberRequestDTO.getUserEmail())) return false;

        Member member = memberRequestDTO.toEntity();
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        member.setRole(Role.ROLE_MEMBER);
        memberRepository.save(member);
        return true;
    }

    @Override
    public boolean userEmailDupCheck(String userEmail) {
        return memberRepository.existsByUserEmail(userEmail);
    }

    /*// login을 authenticationFilter를 통해 진행하므로 호출되지 않음
    @Override
    public MemberResponseDTO login(MemberRequestDTO memberRequestDTO) throws loginFailureException {
        Member member = memberRepository.findByUserEmail(memberRequestDTO.getUserEmail())
                .orElseThrow(loginFailureException::new);
        if(!passwordEncoder.matches(memberRequestDTO.getPassword(), member.getPassword()))
            throw new loginFailureException();

        return new MemberResponseDTO(member.getUserEmail());
    }*/


    // JwtAuthenticationFilter를 통한 login
    @Override
    public PrincipalDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUserEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 userName이 없습니다."));
        return new PrincipalDetails(member);
    }


    //DELETE
    @Override
    public boolean resign(MemberRequestDTO memberRequestDTO) {
        //TODO accessToken 비활성화
        refreshTokenService.deleteByEmail(memberRequestDTO.getUserEmail());
        memberRepository.deleteByUserEmail(memberRequestDTO.getUserEmail());
        return true;
    }

    public MemberResponseDTO findMember(MemberRequestDTO memberRequestDTO) throws MemberNotExistException {
        Member member = memberRepository.findByUserEmail(memberRequestDTO.getUserEmail())
                .orElseThrow(MemberNotExistException::new);
        return new MemberResponseDTO(member);
    }



    //READ
    @Override
    public Optional<Long> findIdByUserEmail(String userEmail) {
        return memberRepository.findByUserEmail(userEmail).map(Member::getId);
    }

}
