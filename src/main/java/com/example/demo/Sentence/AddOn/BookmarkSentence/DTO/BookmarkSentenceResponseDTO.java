package com.example.demo.Sentence.AddOn.BookmarkSentence.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookmarkSentenceResponseDTO {
    private Long sentenceId;
}
