package com.example.demo.Ranking.Service;

import com.example.demo.Member.DTO.MemberRequestDTO;
import com.example.demo.Member.Service.MemberService;
import com.example.demo.Ranking.DTO.RankingResponseDTO;
import com.example.demo.Ranking.Entity.TotalQuizResultType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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
        rankingService.addScore(TotalQuizResultType.WORD, 1L, 4, 10);

        List<RankingResponseDTO> rankingList = rankingService.getWordRankingList(new Pageable() {
            @Override
            public int getPageNumber() {
                return 0;
            }

            @Override
            public int getPageSize() {
                return 5;
            }

            @Override
            public long getOffset() {
                return 0;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public Pageable next() {
                return null;
            }

            @Override
            public Pageable previousOrFirst() {
                return null;
            }

            @Override
            public Pageable first() {
                return null;
            }

            @Override
            public Pageable withPage(int pageNumber) {
                return null;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }
        });
        for (RankingResponseDTO rankingResponseDTO : rankingList) {
            System.out.println(
                    rankingResponseDTO.getRank() + ". "
                    + rankingResponseDTO.getUserEmail()
                    + " = " + rankingResponseDTO.getScore()
            );
            System.out.println();
        }
    }

}