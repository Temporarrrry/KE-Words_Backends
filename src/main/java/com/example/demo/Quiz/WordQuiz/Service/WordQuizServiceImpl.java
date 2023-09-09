package com.example.demo.Quiz.WordQuiz.Service;

import com.example.demo.Common.Exception.NoAuthorityException;
import com.example.demo.Quiz.WordQuiz.DTO.Request.GenerateWordQuizRequestDTO;
import com.example.demo.Quiz.WordQuiz.DTO.Request.Grade.GradeWordQuizTestProblemRequestDTO;
import com.example.demo.Quiz.WordQuiz.DTO.Request.Grade.GradeWordQuizTestRequestDTO;
import com.example.demo.Quiz.WordQuiz.DTO.Request.Test.SaveWordQuizTestRequestDTO;
import com.example.demo.Quiz.WordQuiz.DTO.Response.Common.WordQuizCommonProblemResponseDTO;
import com.example.demo.Quiz.WordQuiz.DTO.Response.Common.WordQuizCommonProblemsResponseDTO;
import com.example.demo.Quiz.WordQuiz.DTO.Response.Practice.WordQuizPracticeProblemsResponseDTO;
import com.example.demo.Quiz.WordQuiz.DTO.Response.Result.WordQuizProblemsResultResponseDTO;
import com.example.demo.Quiz.WordQuiz.DTO.Response.Test.WordQuizTestProblemsResponseDTO;
import com.example.demo.Quiz.WordQuiz.Entity.WordQuiz;
import com.example.demo.Quiz.WordQuiz.Exception.WordQuizAnswerLengthNotMatchException;
import com.example.demo.Quiz.WordQuiz.Exception.WordQuizAnswerNotMatchException;
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

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
@Transactional
@RequiredArgsConstructor
public class WordQuizServiceImpl implements WordQuizService {
    private final WordQuizRepository wordQuizRepository;

    private final WordService wordService;

    private final RankingService rankingService;


    //CREATE
    private Long saveBaseQuiz(SaveWordQuizTestRequestDTO saveWordQuizTestRequestDTO) {

        rankingService.addScore(
                TotalQuizResultType.WORD,
                saveWordQuizTestRequestDTO.getUserId(),
                0, // updateQuiz에서 계산
                saveWordQuizTestRequestDTO.getProblems().size()
        );


        List<Boolean> FalseResult = Stream.generate(() -> false)
                .limit(saveWordQuizTestRequestDTO.getProblems().size())
                .toList();

        WordQuiz wordQuiz = saveWordQuizTestRequestDTO.toEntity(Optional.empty(), FalseResult);
        return wordQuizRepository.save(wordQuiz).getId();
    }

    private WordQuizCommonProblemsResponseDTO generateWordQuiz(GenerateWordQuizRequestDTO generateWordQuizRequestDTO) {
        Long userId = generateWordQuizRequestDTO.getUserId();
        Integer count = generateWordQuizRequestDTO.getCount();
        Boolean isTest = generateWordQuizRequestDTO.getIsTest();

        List<WordResponseDTO> words = wordService.findWordsByRandom(count * 4);

        List<List<String>> koreans = words.stream().map(WordResponseDTO::getKorean).toList();

        WordQuizCommonProblemsResponseDTO problems = new WordQuizCommonProblemsResponseDTO();
        for (int i = 0; i < words.size(); i += 4) {
            List<String> originalKorean = koreans.get(i);

            List<List<String>> koreanChoices = new ArrayList<>(koreans.subList(i, i + 4));
            Collections.shuffle(koreanChoices);

            problems.getWordQuizList().add(
                    WordQuizCommonProblemResponseDTO.builder()
                            .wordId(words.get(i).getId())
                            .english(words.get(i).getEnglish())
                            .originalKorean((isTest) ? null : originalKorean)
                            .koreanChoice(koreanChoices)
                            .build()
            );
        }

        if (isTest) {
            List<Long> wordIds = problems.getWordQuizList()
                    .stream().map(WordQuizCommonProblemResponseDTO::getWordId)
                    .toList();

            List<List<List<String>>> koreanChoices = problems.getWordQuizList()
                    .stream().map(WordQuizCommonProblemResponseDTO::getKoreanChoice)
                    .toList();

            problems.setQuizId(saveBaseQuiz(new SaveWordQuizTestRequestDTO(userId, wordIds, koreanChoices)));
        }

        return problems;
    }

    @Override
    @Transactional
    public WordQuizPracticeProblemsResponseDTO getPractice(GenerateWordQuizRequestDTO generateWordQuizRequestDTO) {
        return new WordQuizPracticeProblemsResponseDTO(generateWordQuiz(generateWordQuizRequestDTO));
    }

    @Override
    @Transactional
    public WordQuizTestProblemsResponseDTO getTest(GenerateWordQuizRequestDTO generateWordQuizRequestDTO) {
        return new WordQuizTestProblemsResponseDTO(generateWordQuiz(generateWordQuizRequestDTO));
    }


    //DELETE

