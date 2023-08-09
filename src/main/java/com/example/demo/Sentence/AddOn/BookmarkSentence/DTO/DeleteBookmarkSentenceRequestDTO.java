package com.example.demo.Sentence.AddOn.BookmarkSentence.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteBookmarkSentenceRequestDTO {
    private Long sentenceId;

    public BookmarkSentenceRequestDTO toInnerDTO(Long userId) {
        return new BookmarkSentenceRequestDTO(userId, sentenceId);
    }
}
