package com.example.demo.Word.AddOn.LastWord.Entity;

import com.example.demo.Common.Entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class LastWord extends BaseTimeEntity {
    @Id
    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long wordId;
}