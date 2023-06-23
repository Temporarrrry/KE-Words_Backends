package com.example.demo.Word.dto;

import com.example.demo.Word.Entity.Word;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor // authenticationFilter에서 mapper.readValue를 위해서
@AllArgsConstructor
public class WordRequestDTO {
    @NotBlank
    private String english;

    @NotBlank
    private List<String> korean;

    public Word toEntity(){
        return new Word(english, korean);
    }
}
