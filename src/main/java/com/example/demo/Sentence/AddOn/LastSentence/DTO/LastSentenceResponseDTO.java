package com.example.demo.Sentence.AddOn.LastSentence.DTO;

import com.example.demo.Sentence.AddOn.LastSentence.Entity.LastSentence;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LastSentenceResponseDTO {
    private Long sentenceId;

    public LastSentenceResponseDTO(LastSentence lastSentence) {
        this.sentenceId = lastSentence.getSentenceId();
    }
}
