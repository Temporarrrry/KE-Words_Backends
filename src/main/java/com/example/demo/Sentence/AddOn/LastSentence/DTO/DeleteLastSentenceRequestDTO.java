package com.example.demo.Sentence.AddOn.LastSentence.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteLastSentenceRequestDTO {
    @NotBlank
    private Long sentenceId;

    public LastSentenceRequestDTO toInnerDTO(Long userId) {
        return new LastSentenceRequestDTO(userId, sentenceId);
    }
}