    @Override
    @Transactional
    public void deleteQuiz(Long wordQuizId) throws WordQuizNotExistException {
        if (!wordQuizRepository.existsById(wordQuizId)) throw new WordQuizNotExistException();
        wordQuizRepository.deleteById(wordQuizId);
    }

    //READ

    private WordQuizProblemsResultResponseDTO toResultResponseDTO(WordQuiz wordQuiz) {
        List<WordResponseDTO> wordResponseDTOS = wordQuiz.getWordIds().stream().map(wordService::findById).toList();

        List<String> englishes = wordResponseDTOS.stream().map(WordResponseDTO::getEnglish).toList();
        List<List<String>> originalKoreans = wordResponseDTOS.stream().map(WordResponseDTO::getKorean).toList();

        return new WordQuizProblemsResultResponseDTO(wordQuiz, englishes, originalKoreans);
    }

    @Override
    public WordQuizProblemsResultResponseDTO findById(Long id) throws WordQuizNotExistException {
        WordQuiz wordQuiz = wordQuizRepository.findById(id).orElseThrow(WordQuizNotExistException::new);

        return toResultResponseDTO(wordQuiz);
    }

    @Override
    public Page<WordQuizProblemsResultResponseDTO> findAllByUserId(Long userId, Pageable pageable) {
        return wordQuizRepository.findAllByUserId(userId, pageable).map(
                this::toResultResponseDTO
        );
    }

    //UPDATE



    @Override
    @Transactional
    public WordQuizProblemsResultResponseDTO gradeQuiz(Long quizId, Long userId, GradeWordQuizTestRequestDTO gradeWordQuizTestRequestDTO) {

        List<WordQuiz> allByIsCompletedIsFalse = wordQuizRepository.findAllByIsCompletedIsFalse();

        if (allByIsCompletedIsFalse.isEmpty())
            throw new WordQuizNotExistException("채점 가능한 퀴즈가 존재하지 않습니다");

        // 전처리
        // (마지막 wordQuiz == 수정해야 하는 wordQuiz)를 뽑아옴
        WordQuiz wordQuiz = allByIsCompletedIsFalse.stream()
                .max(Comparator.comparing(WordQuiz::getCreateTime))
                .orElseThrow(WordQuizNotExistException::new);

        if (!wordQuiz.getUserId().equals(userId))
            throw new NoAuthorityException("이 퀴즈의 주인이 아닙니다.");

        if (!wordQuiz.getId().equals(quizId))
            throw new NoAuthorityException("수정할 수 없거나 존재하지 않는 퀴즈입니다.");

        // completed된 wordQuiz 파일이 복수 개 존재할 경우
        if (1 < allByIsCompletedIsFalse.size()) {
            // (마지막 wordQuiz == 수정해야 하는 wordQuiz)를 제외한 나머지 wordQuiz들을 completed로 바꿈
            allByIsCompletedIsFalse.stream()
                    .filter(wq -> !wq.equals(wordQuiz))
                    .forEach(wq -> wq.setIsCompleted(true));
        }


        List<WordResponseDTO> words = wordQuiz.getWordIds()
                .stream().map(wordService::findById)
                .toList();

        // dto에서 정보를 뽑아냄
        Map<Long, List<String>> userAnswersMap = gradeWordQuizTestRequestDTO.getUserAnswers()
                .stream().collect(Collectors.toMap(
                        GradeWordQuizTestProblemRequestDTO::getWordId,
                        GradeWordQuizTestProblemRequestDTO::getUserKoreanAnswer
                        )
                );

        List<List<String>> orderedUserKoreanAnswers = words.stream()
                .filter(wordResponseDTO -> userAnswersMap.containsKey(wordResponseDTO.getId()))
                .map(word -> userAnswersMap.get(word.getId()))
                .toList();


        if (orderedUserKoreanAnswers.size() != words.size())
            throw new WordQuizAnswerNotMatchException();


        List<List<String>> answers = words.stream().map(WordResponseDTO::getKorean).toList();

        List<Boolean> result = IntStream.range(0, answers.size())
                .mapToObj(idx -> orderedUserKoreanAnswers.get(idx).equals(answers.get(idx)))
                .toList();

        if (answers.size() != result.size())
            throw new WordQuizAnswerLengthNotMatchException();

        Integer correctCount = (int) result.stream().filter(Boolean::booleanValue).count();
        Integer totalCount = result.size();

        // wordQuiz Entity 업데이트
        wordQuiz.setUserAnswers(Optional.of(orderedUserKoreanAnswers));
        wordQuiz.setResult(result);
        wordQuiz.setIsCompleted(true);
        wordQuiz.setCorrectCount(correctCount);
        wordQuiz.setTotalCount(totalCount);


        // 랭킹 정보 업데이트
        rankingService
                .addScore(
                        TotalQuizResultType.WORD,
                        wordQuiz.getUserId(),
                        correctCount,
                        0 // 이미 더해져 있다고 가정
                );

        return toResultResponseDTO(wordQuiz);
    }
}
