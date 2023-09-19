package com.example.demo.Sentence.AddOn.BookmarkSentence.Service;

import com.example.demo.Sentence.AddOn.BookmarkSentence.DTO.BookmarkSentenceRequestDTO;
import com.example.demo.Sentence.AddOn.BookmarkSentence.DTO.BookmarkSentenceResponseDTO;
import com.example.demo.Sentence.AddOn.BookmarkSentence.Exception.BookmarkSentenceExistException;
import com.example.demo.Sentence.AddOn.BookmarkSentence.Exception.BookmarkSentenceNotExistException;
import com.example.demo.Sentence.AddOn.BookmarkSentence.Repository.BookmarkSentenceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarkSentenceServiceImpl implements BookmarkSentenceService {

    private final BookmarkSentenceRepository bookmarkSentenceRepository;

    @Override
    @Transactional
    public void saveBookmarkSentence(BookmarkSentenceRequestDTO bookmarkSentenceRequestDTO) throws BookmarkSentenceExistException {
        if (isExist(bookmarkSentenceRequestDTO)) throw new BookmarkSentenceExistException();
        bookmarkSentenceRepository.save(bookmarkSentenceRequestDTO.toEntity());
    }

    @Override
    @Transactional
    public void deleteBookmarkSentence(BookmarkSentenceRequestDTO bookmarkSentenceRequestDTO) {
        if (!isExist(bookmarkSentenceRequestDTO)) throw new BookmarkSentenceNotExistException();
        bookmarkSentenceRepository.deleteByUserIdAndSentenceId(bookmarkSentenceRequestDTO.getUserId(), bookmarkSentenceRequestDTO.getSentenceId());
    }

    @Override
    public Boolean isExist(BookmarkSentenceRequestDTO bookmarkSentenceRequestDTO) {
        return bookmarkSentenceRepository.existsByUserIdAndSentenceId(bookmarkSentenceRequestDTO.getUserId(), bookmarkSentenceRequestDTO.getSentenceId());
    }

    @Override
    public Page<BookmarkSentenceResponseDTO> findAllByUserId(Long userId, Pageable pageable) {
        return bookmarkSentenceRepository.findAllByUserId(userId, pageable)
                .map(bookmarkSentence ->
                        new BookmarkSentenceResponseDTO(
                                bookmarkSentence.getSentenceId()
                        )
                );
    }
}
