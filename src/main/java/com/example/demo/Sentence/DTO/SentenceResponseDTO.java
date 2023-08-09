package com.example.demo.Sentence.DTO;

import com.example.demo.Sentence.Entity.Sentence;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SentenceResponseDTO {
    private Long id;
    @NotBlank
    private String english;

    private List<String> korean;

    boolean isBookmarked;

    public SentenceResponseDTO(Sentence sentence, boolean isBookmarked) {
        this.id = sentence.getId();
        this.english = sentence.getEnglish();
        this.korean = new ArrayList<>(Collections.singleton(sentence.getKorean()));
        this.isBookmarked = isBookmarked;
    }
}
