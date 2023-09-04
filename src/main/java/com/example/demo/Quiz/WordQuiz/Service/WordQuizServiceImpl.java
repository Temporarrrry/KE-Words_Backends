package com.example.demo.Quiz.WordQuiz.Service;

import com.example.demo.Quiz.WordQuiz.DTO.*;
import com.example.demo.Quiz.WordQuiz.Entity.WordQuiz;
import com.example.demo.Quiz.WordQuiz.Exception.WordQuizNotExistException;
import com.example.demo.Quiz.WordQuiz.Repository.WordQuizRepository;
import com.example.demo.Ranking.Entity.TotalQuizResultType;
import com.example.demo.Ranking.Service.RankingService;
import com.example.demo.Word.DTO.WordResponseDTO;
import com.example.demo.Word.Service.WordService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class WordQuizServiceImpl implements WordQuizService {
    private final WordQuizRepository wordQuizRepository;

    private final WordService wordService;

    private final RankingService rankingService;

    private Long saveQuiz(WordQuizRequestDTO wordQuizRequestDTO,
                          List<Boolean> result,
                          Integer correctCount, Integer totalCount) {

        rankingService
                .addScore(
                        TotalQuizResultType.WORD,
                        wordQuizRequestDTO.getUserId(),
                        correctCount,
                        totalCount
                );

        return wordQuizRepository.save(wordQuizRequestDTO.toEntity(result)).getId();
    }

    @Override
    public WordQuizResultResponseDTO checkQuiz(WordQuizRequestDTO wordQuizRequestDTO) {

        List<WordQuizOneProblemResultResponseDTO> wordQuizOneProblemResultResponseDTOList = wordQuizRequestDTO
                .getUserAnswers()
                .stream().map(saveWordQuizRequestDTO -> {
                    Long wordId = saveWordQuizRequestDTO.getWordId();
                    WordResponseDTO wordResponseDTO = wordService.findById(wordId);

                    List<String> answer = wordResponseDTO.getKorean();
                    List<String> userKoreanAnswer = saveWordQuizRequestDTO.getUserKoreanAnswer();

                    Boolean result = answer.equals(userKoreanAnswer);

                    return WordQuizOneProblemResultResponseDTO.builder()
                            .wordId(wordId)
                            .english(wordResponseDTO.getEnglish())
                            .originalKorean(answer)
                            .userKoreanAnswer(userKoreanAnswer)
                            .koreanChoices(saveWordQuizRequestDTO.getProblemKoreans())
                            .result(result)
                            .build();
                }
        ).toList();

        WordQuizResultResponseDTO quizResult = WordQuizResultResponseDTO.builder()
                .userId(wordQuizRequestDTO.getUserId())
                .correctCount((int) wordQuizOneProblemResultResponseDTOList.stream().filter(WordQuizOneProblemResultResponseDTO::getResult).count())
                .totalCount(wordQuizOneProblemResultResponseDTOList.size())
                .wordQuizOneProblemResultResponseDTOList(wordQuizOneProblemResultResponseDTOList)
                .build();

        List<Boolean> result = quizResult.getWordQuizOneProblemResultResponseDTOList()
                .stream().map(WordQuizOneProblemResultResponseDTO::getResult)
                .toList();

        if (wordQuizRequestDTO.getIsTest())
            quizResult.setQuizId(saveQuiz(wordQuizRequestDTO, result, quizResult.getCorrectCount(), quizResult.getTotalCount()));

        return quizResult;
    }

    @Override
    public void deleteQuiz(Long wordQuizId) throws WordQuizNotExistException {
        if (!wordQuizRepository.existsById(wordQuizId)) throw new WordQuizNotExistException();
        wordQuizRepository.deleteById(wordQuizId);
    }

    @Override
    public WordQuizProblemsResponseDTO generateEnglishWordQuiz(int count) {
        List<WordResponseDTO> words = wordService.findWordsByRandom(count * 4);

        List<List<String>> koreans = words.stream().map(WordResponseDTO::getKorean).toList();

        WordQuizProblemsResponseDTO problems = new WordQuizProblemsResponseDTO();

        for (int i = 0; i < words.size(); i += 4) {
            List<List<String>> curKoreans = new ArrayList<>(koreans.subList(i, i + 4));
            Collections.shuffle(curKoreans);

            problems.getWordQuizList().add(
                    WordQuizProblemResponseDTO.builder()
                            .wordId(words.get(i).getId())
                            .english(words.get(i).getEnglish())
                            .koreanChoice(curKoreans)
                            .build()
            );
        }

        return problems;
    }

    /*@Override
    public KoreanWordQuizResponseDTO generateKoreanWordQuiz(int count) {
        List<WordResponseDTO> words = wordService.findWordsByRandom(count * 4);

        Stream<List<String>> koreanStream = words.stream().map(WordResponseDTO::getKorean);
        List<List<String>> koreans = koreanStream.toList();

        List<String> englishes = words.stream().map(WordResponseDTO::getEnglish).toList();

        KoreanWordQuizResponseDTO koreanWordQuizResponseDTO = new KoreanWordQuizResponseDTO();

        koreanWordQuizResponseDTO.setKorean(
                IntStream.range(0, koreans.size())
                        .filter(i -> i % 4 == 0)
                        .mapToObj(koreans::get)
                        .toList()
        );

        koreanWordQuizResponseDTO.setAnswer(
                IntStream.range(0, englishes.size())
                        .filter(i -> i % 4 == 0)
                        .mapToObj(englishes::get)
                        .toList()
        );


        for (int i = 0; i < count * 4; i += 4) {
            List<String> problemEnglish = new ArrayList<>(englishes.subList(i, i + 4)); // list가 unmodifiable하기 때문에 new로 생성
            Collections.shuffle(problemEnglish);
            koreanWordQuizResponseDTO.getEnglishChoice().add(problemEnglish);
        }

        return koreanWordQuizResponseDTO;
    }*/

    private WordQuizResultResponseDTO toResultResponseDTO(WordQuiz wordQuiz) {
        List<WordResponseDTO> wordResponseDTOS = wordQuiz.getWordIds().stream().map(wordService::findById).toList();

        List<String> englishes = wordResponseDTOS.stream().map(WordResponseDTO::getEnglish).toList();
        List<List<String>> originalKoreans = wordResponseDTOS.stream().map(WordResponseDTO::getKorean).toList();

        return new WordQuizResultResponseDTO(wordQuiz, englishes, originalKoreans);
    }

    @Override
    public WordQuizResultResponseDTO findById(Long id) throws WordQuizNotExistException {
        WordQuiz wordQuiz = wordQuizRepository.findById(id).orElseThrow(WordQuizNotExistException::new);

        return toResultResponseDTO(wordQuiz);
    }

    @Override
    public Page<WordQuizResultResponseDTO> findAllByUserId(Long userId, Pageable pageable) {
        return wordQuizRepository.findAllByUserId(userId, pageable).map(
                this::toResultResponseDTO
        );
    }
}
