package com.example.demo.Member.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberChangePasswordRequestDTO {

    @NotBlank
    private String password;

    @NotBlank
    private String newPassword;
}
