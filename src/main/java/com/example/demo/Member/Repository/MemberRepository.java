package com.example.demo.Member.Repository;

import com.example.demo.Member.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    //CREATE
    @Override
    <S extends Member> S save(S entity);

    //DELETE


    void deleteByUserEmail(String userEmail);



    //READ
    Optional<Member> findByUserEmail(String userEmail);

    boolean existsByUserEmail(String userEmail);

    Optional<Member> findByUserEmailAndPassword(String userEmail, String password);

    //UPDATE
}
