package com.example.demo.Word.dto;

import com.example.demo.Word.Entity.Word;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WordResponseDTO {
    @NotBlank
    private String english;

    @NotBlank
    private List<String> korean;

    public WordResponseDTO(Word word) {
        this.english = word.getEnglish();
        this.korean = Arrays.asList(word.getKorean().split("/"));
    }
}
