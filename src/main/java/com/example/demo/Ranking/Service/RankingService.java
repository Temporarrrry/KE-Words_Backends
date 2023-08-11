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

    private final String key = "ranking";

    @Autowired
    public RankingService(TotalQuizResultRepository totalQuizResultRepository, MemberService memberService, RedisTemplate<String, String> redisTemplate) {
        this.totalQuizResultRepository = totalQuizResultRepository;
        this.memberService = memberService;
        this.redisTemplate = redisTemplate;
        this.zSet = redisTemplate.opsForZSet();
    }

    @Transactional
    public void addScore(TotalQuizResultType type, Long userId, List<Boolean> result) {
        String userEmail = memberService.findUserEmailById(userId).orElseThrow(MemberNotExistException::new);

        Long correctCount = result.stream().filter(Boolean::booleanValue).count();
        Long totalCount = (long) result.size();

        totalQuizResultRepository.findByUserIdAndType(userId, type).ifPresentOrElse(
                totalQuizResult -> {
                    long curCorrectCount = totalQuizResult.getCorrectCount() + correctCount;
                    long curTotalCount = totalQuizResult.getTotalCount() + totalCount;

                    totalQuizResult.setCorrectCount(curCorrectCount);
                    totalQuizResult.setTotalCount(curTotalCount);

                    zSet.add(type.name(), userEmail, (double) curCorrectCount / curTotalCount); //update
                },
                () -> {
                    totalQuizResultRepository.save(new TotalQuizResultCounter(userId, type, correctCount, totalCount));

                    zSet.add(type.name(), userEmail, (double) correctCount / totalCount); //insert
                }
        );
    }

    public Long getScore(String userEmail) {
        if (zSet.score(key, userEmail) == null) return 0L;
        else return Objects.requireNonNull(zSet.score(key, userEmail)).longValue();
    }

    public Long getRank(String userEmail) {
        return (zSet.reverseRank(key, userEmail) + 1L);
    }

    public MyRankingResponseDTO getMyRank() {
        String userEmail = memberService.findByAuthentication().getUserEmail();
        return new MyRankingResponseDTO(getRank(userEmail), getScore(userEmail));
    }


    public List<RankingResponseDTO> getRankingList(int startRank, int size) {

        AtomicInteger atomicInteger = new AtomicInteger();

        return Objects.requireNonNull(zSet.reverseRangeWithScores(key, startRank, startRank + size - 1))
                .stream().map(
                        tuple -> new RankingResponseDTO(
                                atomicInteger.incrementAndGet(),
                                tuple.getValue(),
                                Objects.requireNonNull(tuple.getScore()).longValue())
        ).toList();
    }
}
