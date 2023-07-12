package com.example.demo.LastWord.DTO;

import com.example.demo.LastWord.Entity.LastWord;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LastWordRequestDTO {
    private Long userId;
    @NotBlank
    private Long wordId;

    public LastWord toEntity() {
        return new LastWord(userId, wordId);
    }
}
