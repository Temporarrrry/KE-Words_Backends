package com.example.demo.Word.AddOn.BookmarkWord.Service;

import com.example.demo.Word.AddOn.BookmarkWord.DTO.BookmarkWordResponseDTO;
import com.example.demo.Word.AddOn.BookmarkWord.Exception.BookmarkWordExistException;
import com.example.demo.Word.AddOn.BookmarkWord.Exception.BookmarkWordNotExistException;
import com.example.demo.Word.AddOn.BookmarkWord.Repository.BookmarkWordRepository;
import com.example.demo.Word.AddOn.BookmarkWord.DTO.BookmarkWordRequestDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkWordServiceImpl implements BookmarkWordService {

    private final BookmarkWordRepository bookmarkWordRepository;

    @Override
    public void saveBookmarkWord(BookmarkWordRequestDTO bookmarkWordRequestDTO) throws BookmarkWordExistException {
        if (isExist(bookmarkWordRequestDTO)) throw new BookmarkWordExistException();
        bookmarkWordRepository.save(bookmarkWordRequestDTO.toEntity());
    }

    @Override
    public void deleteBookmarkWord(BookmarkWordRequestDTO bookmarkWordRequestDTO) {
        if (!isExist(bookmarkWordRequestDTO)) throw new BookmarkWordNotExistException();
        bookmarkWordRepository.deleteByUserIdAndWordId(bookmarkWordRequestDTO.getUserId(), bookmarkWordRequestDTO.getWordId());
    }

    @Override
    public Boolean isExist(BookmarkWordRequestDTO bookmarkWordRequestDTO) {
        return bookmarkWordRepository.existsByUserIdAndWordId(bookmarkWordRequestDTO.getUserId(), bookmarkWordRequestDTO.getWordId());
    }

    @Override
    public Page<BookmarkWordResponseDTO> findAllByUserId(Long userId, Pageable pageable) {
        return bookmarkWordRepository.findAllByUserId(userId, pageable)
                .map(bookmarkWord ->
                        new BookmarkWordResponseDTO(
                                bookmarkWord.getWordId()
                        )
                );
    }
}
