package com.example.demo.BookmarkWord.DTO;

import com.example.demo.BookmarkWord.Entity.BookmarkWord;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkWordRequestDTO {
    private Long userId;
    @NotBlank
    private String english;

    public BookmarkWord toEntity(List<String> korean) {
        return new BookmarkWord(userId, english, korean);
    }
}
