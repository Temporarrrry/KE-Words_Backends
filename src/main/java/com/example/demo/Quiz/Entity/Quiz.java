package com.example.demo.Quiz.Entity;

import com.example.demo.Common.Entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Quiz extends BaseTimeEntity {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private long userId;

    @Column(nullable = false)
    private String english;



    private String result;


    @Builder
    public Quiz(Long userId, List<String> english, List<Boolean> result) {
        this.userId = userId;
        this.english = String.join("|", english);
        this.result = String.join("|", result.stream()
                .map(aBoolean -> (aBoolean) ? "1" : "0").toList());
    }
}