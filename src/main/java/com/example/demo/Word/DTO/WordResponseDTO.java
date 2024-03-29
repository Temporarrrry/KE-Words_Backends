package com.example.demo.Word.DTO;

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
    private Long id;

    @NotBlank
    private String english;

    private List<String> korean;

    Boolean isBookmarked;

    public WordResponseDTO(Word word) {
        this.id = word.getId();
        this.english = word.getEnglish();
        this.korean = Arrays.asList(word.getKorean().split("/"));
    }
}
