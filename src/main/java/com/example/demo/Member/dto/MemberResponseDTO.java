package com.example.demo.Member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MemberResponseDTO {

    @NotBlank
    private String userEmail; //TODO response 정보 수정해야함 임시로 email만 넣음



}
