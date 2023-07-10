package com.example.demo.Word.BookmarkWord.DTO;

import com.example.demo.Word.BookmarkWord.Entity.BookmarkWord;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveBookmarkWordRequestDTO {
    @NotBlank
    private Long wordId;

    public BookmarkWordRequestDTO toInnerDTO(Long userId) {
        return new BookmarkWordRequestDTO(userId, wordId);
    }
}
