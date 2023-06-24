package com.example.demo.Quiz.Service;

import com.example.demo.Quiz.Repository.QuizRepository;
import com.example.demo.Quiz.dto.QuizProblemResponseDTO;
import com.example.demo.Quiz.dto.QuizRequestDTO;
import com.example.demo.Quiz.dto.QuizResponseDTO;
import com.example.demo.Word.Service.WordService;
import com.example.demo.Word.dto.WordResponseDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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
        quizRepository.save(quizRequestDTO.toEntity());
    }

    @Override
    public void deleteQuizById(Long id) {
        quizRepository.deleteById(id);
    }

    @Override
    public QuizProblemResponseDTO generateQuiz(int count) {
        List<WordResponseDTO> words = wordService.findWords(count * 4);

        Stream<String> englishStream = words.stream().map(WordResponseDTO::getEnglish);
        List<String> englishes = englishStream.toList();

        List<List<String>> koreans = words.stream().map(WordResponseDTO::getKorean).toList();

        QuizProblemResponseDTO quizProblemResponseDTO = new QuizProblemResponseDTO();

        quizProblemResponseDTO.setEnglish(
                IntStream.range(0, englishes.size())
                    .filter(i -> i % 4 == 0)
                    .mapToObj(englishes::get)
                    .toList()
        );
        System.out.print("korean: ");
        System.out.println(koreans);

        for (int i = 0; i < count * 4; i += 4) {
            List<List<String>> problemKorean = new ArrayList<>(koreans.subList(i, i + 4)); // list가 unmodifiable하기 때문에 new로 생성
            Collections.shuffle(problemKorean);
            quizProblemResponseDTO.getKoreanChoice().add(problemKorean);
        }

        return quizProblemResponseDTO;
    }

    @Override
    public QuizResponseDTO findById(Long id) {
        return new QuizResponseDTO(quizRepository.findById(id));
    }

    @Override
    public List<QuizResponseDTO> findByUserId(Long userId) {
        return quizRepository.findAllByUserId(userId).stream().map(QuizResponseDTO::new).toList();
    }
}
