package com.example.demo.Sentence.AddOn.BookmarkSentence.Entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class BookmarkSentencePk implements Serializable {
    private Long userId;
    private Long sentenceId;
}
