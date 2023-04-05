package com.example.demo.Member.Entity;

import com.example.demo.Member.Common.Entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Member extends BaseTimeEntity {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String userEmail;

    @Column(nullable = false)
    String password;

    @Builder
    public Member(String userEmail, String password) {
        this.userEmail = userEmail;
        this.password = password;
    }
}