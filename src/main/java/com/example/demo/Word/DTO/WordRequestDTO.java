package com.example.demo.Word.DTO;

import com.example.demo.Word.Entity.Word;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor // @RequestBody를 위해서는 기본 생성자가 필요하다.
@AllArgsConstructor
public class WordRequestDTO {
    @NotBlank
    private String english;

    @NotBlank
    private List<String> korean;

    public Word toEntity(){
        return new Word(english, korean);
    }
}
