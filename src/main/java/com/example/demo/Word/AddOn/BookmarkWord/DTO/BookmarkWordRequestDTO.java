package com.example.demo.Word.AddOn.BookmarkWord.DTO;

import com.example.demo.Word.AddOn.BookmarkWord.Entity.BookmarkWord;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkWordRequestDTO {
    private Long userId;
    @NotBlank
    private Long wordId;

    public BookmarkWord toEntity() {
        return new BookmarkWord(userId, wordId);
    }
}
