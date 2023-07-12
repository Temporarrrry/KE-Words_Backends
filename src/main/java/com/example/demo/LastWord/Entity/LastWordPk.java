package com.example.demo.LastWord.Entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class LastWordPk implements Serializable {
    private Long userId;
    private Long wordId;
}
