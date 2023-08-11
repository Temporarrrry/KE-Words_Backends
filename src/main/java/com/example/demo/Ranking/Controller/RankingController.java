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

    @GetMapping("/myWordRanking")
    public ResponseEntity<MyRankingResponseDTO> getMyWordRanking() {
        MyRankingResponseDTO myRank = rankingService.getMyWordRank();
        return new ResponseEntity<>(myRank, HttpStatus.OK);
    }

    @GetMapping("/mySentenceRanking")
    public ResponseEntity<MyRankingResponseDTO> getMySentenceRanking() {
        MyRankingResponseDTO myRank = rankingService.getMySentenceRank();
        return new ResponseEntity<>(myRank, HttpStatus.OK);
    }

    @GetMapping("/word")
    public ResponseEntity<List<RankingResponseDTO>> getWordRankingList(int startRank, int size) {
        if (20 < size) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        List<RankingResponseDTO> rankingList = rankingService.getWordRankingList(startRank, size);
        return new ResponseEntity<>(rankingList, HttpStatus.OK);
    }

    @GetMapping("/sentence")
    public ResponseEntity<List<RankingResponseDTO>> getSentenceRankingList(int startRank, int size) {
        if (20 < size) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        List<RankingResponseDTO> rankingList = rankingService.getSentenceRankingList(startRank, size);
        return new ResponseEntity<>(rankingList, HttpStatus.OK);
    }
}
