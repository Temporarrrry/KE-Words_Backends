package com.example.demo.Sentence.AddOn.LastSentence.DTO;

import com.example.demo.Sentence.AddOn.LastSentence.Entity.LastSentence;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LastSentenceRequestDTO {
    private Long userId;
    private Long sentenceId;

    public LastSentenceRequestDTO(Long userId) {
        this.userId = userId;
    }

    public LastSentence toEntity() {
        return new LastSentence(userId, sentenceId);
    }
}
