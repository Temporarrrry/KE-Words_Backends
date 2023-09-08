package com.example.demo.Quiz.SentenceQuiz.Service;

import com.example.demo.Common.Exception.NoAuthorityException;
import com.example.demo.Quiz.SentenceQuiz.DTO.DeleteSentenceQuizRequestDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.Request.GenerateSentenceQuizRequestDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.Request.Grade.GradeSentenceQuizTestProblemRequestDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.Request.Grade.GradeSentenceQuizTestRequestDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Common.CommonSentenceQuizProblem;
import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Common.CommonSentenceQuizProblemsResponseDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Practice.PracticeSentenceQuizProblemsResponseDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Test.TestSentenceQuizProblemsResponseDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.SentenceQuizRequestDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.SentenceQuizResultResponseDTO;
import com.example.demo.Quiz.SentenceQuiz.Entity.SentenceQuiz;
import com.example.demo.Quiz.SentenceQuiz.Exception.SentenceQuizAnswerLengthNotMatchException;
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
    public void deleteQuiz(Long userId, DeleteSentenceQuizRequestDTO deleteSentenceQuizRequestDTO) throws NoAuthorityException {
        if (!findById(deleteSentenceQuizRequestDTO.getSentenceQuizId()).getUserId().equals(userId))
            throw new NoAuthorityException();

        sentenceQuizRepository.deleteById(deleteSentenceQuizRequestDTO.getSentenceQuizId());
    }





    private CommonSentenceQuizProblemsResponseDTO generateFillingSentenceQuiz(GenerateSentenceQuizRequestDTO generateSentenceQuizRequestDTO) throws SentenceTooShortException {
        Integer count = generateSentenceQuizRequestDTO.getCount();
        Long userId = generateSentenceQuizRequestDTO.getUserId();
        Boolean isTest = generateSentenceQuizRequestDTO.getIsTest();

        Random random = new Random();

        List<CommonSentenceQuizProblem> problems = sentenceService.findByRandom(count)
                .stream().map(dto -> {
                    List<String> originalSentence = Arrays.asList(dto.getEnglish().split(" "));

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


                    return CommonSentenceQuizProblem.builder()
                            .sentenceId(dto.getId())
                            .originalEnglish((isTest) ? null : originalSentence)
                            .editedEnglish(blankedSentence)
                            .korean(dto.getKorean())
                            .build();
                })
                .toList();

        CommonSentenceQuizProblemsResponseDTO problemResponseDTO = new CommonSentenceQuizProblemsResponseDTO();
        problemResponseDTO.setCommonProblems(problems);


        if (isTest) {
            List<Long> sentenceIds = problemResponseDTO.getCommonProblems()
                    .stream().map(CommonSentenceQuizProblem::getSentenceId)
                    .toList();

            List<List<String>> editedEnglish = problemResponseDTO.getCommonProblems()
                    .stream().map(CommonSentenceQuizProblem::getEditedEnglish)
                    .toList();

            problemResponseDTO.setQuizId(saveBaseQuiz(new SentenceQuizRequestDTO(userId, sentenceIds, editedEnglish)));
        }

        return problemResponseDTO;
    }

    @Override
    @Transactional
    public TestSentenceQuizProblemsResponseDTO getFillingTest(GenerateSentenceQuizRequestDTO generateSentenceQuizRequestDTO) {
        return new TestSentenceQuizProblemsResponseDTO(generateFillingSentenceQuiz(generateSentenceQuizRequestDTO));
    }


    @Override
    @Transactional
    public PracticeSentenceQuizProblemsResponseDTO getFillingPractice(GenerateSentenceQuizRequestDTO generateSentenceQuizRequestDTO) {
        return new PracticeSentenceQuizProblemsResponseDTO(generateFillingSentenceQuiz(generateSentenceQuizRequestDTO));
    }


    private CommonSentenceQuizProblemsResponseDTO generateOrderingSentenceQuiz(GenerateSentenceQuizRequestDTO generateSentenceQuizRequestDTO) {
        Integer count = generateSentenceQuizRequestDTO.getCount();
        Long userId = generateSentenceQuizRequestDTO.getUserId();
        Boolean isTest = generateSentenceQuizRequestDTO.getIsTest();


        List<CommonSentenceQuizProblem> problems = sentenceService.findByRandom(count)
                .stream().map(dto -> {
                    List<String> originalSentence = Arrays.asList(dto.getEnglish().split(" "));

                    List<String> shuffledSentence = new ArrayList<>(originalSentence);
                    Collections.shuffle(shuffledSentence);


                    return CommonSentenceQuizProblem.builder()
                            .sentenceId(dto.getId())
                            .originalEnglish((isTest) ? null : originalSentence)
                            .editedEnglish(shuffledSentence)
                            .korean(dto.getKorean())
                            .build();
                })
                .toList();

        CommonSentenceQuizProblemsResponseDTO problemResponseDTO = new CommonSentenceQuizProblemsResponseDTO();
        problemResponseDTO.setCommonProblems(problems);

        if (isTest) {
            List<Long> sentenceIds = problemResponseDTO.getCommonProblems()
                    .stream().map(CommonSentenceQuizProblem::getSentenceId)
                    .toList();

            List<List<String>> editedEnglish = problemResponseDTO.getCommonProblems()
                    .stream().map(CommonSentenceQuizProblem::getEditedEnglish)
                    .toList();

            problemResponseDTO.setQuizId(saveBaseQuiz(new SentenceQuizRequestDTO(userId, sentenceIds, editedEnglish)));
        }

        return problemResponseDTO;
    }

    @Override
    @Transactional
    public TestSentenceQuizProblemsResponseDTO getOrderingTest(GenerateSentenceQuizRequestDTO generateSentenceQuizRequestDTO) {
        return new TestSentenceQuizProblemsResponseDTO(generateOrderingSentenceQuiz(generateSentenceQuizRequestDTO));
    }

    @Override
    @Transactional
    public PracticeSentenceQuizProblemsResponseDTO getOrderingPractice(GenerateSentenceQuizRequestDTO generateSentenceQuizRequestDTO) {
        return new PracticeSentenceQuizProblemsResponseDTO(generateOrderingSentenceQuiz(generateSentenceQuizRequestDTO));
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


    @Override
    @Transactional
    public SentenceQuizResultResponseDTO gradeQuiz(GradeSentenceQuizTestRequestDTO gradeSentenceQuizTestRequestDTO) {
        List<SentenceQuiz> allByIsCompletedIsFalse = sentenceQuizRepository.findAllByIsCompletedIsFalse();

        if (allByIsCompletedIsFalse.isEmpty())
            throw new SentenceQuizNotExistException("채점 가능한 퀴즈가 존재하지 않습니다");

        SentenceQuiz sentenceQuiz = allByIsCompletedIsFalse
                .stream().max(Comparator.comparing(SentenceQuiz::getCreateTime))
                .orElseThrow(SentenceQuizNotExistException::new);

        if (!sentenceQuiz.getId().equals(gradeSentenceQuizTestRequestDTO.getQuizId()))
            throw new NoAuthorityException("이제 수정할 수 없는 퀴즈입니다.");

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
                                GradeSentenceQuizTestProblemRequestDTO::getUserKoreanAnswer
                        )
                );

        List<List<String>> orderedUserAnswers = sentences
                .stream().map(sentence -> userAnswerMap.get(sentence.getId()))
                .toList();

        List<String> answers = sentences.stream().map(SentenceResponseDTO::getKorean).toList();

        if (orderedUserAnswers.size() != answers.size())
            throw new SentenceQuizAnswerLengthNotMatchException();

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

        List<List<String>> originalEnglish = sentences.stream()
                .map(SentenceResponseDTO::getEnglish)
                .map(s -> Arrays.stream(s.split(" ")).toList())
                .toList();

        return new SentenceQuizResultResponseDTO(sentenceQuiz, originalEnglish);
    }
}