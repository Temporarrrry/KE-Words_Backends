package com.example.demo.Word.AddOn.LastWord.DTO;

import com.example.demo.Word.AddOn.LastWord.Entity.LastWord;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LastWordRequestDTO {
    private Long userId;
    private Long wordId;

    public LastWordRequestDTO(Long userId) {
        this.userId = userId;
    }

    public LastWord toEntity() {
        return new LastWord(userId, wordId);
    }
}
