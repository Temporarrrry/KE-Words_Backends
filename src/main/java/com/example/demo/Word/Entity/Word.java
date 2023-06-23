package com.example.demo.Word.Entity;

import com.example.demo.Common.Entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Word extends BaseTimeEntity {

    @Id
    @Column(nullable = false)
    private String english;

    @Column(nullable = false)
    private String korean;


    @Builder
    public Word(String english, List<String> korean) {
        this.english = english;
        this.korean = String.join("/", korean);
    }
}