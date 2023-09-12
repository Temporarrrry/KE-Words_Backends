package com.example.demo.Sentence.Service.WithBookmark;

import com.example.demo.Member.Service.MemberService;
import com.example.demo.Sentence.AddOn.BookmarkSentence.DTO.BookmarkSentenceRequestDTO;
import com.example.demo.Sentence.AddOn.BookmarkSentence.Service.BookmarkSentenceService;
import com.example.demo.Sentence.AddOn.LastSentence.DTO.LastSentenceRequestDTO;
import com.example.demo.Sentence.AddOn.LastSentence.Service.LastSentenceService;
import com.example.demo.Sentence.DTO.SentenceWithBookmarkResponseDTO;
import com.example.demo.Sentence.Entity.Sentence;
import com.example.demo.Sentence.Exception.SentenceNotExistException;
import com.example.demo.Sentence.Repository.SentenceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SentenceWithBookmarkServiceImpl implements SentenceWithBookmarkService {

    private final SentenceRepository sentenceRepository;

    private final BookmarkSentenceService bookmarkSentenceService;

    private final MemberService memberService;

    private final LastSentenceService lastSentenceService;

    @Override
    public SentenceWithBookmarkResponseDTO findById(Long id) {
        Sentence sentence = sentenceRepository.findById(id).orElseThrow(SentenceNotExistException::new);
        Long userId = memberService.findIdByAuthentication();

        return new SentenceWithBookmarkResponseDTO(
                sentence,
                bookmarkSentenceService.isExist(
                        new BookmarkSentenceRequestDTO(userId, sentence.getId())
                )
        );
    }


    @Override
    public List<SentenceWithBookmarkResponseDTO> findByRandom(int count) {
        Long userId = memberService.findIdByAuthentication();

        return sentenceRepository.findByRandom(count).stream().map(
                sentence -> new SentenceWithBookmarkResponseDTO(
                        sentence,
                        bookmarkSentenceService.isExist(
                                new BookmarkSentenceRequestDTO(
                                        userId,
                                        sentence.getId()
                                )
                        )
                )
        ).toList();
    }

    @Override
    public Page<SentenceWithBookmarkResponseDTO> findAll(Pageable pageable) {
        Long userId = memberService.findIdByAuthentication();

        Page<SentenceWithBookmarkResponseDTO> sentences = sentenceRepository
                .findAll(pageable)
                .map(
                        sentence -> new SentenceWithBookmarkResponseDTO(
                                sentence,
                                bookmarkSentenceService.isExist(
                                        new BookmarkSentenceRequestDTO(userId, sentence.getId())
                                )
                        )
                );

        if (sentences.hasContent()) {
            Long firstId = sentences.getContent().get(0).getId();
            lastSentenceService.saveOrUpdate(new LastSentenceRequestDTO(userId, firstId));
        }

        return sentences;
    }
}
