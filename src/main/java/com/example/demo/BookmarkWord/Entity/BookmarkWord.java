package com.example.demo.BookmarkWord.Entity;

import com.example.demo.Common.Entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.*;

import java.util.List;

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
    private String english;

    @Column(nullable = false)
    private String korean;


    public BookmarkWord(Long userId, String english, List<String> korean) {
        this.userId = userId;
        this.english = english;
        this.korean = String.join("/", korean);
    }
}