package com.example.demo.Ranking.Service;

import com.example.demo.Member.DTO.MemberRequestDTO;
import com.example.demo.Member.Service.MemberService;
import com.example.demo.Ranking.DTO.RankingResponseDTO;
import com.example.demo.Ranking.Entity.TotalQuizResultType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class RankingServiceTest {

    @Autowired
    private RankingService rankingService;

    @Autowired
    private MemberService memberService;



    @Test
    void addScore() {
        memberService.register(new MemberRequestDTO("emailEx", "passwordEx"));

        String email = "emailEx";
        List<Boolean> score = List.of(true, true, true, true, false, true, false, true, true, true);
        rankingService.addScore(TotalQuizResultType.WORD, 1L, score);

        List<RankingResponseDTO> rankingList = rankingService.getRankingList(0, 20);
        for (RankingResponseDTO rankingResponseDTO : rankingList) {
            System.out.println(
                    rankingResponseDTO.getRank() + ". "
                    + rankingResponseDTO.getUserEmail()
                    + " = " + rankingResponseDTO.getScore()
            );
            System.out.println();
        }

        Long rank = rankingService.getRank(email);
        System.out.println("rank = " + rank);

        Long myScore = rankingService.getScore(email);
        System.out.println("myScore = " + myScore);
    }

}