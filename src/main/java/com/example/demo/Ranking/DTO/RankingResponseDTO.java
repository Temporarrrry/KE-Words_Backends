package com.example.demo.Ranking.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RankingResponseDTO {

    private Integer rank;

    private String userEmail;

    private Long score;
}
