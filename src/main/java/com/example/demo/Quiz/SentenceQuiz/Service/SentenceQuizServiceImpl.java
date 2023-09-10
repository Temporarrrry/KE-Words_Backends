package com.example.demo.Quiz.SentenceQuiz.Service;

import com.example.demo.Common.Exception.NoAuthorityException;
import com.example.demo.Quiz.SentenceQuiz.DTO.Request.GenerateSentenceQuizRequestDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.Request.Grade.GradeSentenceQuizTestProblemRequestDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.Request.Grade.GradeSentenceQuizTestRequestDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Filling.Common.SentenceQuizFillingCommonProblem;
import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Filling.Common.SentenceQuizFillingCommonProblemsResponseDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Filling.Practice.SentenceQuizFillingPracticeProblemsResponseDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Filling.Test.SentenceQuizFillingTestProblemsResponseDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Meaning.Common.SentenceQuizMeaningCommonProblem;
import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Meaning.Common.SentenceQuizMeaningCommonProblemsResponseDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Meaning.Practice.SentenceQuizMeaningPracticeProblemsResponseDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Meaning.Test.SentenceQuizMeaningTestProblemsResponseDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Ordering.Common.SentenceQuizOrderingCommonProblem;
import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Ordering.Common.SentenceQuizOrderingCommonProblemsResponseDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Ordering.Practice.SentenceQuizOrderingPracticeProblemsResponseDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Ordering.Test.SentenceQuizOrderingTestProblemsResponseDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Result.SentenceQuizProblemsResultResponseDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.SentenceQuizRequestDTO;
import com.example.demo.Quiz.SentenceQuiz.Entity.SentenceQuiz;
import com.example.demo.Quiz.SentenceQuiz.Entity.SentenceQuizType;
import com.example.demo.Quiz.SentenceQuiz.Exception.SentenceQuizAnswerNotMatchException;
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
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
@Transactional
@RequiredArgsConstructor
public class SentenceQuizServiceImpl implements SentenceQuizService {

    private final SentenceQuizRepository sentenceQuizRepository;

    private final SentenceService sentenceService;

    private final RankingService rankingService;


    private Long saveBaseQuiz(SentenceQuizRequestDTO sentenceQuizRequestDTO) {

        rankingService.addScore(
                TotalQuizResultType.SENTENCE,
                sentenceQuizRequestDTO.getUserId(),
                0, // updateQuiz에서 계산
                sentenceQuizRequestDTO.getProblems().size()
        );

        List<Boolean> FalseResult = Stream.generate(() -> false)
                .limit(sentenceQuizRequestDTO.getProblems().size())
                .toList();

        SentenceQuiz sentenceQuiz = sentenceQuizRequestDTO.toEntity(Optional.empty(), FalseResult);
        return sentenceQuizRepository.save(sentenceQuiz).getId();
    }

    @Override
    public void deleteQuiz(Long userId, Long quizId) throws NoAuthorityException {
        if (!findById(quizId).getUserId().equals(userId))
            throw new NoAuthorityException();

        sentenceQuizRepository.deleteById(quizId);
    }


    private SentenceQuizMeaningCommonProblemsResponseDTO generateMeaningSentenceQuiz(GenerateSentenceQuizRequestDTO generateSentenceQuizRequestDTO) {
        Integer count = generateSentenceQuizRequestDTO.getCount();
        Long userId = generateSentenceQuizRequestDTO.getUserId();
        Boolean isTest = generateSentenceQuizRequestDTO.getIsTest();

        List<SentenceResponseDTO> sentences = sentenceService.findByRandom(count * 4);

        List<String> koreans = sentences.stream().map(SentenceResponseDTO::getKorean).toList();

        SentenceQuizMeaningCommonProblemsResponseDTO problems = new SentenceQuizMeaningCommonProblemsResponseDTO();
        for (int i = 0; i < sentences.size(); i += 4) {
            String originalKorean = koreans.get(i);
            String english = String.join(" ", sentences.get(i).getEnglish());

            List<String> koreanChoices = new ArrayList<>(koreans.subList(i, i + 4));
            Collections.shuffle(koreanChoices);

            problems.getMeaningProblems().add(
                    SentenceQuizMeaningCommonProblem.builder()
                            .sentenceId(sentences.get(i).getId())
                            .english(english)
                            .koreanChoices(koreanChoices)
                            .originalKorean((isTest) ? null : originalKorean)
                            .build()
            );
        }

        if (isTest) {
            List<Long> sentenceIds = problems.getMeaningProblems()
                    .stream().map(SentenceQuizMeaningCommonProblem::getSentenceId)
                    .toList();

            List<List<String>> koreanChoices = problems.getMeaningProblems()
                    .stream().map(SentenceQuizMeaningCommonProblem::getKoreanChoices)
                    .toList();

            problems.setQuizId(saveBaseQuiz(new SentenceQuizRequestDTO(userId, SentenceQuizType.MEANING, sentenceIds, koreanChoices)));
        }

        return problems;
    }

