package com.example.demo.Ranking.Service;

import com.example.demo.Member.Exception.MemberNotExistException;
import com.example.demo.Member.Service.MemberService;
import com.example.demo.Ranking.DTO.MyRankingResponseDTO;
import com.example.demo.Ranking.DTO.RankingResponseDTO;
import com.example.demo.Ranking.Entity.TotalQuizResultCounter;
import com.example.demo.Ranking.Entity.TotalQuizResultType;
import com.example.demo.Ranking.Repository.TotalQuizResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class RankingService {

    private final TotalQuizResultRepository totalQuizResultRepository;

    private final MemberService memberService;

    private final RedisTemplate<String, String> redisTemplate;

    private final ZSetOperations<String, String> zSet;


    @Autowired
    public RankingService(TotalQuizResultRepository totalQuizResultRepository, MemberService memberService, RedisTemplate<String, String> redisTemplate) {
        this.totalQuizResultRepository = totalQuizResultRepository;
        this.memberService = memberService;
        this.redisTemplate = redisTemplate;
        this.zSet = redisTemplate.opsForZSet();
    }

    @Transactional
    public void addScore(TotalQuizResultType type, Long userId, Integer correctCount, Integer totalCount) {
        String userEmail = memberService.findUserEmailById(userId).orElseThrow(MemberNotExistException::new);

        totalQuizResultRepository.findByUserIdAndType(userId, type).ifPresentOrElse(
                totalQuizResult -> {
                    Integer curCorrectCount = totalQuizResult.getCorrectCount() + correctCount;
                    Integer curTotalCount = totalQuizResult.getTotalCount() + totalCount;

                    totalQuizResult.setCorrectCount(curCorrectCount);
                    totalQuizResult.setTotalCount(curTotalCount);

                    totalQuizResultRepository.save(new TotalQuizResultCounter(userId, type, curCorrectCount, curTotalCount));

                    zSet.add(type.name(), userEmail, (double) curCorrectCount / curTotalCount); //update
                },
                () -> {
                    totalQuizResultRepository.save(new TotalQuizResultCounter(userId, type, correctCount, totalCount));

                    zSet.add(type.name(), userEmail, (double) correctCount / totalCount); //insert
                }
        );
    }

    public Double getScore(TotalQuizResultType type, String userEmail) {
        if (zSet.score(type.name(), userEmail) == null) return 0D;
        else {
            return zSet.score(type.name(), userEmail);
        }
    }

    public Long getRank(TotalQuizResultType type, String userEmail) {
        return (zSet.reverseRank(type.name(), userEmail) + 1L);
    }

    private MyRankingResponseDTO getMyRank(TotalQuizResultType type) {
        String userEmail = memberService.findByAuthentication().getUserEmail();
        return new MyRankingResponseDTO(getRank(type, userEmail), getScore(type, userEmail));
    }

    public MyRankingResponseDTO getMyWordRank() {
        return getMyRank(TotalQuizResultType.WORD);
    }

    public MyRankingResponseDTO getMySentenceRank() {
        return getMyRank(TotalQuizResultType.SENTENCE);
    }

    private List<RankingResponseDTO> getRankingList(TotalQuizResultType type, int startRank, int size) {

        AtomicInteger atomicInteger = new AtomicInteger();

        return Objects.requireNonNull(zSet.reverseRangeWithScores(type.name(), startRank, startRank + size - 1))
                .stream().map(
                        tuple -> new RankingResponseDTO(
                                atomicInteger.incrementAndGet(),
                                tuple.getValue(),
                                tuple.getScore())
        ).toList();
    }


    public List<RankingResponseDTO> getWordRankingList(int startRank, int size) {
        return getRankingList(TotalQuizResultType.WORD, startRank - 1, size);
    }

    public List<RankingResponseDTO> getSentenceRankingList(int startRank, int size) {
        return getRankingList(TotalQuizResultType.SENTENCE, startRank - 1, size);
    }
}
