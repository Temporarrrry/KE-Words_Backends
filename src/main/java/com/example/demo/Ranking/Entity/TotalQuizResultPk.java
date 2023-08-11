package com.example.demo.Ranking.Entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class TotalQuizResultPk implements Serializable {

    private Long userId;

    private TotalQuizResultType type;
}
