package com.example.demo.Word.AddOn.BookmarkWord.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteBookmarkWordRequestDTO {
    private Long wordId;

    public BookmarkWordRequestDTO toInnerDTO(Long userId) {
        return new BookmarkWordRequestDTO(userId, wordId);
    }
}
