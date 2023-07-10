package com.example.demo.Sentence.DTO;

import com.example.demo.Sentence.Entity.Sentence;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SentenceResponseDTO {
    @NotBlank
    private Long id;
    @NotBlank
    private String english;

    @NotBlank
    private String korean;

    public SentenceResponseDTO(Sentence sentence) {
        this.id = sentence.getId();
        this.english = sentence.getEnglish();
        this.korean = sentence.getKorean();
    }
}
