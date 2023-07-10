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
    @NotBlank
    private Long id;

    @NotBlank
    private String english;

    @NotBlank
    private List<String> korean;

    boolean isBookmarked;

    public WordResponseDTO(Word word, boolean isBookmarked) {
        this.id = word.getId();
        this.english = word.getEnglish();
        this.korean = Arrays.asList(word.getKorean().split("/"));
        this.isBookmarked = isBookmarked;
    }
}
