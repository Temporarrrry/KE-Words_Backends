package com.example.demo.Sentence.AddOn.LastSentence.Entity;

import com.example.demo.Common.Entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class LastSentence extends BaseTimeEntity {
    @Id
    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long sentenceId;
}