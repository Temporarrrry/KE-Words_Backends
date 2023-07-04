package com.example.demo.BookmarkWord.DTO;

import com.example.demo.BookmarkWord.Entity.BookmarkWord;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookmarkWordResponseDTO {
    @NotBlank
    private String english;
    @NotBlank
    private String korean;

    public BookmarkWordResponseDTO(BookmarkWord bookmarkWord) {
        this.english = bookmarkWord.getEnglish();
        this.korean = bookmarkWord.getKorean();
    }
}
