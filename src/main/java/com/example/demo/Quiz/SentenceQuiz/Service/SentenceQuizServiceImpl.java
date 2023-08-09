package com.example.demo.Quiz.SentenceQuiz.Service;

import com.example.demo.Member.Exception.MemberNotExistException;
import com.example.demo.Member.Service.MemberService;
import com.example.demo.Quiz.SentenceQuiz.DTO.DeleteSentenceQuizRequestDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.FillingQuiz.FillingQuizProblem;
import com.example.demo.Quiz.SentenceQuiz.DTO.FillingQuiz.FillingQuizProblemsResponseDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.OrderingQuiz.OrderingQuizProblem;
import com.example.demo.Quiz.SentenceQuiz.DTO.OrderingQuiz.OrderingQuizProblemsResponseDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.SentenceQuizRequestDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.SentenceQuizResponseDTO;
import com.example.demo.Quiz.SentenceQuiz.Entity.SentenceQuiz;
import com.example.demo.Quiz.SentenceQuiz.Exception.SentenceQuizNotExistException;
import com.example.demo.Quiz.SentenceQuiz.Exception.SentenceTooShortException;
import com.example.demo.Quiz.SentenceQuiz.Repository.SentenceQuizRepository;
import com.example.demo.Sentence.DTO.SentenceResponseDTO;
import com.example.demo.Sentence.Service.SentenceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class SentenceQuizServiceImpl implements SentenceQuizService {

    private final SentenceQuizRepository sentenceQuizRepository;

    private final SentenceService sentenceService;

    //private final RankingService rankingService;

    private final MemberService memberService;

    private List<Boolean> scoring(List<String> answer, List<String> userAnswer) {
        return List.of(answer.equals(userAnswer));
    }

    @Override
    public void saveQuiz(SentenceQuizRequestDTO sentenceQuizRequestDTO) {
        List<String> answer = sentenceQuizRequestDTO.getSentenceIds()
                .stream().map(sentence -> sentenceService.findById(sentence).getEnglish()).toList();
        List<String> userAnswers = sentenceQuizRequestDTO.getUserAnswers();

        List<Boolean> result = scoring(answer, userAnswers);

        long score = result.stream().filter(Boolean::booleanValue).count();
        String userEmail = memberService.findUserEmailById(sentenceQuizRequestDTO.getUserId())
                .orElseThrow(MemberNotExistException::new);

        //rankingService.addScore(userEmail, score);

        sentenceQuizRepository.save(sentenceQuizRequestDTO.toEntity(result));
    }

    @Override
    public void deleteQuiz(DeleteSentenceQuizRequestDTO deleteSentenceQuizRequestDTO) {
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
                    .originalEnglish(originalSentence)
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
                    .originalEnglish(originalSentence)
                    .shuffledEnglish(shuffledSentence)
                    .korean(sentenceResponseDTO.getKorean())
                    .build();

            orderingQuizProblemsResponseDTO.getOrderingQuizProblems().add(orderingQuizProblem);
        }

        return orderingQuizProblemsResponseDTO;
    }

    @Override
    public SentenceQuizResponseDTO findById(Long id) {
        SentenceQuiz sentenceQuiz = sentenceQuizRepository.findById(id)
                .orElseThrow(SentenceQuizNotExistException::new);

        List<String> english = Arrays.stream(sentenceQuiz.getSentenceIds().split("|"))
                .map(Long::parseLong)
                .map(sentenceService::findById)
                .map(SentenceResponseDTO::getEnglish)
                .toList();


        return new SentenceQuizResponseDTO(sentenceQuiz, english);
    }

    @Override
    public Page<SentenceQuizResponseDTO> findAllByUserId(Long userId, Pageable pageable) {
        return sentenceQuizRepository.findAllByUserId(userId, pageable)
                .map(sentenceQuiz -> new SentenceQuizResponseDTO(
                        sentenceQuiz,
                        Arrays.stream(sentenceQuiz.getSentenceIds().split("|"))
                                .map(Long::parseLong)
                                .map(sentenceService::findById)
                                .map(SentenceResponseDTO::getEnglish).toList()
                        )
                );
    }
}
