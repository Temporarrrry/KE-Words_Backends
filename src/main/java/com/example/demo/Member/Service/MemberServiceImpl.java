package com.example.demo.Member.Service;

import com.example.demo.Jwt.DTO.LogoutAccessTokenRequestDTO;
import com.example.demo.Jwt.Service.LogoutAccessTokenService;
import com.example.demo.Jwt.Service.RefreshTokenService;
import com.example.demo.Jwt.auth.JwtTokenProvider;
import com.example.demo.Member.DTO.MemberChangePasswordRequestDTO;
import com.example.demo.Member.DTO.MemberInfoResponseDTO;
import com.example.demo.Member.DTO.MemberRequestDTO;
import com.example.demo.Member.Entity.Member;
import com.example.demo.Member.Entity.PrincipalDetails;
import com.example.demo.Member.Exception.MemberExistException;
import com.example.demo.Member.Exception.MemberNotExistException;
import com.example.demo.Member.Exception.PasswordNotMatchException;
import com.example.demo.Member.Repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final LogoutAccessTokenService logoutAccessTokenService;

    public MemberServiceImpl(MemberRepository memberRepository, JwtTokenProvider jwtTokenProvider, RefreshTokenService refreshTokenService, LogoutAccessTokenService logoutAccessTokenService) {
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.refreshTokenService = refreshTokenService;
        this.logoutAccessTokenService = logoutAccessTokenService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        this.passwordEncoder = new BCryptPasswordEncoder();
        return this.passwordEncoder;
    }


    //CREATE

    @Override
    public void register(MemberRequestDTO memberRequestDTO) throws MemberExistException {
        if (userEmailDupCheck(memberRequestDTO.getUserEmail())) throw new MemberExistException();

        memberRequestDTO.setPassword(passwordEncoder.encode(memberRequestDTO.getPassword()));
        memberRepository.save(memberRequestDTO.toEntity());
    }

    @Override
    public boolean userEmailDupCheck(String userEmail) {
        return memberRepository.existsByUserEmail(userEmail);
    }

    // login은 authenticationFilter를 통해 진행하므로 호출되지 않음


    // JwtAuthenticationFilter를 통한 login
    @Override
    public PrincipalDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUserEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 userName이 없습니다."));
        return new PrincipalDetails(member);
    }

    @Override
    public void logout(String accessToken) {
        Long remainingTime = jwtTokenProvider.getRemainingTimeByAccessToken(accessToken);
        logoutAccessTokenService.saveLogoutAccessToken(new LogoutAccessTokenRequestDTO(accessToken, remainingTime));
        refreshTokenService.deleteByUserEmail(jwtTokenProvider.getUserEmailByAccessToken(accessToken));
    }

    public Optional<Member> findByUserEmail(String userEmail) {
        return memberRepository.findByUserEmail(userEmail);
    }

    //DELETE
    @Override
    public void resign(String accessToken, String password) throws MemberNotExistException, PasswordNotMatchException {
        Member member = memberRepository
                .findById(findIdByAuthentication())
                .orElseThrow(MemberNotExistException::new);

        if (!memberRepository.existsById(member.getId()))
            throw new MemberNotExistException();

        if (!passwordEncoder.matches(password, member.getPassword()))
            throw new PasswordNotMatchException();

        logout(accessToken);
        memberRepository.delete(member);
    }

    public MemberInfoResponseDTO findMember(MemberRequestDTO memberRequestDTO) throws MemberNotExistException {
        Member member = memberRepository.findByUserEmail(memberRequestDTO.getUserEmail())
                .orElseThrow(MemberNotExistException::new);
        return new MemberInfoResponseDTO(member);
    }

    @Override
    public Optional<String> findUserEmailById(Long userId) {
        return memberRepository.findById(userId).map(Member::getUserEmail);
    }

    //READ
    @Override
    public Optional<Long> findIdByUserEmail(String userEmail) {
        return memberRepository.findByUserEmail(userEmail).map(Member::getId);
    }

    private Boolean passwordCheck(String password, String encodedPassword) {
        return passwordEncoder.matches(password, encodedPassword);
    }


    @Override
    public void changePasswordByUserEmail(String userEmail, MemberChangePasswordRequestDTO memberChangePasswordRequestDTO) throws MemberNotExistException {

        Member member = memberRepository.findByUserEmail(userEmail).orElseThrow(MemberNotExistException::new);

        if (!passwordCheck(memberChangePasswordRequestDTO.getPassword(), member.getPassword()))
            throw new PasswordNotMatchException();

        member.setPassword(passwordEncoder.encode(memberChangePasswordRequestDTO.getNewPassword()));
        memberRepository.save(member);
    }

    @Override
    public Member findByAuthentication() {
        return (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


    @Override
    public Long findIdByAuthentication() throws MemberNotExistException {
        Member member = findByAuthentication();
        return findIdByUserEmail(member.getUserEmail()).orElseThrow(MemberNotExistException::new);
    }
}
