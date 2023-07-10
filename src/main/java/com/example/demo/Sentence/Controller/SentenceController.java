package com.example.demo.Sentence.Controller;

import com.example.demo.Sentence.DTO.SentenceResponseDTO;
import com.example.demo.Sentence.Exception.SentenceNotExistException;
import com.example.demo.Sentence.Service.SentenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(originPatterns = "http://**", maxAge = 3600) //TODO originPatterns 수정
@RestController
@RequestMapping("/api/sentence")
@RequiredArgsConstructor
public class SentenceController {
    private final SentenceService sentenceService;

    //@Secured("ROLE_ADMIN")
    /*@RequestMapping(method = RequestMethod.POST, value = "/save")
    public ResponseEntity<Void> saveSentence(@RequestBody SentenceRequestDTO sentenceRequestDTO) {
        sentenceService.saveSentence(sentenceRequestDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }*/

    //@Secured("ROLE_ADMIN")
    /*@RequestMapping(method = RequestMethod.POST, value = "/delete")
    public ResponseEntity<Void> deleteSentence(@RequestBody SentenceRequestDTO sentenceRequestDTO) {
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
}
