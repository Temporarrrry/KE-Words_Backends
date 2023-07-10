package com.example.demo.Word.BookmarkWord.Entity;

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
@IdClass(BookmarkWordPk.class)
public class BookmarkWord extends BaseTimeEntity {

    @Id
    @Column(nullable = false)
    private Long userId;

    @Id
    @Column(nullable = false)
    private Long wordId;


    public BookmarkWord(Long userId, Long wordId) {
        this.userId = userId;
        this.wordId = wordId;
    }
}