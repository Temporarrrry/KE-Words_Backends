package com.example.demo.Member.Service;

import com.example.demo.Member.dto.MemberRequestDTO;
import com.example.demo.Member.dto.MemberResponseDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public interface MemberService extends UserDetailsService {

    //CREATE
    boolean register(MemberRequestDTO memberRequestDTO);
    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    //MemberResponseDTO login(MemberRequestDTO memberRequestDTO);

    void logout(String accessToken);

    boolean resign(String accessToken);

    boolean userEmailDupCheck(String userEmail);

    Optional<Long> findIdByUserEmail(String userEmail);

    MemberResponseDTO findMember(MemberRequestDTO memberRequestDTO);

    void changePasswordByUserEmail(String userEmail, String newPassword);
}
