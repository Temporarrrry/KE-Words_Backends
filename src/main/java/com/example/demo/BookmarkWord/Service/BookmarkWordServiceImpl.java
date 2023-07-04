package com.example.demo.BookmarkWord.Service;

import com.example.demo.BookmarkWord.DTO.BookmarkWordRequestDTO;
import com.example.demo.BookmarkWord.DTO.BookmarkWordResponseDTO;
import com.example.demo.BookmarkWord.Exception.BookmarkWordExistException;
import com.example.demo.BookmarkWord.Exception.BookmarkWordNotExistException;
import com.example.demo.BookmarkWord.Repository.BookmarkWordRepository;
import com.example.demo.Word.Service.WordService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkWordServiceImpl implements BookmarkWordService {

    private final BookmarkWordRepository bookmarkWordRepository;

    private final WordService wordService;

    @Override
    public void saveBookmarkWord(BookmarkWordRequestDTO bookmarkWordRequestDTO) {
        if (isExist(bookmarkWordRequestDTO)) throw new BookmarkWordExistException();
        List<String> korean = wordService.findByEnglish(bookmarkWordRequestDTO.getEnglish()).getKorean();

        bookmarkWordRepository.save(bookmarkWordRequestDTO.toEntity(korean));
    }

    @Override
    public void deleteBookmarkWord(BookmarkWordRequestDTO bookmarkWordRequestDTO) {
        if (!isExist(bookmarkWordRequestDTO)) throw new BookmarkWordNotExistException();
        bookmarkWordRepository.deleteByUserIdAndEnglish(bookmarkWordRequestDTO.getUserId(), bookmarkWordRequestDTO.getEnglish());
    }

    @Override
    public boolean isExist(BookmarkWordRequestDTO bookmarkWordRequestDTO) {
        return bookmarkWordRepository.existsByUserIdAndEnglish(bookmarkWordRequestDTO.getUserId(), bookmarkWordRequestDTO.getEnglish());
    }

    @Override
    public Page<BookmarkWordResponseDTO> findAllByUserId(Long userId, Pageable pageable) {
        return bookmarkWordRepository.findAllByUserId(userId, pageable).map(BookmarkWordResponseDTO::new);
    }
}
