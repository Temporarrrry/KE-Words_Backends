package com.example.demo.Word.BookmarkWord.Entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class BookmarkWordPk implements Serializable {
    private Long userId;
    private Long wordId;
}