    @Override
    @Transactional
    public SentenceQuizMeaningTestProblemsResponseDTO getMeaningTest(GenerateSentenceQuizRequestDTO generateSentenceQuizRequestDTO) {
        return new SentenceQuizMeaningTestProblemsResponseDTO(generateMeaningSentenceQuiz(generateSentenceQuizRequestDTO));
    }

    @Override
    @Transactional
    public SentenceQuizMeaningPracticeProblemsResponseDTO getMeaningPractice(GenerateSentenceQuizRequestDTO generateSentenceQuizRequestDTO) {
        return new SentenceQuizMeaningPracticeProblemsResponseDTO(generateMeaningSentenceQuiz(generateSentenceQuizRequestDTO));
    }




    private SentenceQuizFillingCommonProblemsResponseDTO generateFillingSentenceQuiz(GenerateSentenceQuizRequestDTO generateSentenceQuizRequestDTO) throws SentenceTooShortException {
        Integer count = generateSentenceQuizRequestDTO.getCount();
        Long userId = generateSentenceQuizRequestDTO.getUserId();
        Boolean isTest = generateSentenceQuizRequestDTO.getIsTest();

        Random random = new Random();

        List<SentenceQuizFillingCommonProblem> problems = sentenceService.findByRandom(count)
                .stream().map(dto -> {
                    List<String> originalSentence = dto.getEnglish();

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


                    return SentenceQuizFillingCommonProblem.builder()
                            .sentenceId(dto.getId())
                            .originalEnglish((isTest) ? null : originalSentence)
                            .editedEnglish(blankedSentence)
                            .korean(dto.getKorean())
                            .build();
                })
                .toList();

        SentenceQuizFillingCommonProblemsResponseDTO problemResponseDTO = new SentenceQuizFillingCommonProblemsResponseDTO();
        problemResponseDTO.setProblems(problems);


        if (isTest) {
            List<Long> sentenceIds = problemResponseDTO.getProblems()
                    .stream().map(SentenceQuizFillingCommonProblem::getSentenceId)
                    .toList();

            List<List<String>> editedEnglish = problemResponseDTO.getProblems()
                    .stream().map(SentenceQuizFillingCommonProblem::getEditedEnglish)
                    .toList();

            problemResponseDTO.setQuizId(saveBaseQuiz(new SentenceQuizRequestDTO(userId, SentenceQuizType.FILLING, sentenceIds, editedEnglish)));
        }

        return problemResponseDTO;
    }

    @Override
    @Transactional
    public SentenceQuizFillingTestProblemsResponseDTO getFillingTest(GenerateSentenceQuizRequestDTO generateSentenceQuizRequestDTO) {
        return new SentenceQuizFillingTestProblemsResponseDTO(generateFillingSentenceQuiz(generateSentenceQuizRequestDTO));
    }


    @Override
    @Transactional
    public SentenceQuizFillingPracticeProblemsResponseDTO getFillingPractice(GenerateSentenceQuizRequestDTO generateSentenceQuizRequestDTO) {
        return new SentenceQuizFillingPracticeProblemsResponseDTO(generateFillingSentenceQuiz(generateSentenceQuizRequestDTO));
    }


    private SentenceQuizOrderingCommonProblemsResponseDTO generateOrderingSentenceQuiz(GenerateSentenceQuizRequestDTO generateSentenceQuizRequestDTO) {
        Integer count = generateSentenceQuizRequestDTO.getCount();
        Long userId = generateSentenceQuizRequestDTO.getUserId();
        Boolean isTest = generateSentenceQuizRequestDTO.getIsTest();


        List<SentenceQuizOrderingCommonProblem> problems = sentenceService.findByRandom(count)
                .stream().map(dto -> {
                    List<String> originalSentence = dto.getEnglish();

                    List<String> shuffledSentence = new ArrayList<>(originalSentence);
                    Collections.shuffle(shuffledSentence);


                    return SentenceQuizOrderingCommonProblem.builder()
                            .sentenceId(dto.getId())
                            .originalEnglish((isTest) ? null : originalSentence)
                            .editedEnglish(shuffledSentence)
                            .korean(dto.getKorean())
                            .build();
                })
                .toList();

        SentenceQuizOrderingCommonProblemsResponseDTO problemResponseDTO = new SentenceQuizOrderingCommonProblemsResponseDTO();
        problemResponseDTO.setProblems(problems);

        if (isTest) {
            List<Long> sentenceIds = problemResponseDTO.getProblems()
                    .stream().map(SentenceQuizOrderingCommonProblem::getSentenceId)
                    .toList();

            List<List<String>> editedEnglish = problemResponseDTO.getProblems()
                    .stream().map(SentenceQuizOrderingCommonProblem::getEditedEnglish)
                    .toList();

            problemResponseDTO.setQuizId(saveBaseQuiz(new SentenceQuizRequestDTO(userId, SentenceQuizType.ORDERING, sentenceIds, editedEnglish)));
        }

        return problemResponseDTO;
    }

