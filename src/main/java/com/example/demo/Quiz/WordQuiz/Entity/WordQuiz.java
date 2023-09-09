package com.example.demo.Quiz.WordQuiz.Entity;

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
@NoArgsConstructor
@Entity
@Access(AccessType.FIELD) // 필드 접근을 지정해서 JPA가 getter, setter를 사용하지 않도록 함
public class WordQuiz extends BaseTimeEntity {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private LocalDate quizDate;

    @Column(nullable = false, length = 10000)
    private String wordIds;

    @Column(nullable = false, length = 10000)
    private String koreanChoices;

    @Column(length = 10000)
    private String userAnswers;

    @Column(nullable = false)
    private Integer correctCount;

    @Column(nullable = false)
    private Integer totalCount;

    @Column(nullable = false, length = 10000)
    private String result;

    @Column(nullable = false)
    private Boolean isCompleted;

    public void setWordIds(List<Long> wordIds) {
        this.wordIds = String.join("|", wordIds.stream().map(String::valueOf).toList());
    }

    public List<Long> getWordIds() {
        return Stream.of(wordIds.split("\\|")).map(Long::parseLong).toList();
    }

    public void setKoreanChoices(List<List<List<String>>> koreanChoices) {
        this.koreanChoices = String.join("/", koreanChoices.stream().map(
                list1 -> String.join("|", list1.stream().map(
                        list2 -> String.join("\\", list2)
                ).toList())
        ).toList());
    }

    public List<List<List<String>>> getKoreanChoices() {
        return Arrays.stream(koreanChoices.split("/"))
                .map(s -> Arrays.stream(s.split("\\|")).toList())
                .map(list -> list.stream().map(s -> Arrays.stream(s.split("\\\\")).toList()).toList()).toList();
    }

    public void setUserAnswers(Optional<List<List<String>>> userAnswers) {
        userAnswers.ifPresent(userAnswer -> {
            List<String> userAnswerList = userAnswer.stream()
                    .map(strings -> String.join("/", strings)).toList();

            this.userAnswers = String.join("|", userAnswerList);
        });
    }

    public Optional<List<List<String>>> getUserAnswers() {
        if (this.userAnswers == null) return Optional.empty();

        return Optional.of(Stream.of(userAnswers.split("\\|"))
                .map(s -> Arrays.stream(s.split("/")).toList()).toList());
    }


    public void setResult(List<Boolean> result) {
        this.result = String.join("|", result.stream().map(aBoolean -> (aBoolean) ? "1" : "0").toList());
    }

    public List<Boolean> getResult() {
        return Arrays.stream(this.result.split("\\|")).map(s -> s.equals("1")).toList();
    }


    @Builder
    public WordQuiz(Long userId, LocalDate quizDate,
                    List<Long> wordIds, List<List<List<String>>> koreanChoices,
                    Optional<List<List<String>>> userAnswers, List<Boolean> result) {
        this.userId = userId;
        this.quizDate = quizDate;
        setWordIds(wordIds);
        setKoreanChoices(koreanChoices);
        setUserAnswers(userAnswers);
        this.correctCount = Long.valueOf(result.stream().filter(Boolean::booleanValue).count()).intValue();
        this.totalCount = result.size();
        setResult(result);
        this.isCompleted = Boolean.FALSE;
    }
}