package com.example.demo.Member.Service;

import com.example.demo.Member.dto.MemberRequestDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface MemberService extends UserDetailsService {

    boolean register(MemberRequestDTO memberRequestDTO);


    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    boolean resign(MemberRequestDTO memberRequestDTO);

    boolean userEmailDupCheck(String userEmail);
}
