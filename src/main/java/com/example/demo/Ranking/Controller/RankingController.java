package com.example.demo.Ranking.Controller;

import com.example.demo.Ranking.DTO.MyRankingResponseDTO;
import com.example.demo.Ranking.DTO.RankingResponseDTO;
import com.example.demo.Ranking.Service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ranking")
public class RankingController {

    private final RankingService rankingService;

    @GetMapping("/myRanking")
    public ResponseEntity<MyRankingResponseDTO> getMyRanking() {
        MyRankingResponseDTO myRank = rankingService.getMyRank();
        return new ResponseEntity<>(myRank, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<RankingResponseDTO>> getRankingList(int startRank, int size) {
        if (20 < size) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        List<RankingResponseDTO> rankingList = rankingService.getRankingList(startRank, size);
        return new ResponseEntity<>(rankingList, HttpStatus.OK);
    }
}
