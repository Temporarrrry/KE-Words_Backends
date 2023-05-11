package com.example.demo.Member.dto;

import com.example.demo.Member.Entity.Member;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDTO {
    @NotBlank
    private String userEmail; //TODO response 정보 수정해야함

    public MemberResponseDTO(Member member) {
        this.userEmail = member.getUserEmail();
    }
}
