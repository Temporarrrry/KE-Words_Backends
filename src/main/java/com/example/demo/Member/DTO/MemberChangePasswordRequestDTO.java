package com.example.demo.Member.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberChangePasswordRequestDTO {
    private String newPassword;
}
