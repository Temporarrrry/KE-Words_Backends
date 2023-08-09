package com.example.demo.Word.AddOn.LastWord.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveLastWordRequestDTO {
    private Long wordId;

    public LastWordRequestDTO toInnerDTO(Long userId) {
        return new LastWordRequestDTO(userId, wordId);
    }
}
