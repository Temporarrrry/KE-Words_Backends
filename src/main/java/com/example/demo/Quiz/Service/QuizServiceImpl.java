package com.example.demo.Quiz.Service;

import com.example.demo.Quiz.Repository.QuizRepository;
import com.example.demo.Quiz.dto.*;
import com.example.demo.Word.Service.WordService;
import com.example.demo.Word.dto.WordResponseDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
@Transactional
public class QuizServiceImpl implements QuizService {
    private final QuizRepository quizRepository;

    private final WordService wordService;

    public QuizServiceImpl(QuizRepository quizRepository, WordService wordService) {
        this.quizRepository = quizRepository;
        this.wordService = wordService;
    }

    @Override
    public void saveQuiz(QuizRequestDTO quizRequestDTO) {
        quizRequestDTO.setQuizDate(LocalDate.now());
        quizRepository.save(quizRequestDTO.toEntity());
    }

    @Override
    public void deleteQuizById(Long id) {
        quizRepository.deleteById(id);
    }

    @Override
    public QuizEnglishProblemResponseDTO generateEnglishQuiz(int count) {
        List<WordResponseDTO> words = wordService.findWordsByRandom(count * 4);

        Stream<String> englishStream = words.stream().map(WordResponseDTO::getEnglish);
        List<String> englishes = englishStream.toList();

        List<List<String>> koreans = words.stream().map(WordResponseDTO::getKorean).toList();

        QuizEnglishProblemResponseDTO quizEnglishProblemResponseDTO = new QuizEnglishProblemResponseDTO();

        quizEnglishProblemResponseDTO.setEnglish(
                IntStream.range(0, englishes.size())
                    .filter(i -> i % 4 == 0)
                    .mapToObj(englishes::get)
                    .toList()
        );

        quizEnglishProblemResponseDTO.setAnswer(
                IntStream.range(0, koreans.size())
                        .filter(i -> i % 4 == 0)
                        .mapToObj(koreans::get)
                        .toList()
        );


        for (int i = 0; i < count * 4; i += 4) {
            List<List<String>> problemKorean = new ArrayList<>(koreans.subList(i, i + 4)); // list가 unmodifiable하기 때문에 new로 생성
            Collections.shuffle(problemKorean);
            quizEnglishProblemResponseDTO.getKoreanChoice().add(problemKorean);
        }

        return quizEnglishProblemResponseDTO;
    }

    @Override
    public QuizKoreanProblemResponseDTO generateKoreanQuiz(int count) {
        List<WordResponseDTO> words = wordService.findWordsByRandom(count * 4);

        Stream<List<String>> koreanStream = words.stream().map(WordResponseDTO::getKorean);
        List<List<String>> koreans = koreanStream.toList();

        List<String> englishes = words.stream().map(WordResponseDTO::getEnglish).toList();

        QuizKoreanProblemResponseDTO quizKoreanProblemResponseDTO = new QuizKoreanProblemResponseDTO();

        quizKoreanProblemResponseDTO.setKorean(
                IntStream.range(0, koreans.size())
                        .filter(i -> i % 4 == 0)
                        .mapToObj(koreans::get)
                        .toList()
        );

        quizKoreanProblemResponseDTO.setAnswer(
                IntStream.range(0, englishes.size())
                        .filter(i -> i % 4 == 0)
                        .mapToObj(englishes::get)
                        .toList()
        );


        for (int i = 0; i < count * 4; i += 4) {
            List<String> problemEnglish = new ArrayList<>(englishes.subList(i, i + 4)); // list가 unmodifiable하기 때문에 new로 생성
            Collections.shuffle(problemEnglish);
            quizKoreanProblemResponseDTO.getEnglishChoice().add(problemEnglish);
        }

        return quizKoreanProblemResponseDTO;
    }

    @Override
    public QuizResponseDTO findById(Long id) {
        return new QuizResponseDTO(quizRepository.findById(id));
    }

    @Override
    public AllQuizByUserIdResponseDTO findAllByUserId(Long userId) {
        return new AllQuizByUserIdResponseDTO(quizRepository.findAllByUserId(userId));
    }
}
