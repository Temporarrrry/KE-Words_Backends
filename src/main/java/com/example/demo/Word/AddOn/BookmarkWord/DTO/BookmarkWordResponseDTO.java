package com.example.demo.Word.AddOn.BookmarkWord.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookmarkWordResponseDTO {
    private Long wordId;
}