    @Override
    @Transactional
    public SentenceQuizOrderingTestProblemsResponseDTO getOrderingTest(GenerateSentenceQuizRequestDTO generateSentenceQuizRequestDTO) {
        return new SentenceQuizOrderingTestProblemsResponseDTO(generateOrderingSentenceQuiz(generateSentenceQuizRequestDTO));
    }

    @Override
    @Transactional
    public SentenceQuizOrderingPracticeProblemsResponseDTO getOrderingPractice(GenerateSentenceQuizRequestDTO generateSentenceQuizRequestDTO) {
        return new SentenceQuizOrderingPracticeProblemsResponseDTO(generateOrderingSentenceQuiz(generateSentenceQuizRequestDTO));
    }


    @Override
    public SentenceQuizProblemsResultResponseDTO findById(Long id) {
        SentenceQuiz sentenceQuiz = sentenceQuizRepository.findById(id)
                .orElseThrow(SentenceQuizNotExistException::new);

        List<SentenceResponseDTO> sentenceResponseDTOS = sentenceQuiz.getSentenceIds().stream()
                .map(sentenceService::findById)
                .toList();


        return new SentenceQuizProblemsResultResponseDTO(sentenceQuiz, sentenceResponseDTOS);
    }

    @Override
    public List<Long> findAllByUserId(Long userId, Pageable pageable) {
        return sentenceQuizRepository
                .findAllByUserId(userId, pageable) // 퀴즈 여러 개
                .map(SentenceQuiz::getId)
                .getContent();
    }


    @Override
    @Transactional
    public SentenceQuizProblemsResultResponseDTO gradeQuiz(Long quizId, Long userId, GradeSentenceQuizTestRequestDTO gradeSentenceQuizTestRequestDTO) {
        List<SentenceQuiz> allByIsCompletedIsFalse = sentenceQuizRepository.findAllByIsCompletedIsFalse();

        if (allByIsCompletedIsFalse.isEmpty())
            throw new SentenceQuizNotExistException("채점 가능한 퀴즈가 존재하지 않습니다");

        SentenceQuiz sentenceQuiz = allByIsCompletedIsFalse
                .stream().max(Comparator.comparing(SentenceQuiz::getCreateTime))
                .orElseThrow(SentenceQuizNotExistException::new);

        if (!sentenceQuiz.getUserId().equals(userId))
            throw new NoAuthorityException("이 퀴즈의 주인이 아닙니다.");


        if (!sentenceQuiz.getId().equals(quizId))
            throw new NoAuthorityException("수정할 수 없거나 존재하지 않는 퀴즈입니다.");


        if (1 < allByIsCompletedIsFalse.size()) {
            allByIsCompletedIsFalse.stream()
                    .filter(oneSentenceQuiz -> !oneSentenceQuiz.equals(sentenceQuiz))
                    .forEach(oneSentenceQuiz -> oneSentenceQuiz.setIsCompleted(true));
        }

        List<SentenceResponseDTO> sentences = sentenceQuiz.getSentenceIds()
                .stream().map(sentenceService::findById)
                .toList();


        Map<Long, List<String>> userAnswerMap = gradeSentenceQuizTestRequestDTO.getUserAnswers()
                .stream().collect(Collectors.toMap(
                                GradeSentenceQuizTestProblemRequestDTO::getSentenceId,
                                GradeSentenceQuizTestProblemRequestDTO::getUserAnswer
                        )
                );

        List<List<String>> orderedUserAnswers = sentences.stream()
                .filter(sentenceResponseDTO ->  userAnswerMap.containsKey(sentenceResponseDTO.getId()))
                .map(sentence -> userAnswerMap.get(sentence.getId()))
                .toList();

        if (orderedUserAnswers.size() != sentences.size())
            throw new SentenceQuizAnswerNotMatchException();

        List<List<String>> answers;
        if (sentenceQuiz.getType().equals(SentenceQuizType.MEANING))
            answers = sentences.stream().map(SentenceResponseDTO::getEnglish).toList();
        else
            answers = sentences.stream().map(dto -> List.of(dto.getKorean())).toList();

        List<Boolean> result = IntStream.range(0, answers.size())
                .mapToObj(idx -> orderedUserAnswers.get(idx).equals(answers.get(idx)))
                .toList();

        Integer correctCount = (int) result.stream().filter(Boolean::booleanValue).count();
        Integer totalCount = result.size();


        sentenceQuiz.setUserAnswers(Optional.of(orderedUserAnswers));
        sentenceQuiz.setResult(result);
        sentenceQuiz.setIsCompleted(true);
        sentenceQuiz.setCorrectCount(correctCount);
        sentenceQuiz.setTotalCount(totalCount);


        rankingService.addScore(
                TotalQuizResultType.SENTENCE,
                sentenceQuiz.getUserId(),
                correctCount,
                0
        );

        return new SentenceQuizProblemsResultResponseDTO(sentenceQuiz, sentences);
    }
}