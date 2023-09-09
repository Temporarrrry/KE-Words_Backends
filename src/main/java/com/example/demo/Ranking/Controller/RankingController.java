package com.example.demo.Ranking.Controller;

import com.example.demo.Ranking.DTO.MyRankingResponseDTO;
import com.example.demo.Ranking.DTO.RankingResponseDTO;
import com.example.demo.Ranking.Service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ranking")
public class RankingController {

    private final RankingService rankingService;

    @GetMapping("/word/self")
    public ResponseEntity<MyRankingResponseDTO> getMyWordRanking() {
        MyRankingResponseDTO myRank = rankingService.getMyWordRank();
        return new ResponseEntity<>(myRank, HttpStatus.OK);
    }

    @GetMapping("/sentence/self")
    public ResponseEntity<MyRankingResponseDTO> getMySentenceRanking() {
        MyRankingResponseDTO myRank = rankingService.getMySentenceRank();
        return new ResponseEntity<>(myRank, HttpStatus.OK);
    }

    @GetMapping("/word")
    public ResponseEntity<List<RankingResponseDTO>> getWordRankingList(Pageable pageable) {
        if (20 < pageable.getPageSize()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        List<RankingResponseDTO> rankingList = rankingService.getWordRankingList(pageable);
        return new ResponseEntity<>(rankingList, HttpStatus.OK);
    }

    @GetMapping("/sentence")
    public ResponseEntity<List<RankingResponseDTO>> getSentenceRankingList(Pageable pageable) {
        if (20 < pageable.getPageSize()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        List<RankingResponseDTO> rankingList = rankingService.getSentenceRankingList(pageable);
        return new ResponseEntity<>(rankingList, HttpStatus.OK);
    }
}
