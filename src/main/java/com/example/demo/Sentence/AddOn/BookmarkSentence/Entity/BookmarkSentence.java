package com.example.demo.Sentence.AddOn.BookmarkSentence.Entity;

import com.example.demo.Common.Entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@IdClass(BookmarkSentencePk.class)
public class BookmarkSentence extends BaseTimeEntity {

    @Id
    @Column(nullable = false)
    private Long userId;

    @Id
    @Column(nullable = false)
    private Long sentenceId;


    public BookmarkSentence(Long userId, Long sentenceId) {
        this.userId = userId;
        this.sentenceId = sentenceId;
    }
}