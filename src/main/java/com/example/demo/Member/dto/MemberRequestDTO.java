package com.example.demo.Member.dto;

import com.example.demo.Member.Entity.Member;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MemberRequestDTO {
    @NotBlank
    private String userEmail;

    @NotBlank
    private String password;

    public Member toEntity(){
        return Member.builder()
                .userEmail(userEmail)
                .password(password)
                .build();
    }
}
