package com.example.demo.Sentence.DTO;

import com.example.demo.Sentence.Serializer.SentenceEnglishSerializer;
import com.example.demo.Sentence.Serializer.SentenceKoreanSerializer;
import com.example.demo.Sentence.Entity.Sentence;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SentenceResponseDTO {
    private Long id;

    @JsonSerialize(using = SentenceEnglishSerializer.class)
    private List<String> english;

    @JsonSerialize(using = SentenceKoreanSerializer.class)
    private String korean;

    public SentenceResponseDTO(Sentence sentence) {
        this.id = sentence.getId();
        this.english = Arrays.stream(sentence.getEnglish().split(" ")).toList();
        this.korean = sentence.getKorean();
    }
}
