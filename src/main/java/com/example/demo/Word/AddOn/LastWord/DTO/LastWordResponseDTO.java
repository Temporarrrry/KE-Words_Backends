package com.example.demo.Word.AddOn.LastWord.DTO;

import com.example.demo.Word.AddOn.LastWord.Entity.LastWord;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LastWordResponseDTO {
    @NotBlank
    private Long wordId;

    public LastWordResponseDTO(LastWord lastWord) {
        this.wordId = lastWord.getWordId();
    }
}
