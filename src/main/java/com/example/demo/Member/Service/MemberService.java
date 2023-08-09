package com.example.demo.Member.Service;

import com.example.demo.Member.DTO.MemberInfoResponseDTO;
import com.example.demo.Member.DTO.MemberRequestDTO;
import com.example.demo.Member.Entity.Member;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public interface MemberService extends UserDetailsService {

    //CREATE
    void register(MemberRequestDTO memberRequestDTO);
    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    //MemberResponseDTO login(MemberRequestDTO memberRequestDTO);

    void logout(String accessToken);

    void resign(String accessToken, String password);

    boolean userEmailDupCheck(String userEmail);

    Optional<String> findUserEmailById(Long userId);

    Optional<Long> findIdByUserEmail(String userEmail);

    MemberInfoResponseDTO findMember(MemberRequestDTO memberRequestDTO);

    Optional<Member> findByUserEmail(String userEmail);

    void changePasswordByUserEmail(String userEmail, String newPassword);

    Member findByAuthentication();

    Long findIdByAuthentication();
}
