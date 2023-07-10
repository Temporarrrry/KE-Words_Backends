package com.example.demo.Word.BookmarkWord.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookmarkWordResponseDTO {
    @NotBlank
    private Long wordId;
}
