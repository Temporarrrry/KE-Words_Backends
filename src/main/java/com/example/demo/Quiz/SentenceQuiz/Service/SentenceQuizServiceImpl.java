package com.example.demo.Quiz.SentenceQuiz.Service;

import com.example.demo.Common.Exception.NoAuthorityException;
import com.example.demo.Quiz.SentenceQuiz.DTO.*;
import com.example.demo.Quiz.SentenceQuiz.DTO.FillingQuiz.FillingQuizProblem;
import com.example.demo.Quiz.SentenceQuiz.DTO.FillingQuiz.FillingQuizProblemsResponseDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.OrderingQuiz.OrderingQuizProblem;
import com.example.demo.Quiz.SentenceQuiz.DTO.OrderingQuiz.OrderingQuizProblemsResponseDTO;
import com.example.demo.Quiz.SentenceQuiz.Entity.SentenceQuiz;
import com.example.demo.Quiz.SentenceQuiz.Exception.SentenceQuizNotExistException;
import com.example.demo.Quiz.SentenceQuiz.Exception.SentenceTooShortException;
import com.example.demo.Quiz.SentenceQuiz.Repository.SentenceQuizRepository;
import com.example.demo.Ranking.Entity.TotalQuizResultType;
import com.example.demo.Ranking.Service.RankingService;
import com.example.demo.Sentence.DTO.SentenceResponseDTO;
import com.example.demo.Sentence.Service.SentenceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class SentenceQuizServiceImpl implements SentenceQuizService {

    private final SentenceQuizRepository sentenceQuizRepository;

    private final SentenceService sentenceService;

    private final RankingService rankingService;


    private Long saveQuiz(SentenceQuizRequestDTO sentenceQuizRequestDTO,
                          List<Boolean> result,
                          Integer correctCount, Integer totalCount) {

        rankingService.addScore(
                TotalQuizResultType.SENTENCE,
                sentenceQuizRequestDTO.getUserId(),
                correctCount,
                totalCount
        );

        return sentenceQuizRepository.save(sentenceQuizRequestDTO.toEntity(result)).getId();
    }

    @Override
    public SentenceQuizResultResponseDTO checkQuiz(SentenceQuizRequestDTO sentenceQuizRequestDTO) {

        List<SentenceQuizOneProblemResultResponseDTO> sentenceQuizOneProblemResultResponseDTOS = sentenceQuizRequestDTO
                .getUserAnswers().stream().map(
                        saveSentenceQuizRequestDTO -> {
                            Long sentenceId = saveSentenceQuizRequestDTO.getSentenceId();
                            List<String> userAnswer = saveSentenceQuizRequestDTO.getUserAnswer();

                            SentenceResponseDTO sentenceResponseDTO = sentenceService.findById(sentenceId);
                            List<String> originalSentence = Arrays.asList(sentenceResponseDTO.getEnglish().split(" "));


                            return SentenceQuizOneProblemResultResponseDTO.builder()
                                    .sentenceId(sentenceId)
                                    .originalSentence(originalSentence)
                                    .problemSentence(saveSentenceQuizRequestDTO.getProblemSentence())
                                    .userAnswer(userAnswer)
                                    .result(originalSentence.equals(userAnswer))
                                    .build();
                        })
                .toList();


        List<Boolean> result = sentenceQuizOneProblemResultResponseDTOS
                .stream().map(SentenceQuizOneProblemResultResponseDTO::getResult)
                .toList();

        SentenceQuizResultResponseDTO quizResultList = SentenceQuizResultResponseDTO.builder()
                .userId(sentenceQuizRequestDTO.getUserId())
                .sentenceQuizOneProblemResultResponseDTOList(sentenceQuizOneProblemResultResponseDTOS)
                .correctCount((int) result.stream().filter(Boolean::booleanValue).count())
                .totalCount(sentenceQuizOneProblemResultResponseDTOS.size())
                .build();


        if (sentenceQuizRequestDTO.getIsTest())
            quizResultList.setQuizId(
                    saveQuiz(
                            sentenceQuizRequestDTO,
                            result,
                            quizResultList.getCorrectCount(), quizResultList.getTotalCount()
                    )
            );

        return quizResultList;
    }

    @Override
    public void deleteQuiz(Long userId, DeleteSentenceQuizRequestDTO deleteSentenceQuizRequestDTO) throws NoAuthorityException {
        if (!findById(deleteSentenceQuizRequestDTO.getSentenceQuizId()).getUserId().equals(userId))
            throw new NoAuthorityException();

        sentenceQuizRepository.deleteById(deleteSentenceQuizRequestDTO.getSentenceQuizId());
    }

    @Override
    public FillingQuizProblemsResponseDTO generateFillingSentenceQuiz(int count) throws SentenceTooShortException {
        Random random = new Random();

        List<SentenceResponseDTO> sentenceResponseDTOs = sentenceService.findByRandom(count);

        FillingQuizProblemsResponseDTO fillingQuizProblemsResponseDTO = new FillingQuizProblemsResponseDTO();

        for (SentenceResponseDTO sentenceResponseDTO : sentenceResponseDTOs) {
            List<String> originalSentence = Arrays.asList(sentenceResponseDTO.getEnglish().split(" "));

            int sentenceSize = originalSentence.size();

            if (sentenceSize < 2) throw new SentenceTooShortException(); // 문장이 너무 짧으면 예외 발생

            List<String> blankedSentence = new ArrayList<>(originalSentence);

            int blankCount = random.nextInt(sentenceSize / 4, sentenceSize / 2);
            blankCount = Math.max(blankCount, 1);

            HashSet<Integer> blankIndex = new HashSet<>();
            while (blankIndex.size() < blankCount) {
                blankIndex.add(random.nextInt(sentenceSize));
            }
            for (int index : blankIndex) {
                blankedSentence.set(index, "______");
            }


            FillingQuizProblem fillingQuizProblem = FillingQuizProblem.builder()
                    .sentenceId(sentenceResponseDTO.getId())
                    .blankedEnglish(blankedSentence)
                    .korean(sentenceResponseDTO.getKorean())
                    .build();

            fillingQuizProblemsResponseDTO.getFillingQuizProblems().add(fillingQuizProblem);
        }

        return fillingQuizProblemsResponseDTO;
    }

    @Override
    public OrderingQuizProblemsResponseDTO generateOrderingSentenceQuiz(int count) {
        List<SentenceResponseDTO> sentenceResponseDTOs = sentenceService.findByRandom(count);

        OrderingQuizProblemsResponseDTO orderingQuizProblemsResponseDTO = new OrderingQuizProblemsResponseDTO();

        for (SentenceResponseDTO sentenceResponseDTO : sentenceResponseDTOs) {
            List<String> originalSentence = Arrays.asList(sentenceResponseDTO.getEnglish().split(" "));

            List<String> shuffledSentence = new ArrayList<>(originalSentence);
            Collections.shuffle(shuffledSentence);


            OrderingQuizProblem orderingQuizProblem = OrderingQuizProblem.builder()
                    .sentenceId(sentenceResponseDTO.getId())
                    .shuffledEnglish(shuffledSentence)
                    .korean(sentenceResponseDTO.getKorean())
                    .build();

            orderingQuizProblemsResponseDTO.getOrderingQuizProblems().add(orderingQuizProblem);
        }

        return orderingQuizProblemsResponseDTO;
    }

    @Override
    public SentenceQuizResultResponseDTO findById(Long id) {
        SentenceQuiz sentenceQuiz = sentenceQuizRepository.findById(id)
                .orElseThrow(SentenceQuizNotExistException::new);

        List<List<String>> english = sentenceQuiz.getSentenceIds().stream()
                .map(sentenceService::findById)
                .map(SentenceResponseDTO::getEnglish)
                .map(s -> Arrays.stream(s.split(" ")).toList())
                .toList();


        return new SentenceQuizResultResponseDTO(sentenceQuiz, english);
    }

    @Override
    public List<SentenceQuizResultResponseDTO> findAllByUserId(Long userId, Pageable pageable) {

        return sentenceQuizRepository.findAllByUserId(userId, pageable) // 퀴즈 여러 개
                .map(sentenceQuiz -> { // 퀴즈 하나
                            List<List<String>> originalSentences = sentenceQuiz.getSentenceIds()
                                    .stream().map(sentenceService::findById)
                                    .map(SentenceResponseDTO::getEnglish)
                                    .map(s -> Arrays.stream(s.split(" ")).toList())
                                    .toList();

                            return new SentenceQuizResultResponseDTO(sentenceQuiz, originalSentences);
                }).getContent();
    }
}