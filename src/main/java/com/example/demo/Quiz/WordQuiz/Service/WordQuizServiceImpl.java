package com.example.demo.Quiz.WordQuiz.Service;

import com.example.demo.Quiz.WordQuiz.DTO.*;
import com.example.demo.Quiz.WordQuiz.Entity.WordQuiz;
import com.example.demo.Quiz.WordQuiz.Exception.WordQuizNotExistException;
import com.example.demo.Quiz.WordQuiz.Repository.WordQuizRepository;
import com.example.demo.Word.DTO.WordResponseDTO;
import com.example.demo.Word.Service.WordService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

@Service
@Transactional
@RequiredArgsConstructor
public class WordQuizServiceImpl implements WordQuizService {
    private final WordQuizRepository wordQuizRepository;

    private final WordService wordService;


    private List<Boolean> scoring(List<List<String>> answer, List<List<String>> userAnswer) {
        List<Boolean> score = new ArrayList<>();

        IntStream.range(0, answer.size()).forEach(i -> {
            List<String> answerList = answer.get(i);
            List<String> userAnswerList = userAnswer.get(i);

            if (answerList.size() != userAnswerList.size()) {
                score.add(false);
                return;
            }

            score.add(answerList.equals(userAnswerList));
        });

        return score;
    }

    @Override
    public void saveQuiz(WordQuizRequestDTO wordQuizRequestDTO) {
        List<WordQuizAnswerRequestDTO> userAnswers = wordQuizRequestDTO.getUserAnswers();

        List<List<String>> koreanAnswer = userAnswers
                .stream().map(WordQuizAnswerRequestDTO::getWordId)
                .map(id -> wordService.findById(id).getKorean())
                .toList();

        List<List<String>> userKoreanAnswers = userAnswers
                .stream().map(WordQuizAnswerRequestDTO::getUserKoreanAnswer)
                .toList();

        List<Boolean> score = scoring(koreanAnswer, userKoreanAnswers);

        wordQuizRepository.save(wordQuizRequestDTO.toEntity(score));
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
        List<String> englishes = Arrays.stream(wordQuiz.getWordIds().split("\\|"))
                .map(Long::parseLong)
                .map(wordService::findById)
                .map(WordResponseDTO::getEnglish)
                .toList();

        return new WordQuizResultResponseDTO(englishes, wordQuiz);
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
