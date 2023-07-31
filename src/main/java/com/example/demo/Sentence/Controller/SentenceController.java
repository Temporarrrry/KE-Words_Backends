package com.example.demo.Sentence.Controller;

import com.example.demo.Sentence.DTO.SentenceResponseDTO;
import com.example.demo.Sentence.Exception.SentenceNotExistException;
import com.example.demo.Sentence.Service.SentenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sentence")
@RequiredArgsConstructor
public class SentenceController {
    private final SentenceService sentenceService;

    //@Secured("ROLE_ADMIN")
    /*@RequestMapping(method = RequestMethod.POST, value = "/save")
    public ResponseEntity<Void> saveSentence(@RequestBody @Valid SentenceRequestDTO sentenceRequestDTO) {
        sentenceService.saveSentence(sentenceRequestDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }*/

    //@Secured("ROLE_ADMIN")
    /*@RequestMapping(method = RequestMethod.POST, value = "/delete")
    public ResponseEntity<Void> deleteSentence(@RequestBody @Valid SentenceRequestDTO sentenceRequestDTO) {
        sentenceService.deleteSentence((sentenceRequestDTO));
        return new ResponseEntity<>(HttpStatus.OK);
    }*/

    @RequestMapping(method = RequestMethod.GET, value = "/findByEnglish")
    public ResponseEntity<SentenceResponseDTO> findByEnglish(@RequestParam(value = "english") String english) throws SentenceNotExistException {
        return new ResponseEntity<>(sentenceService.findByEnglish(english), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getSentencesByRandom")
    public ResponseEntity<List<SentenceResponseDTO>> getSentencesByRandom(@RequestParam(value = "count") int count) {
        if (200 < count) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(sentenceService.findByRandom(count), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findAll")
    public ResponseEntity<List<SentenceResponseDTO>> findAll(@PageableDefault(size = 5) Pageable pageable) {
        return new ResponseEntity<>(sentenceService.findAll(pageable).getContent(), HttpStatus.OK);
    }
}
