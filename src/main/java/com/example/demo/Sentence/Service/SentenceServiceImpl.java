package com.example.demo.Sentence.Service;

import com.example.demo.Member.Service.MemberService;
import com.example.demo.Sentence.AddOn.BookmarkSentence.DTO.BookmarkSentenceRequestDTO;
import com.example.demo.Sentence.AddOn.BookmarkSentence.Service.BookmarkSentenceService;
import com.example.demo.Sentence.AddOn.LastSentence.DTO.LastSentenceRequestDTO;
import com.example.demo.Sentence.AddOn.LastSentence.Service.LastSentenceService;
import com.example.demo.Sentence.DTO.SentenceResponseDTO;
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
public class SentenceServiceImpl implements SentenceService {

    private final SentenceRepository sentenceRepository;

    private final BookmarkSentenceService bookmarkSentenceService;

    private final MemberService memberService;

    private final LastSentenceService lastSentenceService;

    @Override
    public SentenceResponseDTO findById(Long id) {
        Sentence sentence = sentenceRepository.findById(id).orElseThrow(SentenceNotExistException::new);
        Long userId = memberService.findIdByAuthentication();

        return new SentenceResponseDTO(
                sentence,
                bookmarkSentenceService.isExist(
                        new BookmarkSentenceRequestDTO(userId, sentence.getId())
                )
        );
    }


    @Override
    public List<SentenceResponseDTO> findByRandom(int count) {
        Long userId = memberService.findIdByAuthentication();

        return sentenceRepository.findByRandom(count).stream().map(
                sentence -> new SentenceResponseDTO(
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
    public Page<SentenceResponseDTO> findAll(Pageable pageable) {
        Long userId = memberService.findIdByAuthentication();

        Page<SentenceResponseDTO> sentences = sentenceRepository.findAll(pageable).map(
                sentence -> new SentenceResponseDTO(
                        sentence,
                        bookmarkSentenceService.isExist(
                                new BookmarkSentenceRequestDTO(
                                        userId,
                                        sentence.getId()
                                )
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
