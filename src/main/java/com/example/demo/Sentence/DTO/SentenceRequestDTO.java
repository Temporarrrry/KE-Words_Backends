package com.example.demo.Sentence.DTO;

import com.example.demo.Sentence.Entity.Sentence;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // @RequestBody를 위해서는 기본 생성자가 필요하다.
@AllArgsConstructor
public class SentenceRequestDTO {
    @NotBlank
    private String english;

    @NotBlank
    private String korean;

    public Sentence toEntity(){
        return new Sentence(english, korean);
    }
}
