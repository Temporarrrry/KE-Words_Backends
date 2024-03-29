package com.example.demo.Member.DTO;

import com.example.demo.Member.Entity.Member;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoResponseDTO {
    @NotBlank
    private String userEmail;

    private LocalDate createdAt;

    public MemberInfoResponseDTO(Member member) {
        this.userEmail = member.getUserEmail();
        this.createdAt = member.getCreateTime().toLocalDate();
    }
}
