package com.example.demo.Sentence.AddOn.BookmarkSentence.DTO;

import com.example.demo.Sentence.AddOn.BookmarkSentence.Entity.BookmarkSentence;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkSentenceRequestDTO {
    private Long sentenceId;
    private Long userId;

    public BookmarkSentence toEntity() {
        return new BookmarkSentence(userId, sentenceId);
    }
}
