package com.example.demo.Quiz.SentenceQuiz.Entity;

import com.example.demo.Common.Entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Access(AccessType.FIELD) // 필드 접근을 지정해서 JPA가 getter, setter를 사용하지 않도록 함
public class SentenceQuiz extends BaseTimeEntity {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private LocalDate quizDate;

    @Column(nullable = false, length = 2047)
    private String sentenceIds;

    @Column(nullable = false, length = 2047)
    private String problemSentences;

    @Column(length = 2047)
    private String userAnswers;

    @Column(nullable = false)
    private int correctCount;

    @Column(nullable = false)
    private int totalCount;

    @Column(nullable = false, length = 2047)
    private String result;

    @Column(nullable = false)
    private Boolean isCompleted;

    public void setSentenceIds(List<Long> sentenceIds) {
        this.sentenceIds = String.join("|", sentenceIds.stream().map(String::valueOf).toList());
    }

    public List<Long> getSentenceIds() {
        return Stream.of(sentenceIds.split("\\|")).map(Long::parseLong).toList();
    }


    public void setProblemSentences(List<List<String>> problemSentences) {
        List<String> problemSentenceList = problemSentences.stream()
                .map(strings -> String.join(" ", strings))
                .toList();

        this.problemSentences = String.join("|", problemSentenceList);
    }

    public List<List<String>> getProblemSentences() {
        return Arrays.stream(this.problemSentences.split("\\|"))
                .map(s -> Arrays.stream(s.split(" ")).toList())
                .toList();
    }

    public void setUserAnswers(Optional<List<List<String>>> userAnswers) {
        userAnswers.ifPresent(userAnswer -> {
            List<String> userAnswerList = userAnswer.stream()
                    .map(strings -> String.join(" ", strings))
                    .toList();

            this.userAnswers = String.join("|", userAnswerList);
        });
    }

    public List<List<String>> getUserAnswers() {
        return Arrays.stream(this.userAnswers.split("\\|"))
                .map(s -> Arrays.stream(s.split(" ")).toList())
                .toList();
    }

    public void setResult(List<Boolean> result) {
        this.result = String.join("|", result.stream().map(aBoolean -> (aBoolean) ? "1" : "0").toList());
    }

    public List<Boolean> getResult() {
        return Arrays.stream(this.result.split("\\|")).map(s -> s.equals("1")).toList();
    }

    @Builder
    public SentenceQuiz(Long userId, LocalDate quizDate, List<Long> sentenceIds,
                        List<List<String>> problemSentences, Optional<List<List<String>>> userAnswers, List<Boolean> result) {

        this.userId = userId;
        this.quizDate = quizDate;
        setSentenceIds(sentenceIds);
        setProblemSentences(problemSentences);
        setUserAnswers(userAnswers);
        this.correctCount = Long.valueOf(result.stream().filter(Boolean::booleanValue).count()).intValue();
        this.totalCount = result.size();
        setResult(result);
        this.isCompleted = Boolean.FALSE;
    }
}