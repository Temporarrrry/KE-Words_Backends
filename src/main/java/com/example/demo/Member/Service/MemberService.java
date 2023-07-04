package com.example.demo.Member.Service;

import com.example.demo.Member.dto.MemberRequestDTO;
import com.example.demo.Member.dto.MemberInfoResponseDTO;
import org.springframework.security.core.Authentication;
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

    void resign(String accessToken);

    boolean userEmailDupCheck(String userEmail);

    Optional<Long> findIdByUserEmail(String userEmail);

    MemberInfoResponseDTO findMember(MemberRequestDTO memberRequestDTO);

    void changePasswordByUserEmail(String userEmail, String newPassword);

    Long findIdByAuthentication(Authentication authentication);
}
