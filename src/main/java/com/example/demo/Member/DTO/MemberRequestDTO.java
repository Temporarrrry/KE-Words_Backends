package com.example.demo.Member.DTO;

import com.example.demo.Member.Entity.Member;
import com.example.demo.Member.Entity.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor // authenticationFilter에서 mapper.readValue를 위해서
@AllArgsConstructor
public class MemberRequestDTO {
    @NotBlank
    private String userEmail;

    private String password;

    public MemberRequestDTO(String userEmail) {
        this.userEmail = userEmail;
    }


    public Member toEntity(){
        return Member.builder()
                .userEmail(userEmail)
                .password(password)
                .role(Role.ROLE_MEMBER)
                .build();
    }
}
