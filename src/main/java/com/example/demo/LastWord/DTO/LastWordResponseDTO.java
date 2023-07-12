package com.example.demo.LastWord.DTO;

import com.example.demo.LastWord.Entity.LastWord;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class LastWordResponseDTO {
    @NotBlank
    private Long wordId;

    public LastWordResponseDTO(LastWord lastWord) {
        this.wordId = lastWord.getWordId();
    }
}
