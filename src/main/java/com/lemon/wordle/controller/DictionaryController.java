/**
 * 
 */
package com.lemon.wordle.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lemon.wordle.beans.Word;
import com.lemon.wordle.json.CharCount;
import com.lemon.wordle.json.WordSearch;
import com.lemon.wordle.service.DictionaryService;

/**
 * @author Mattia Accinelli
 *
 */
@RestController
@RequestMapping("/dictionary")
public class DictionaryController {
	
	@Autowired
	private DictionaryService dictionaryService;
	
	@RequestMapping(path = "/load", method = RequestMethod.POST)
	public ResponseEntity<String> loadDictionary() throws IOException {
		final String fileName = "src/main/resources/words_alpha.txt";
		dictionaryService.loadDictionary(fileName);
		return new ResponseEntity<String>("Success", HttpStatus.OK);
	}
	
	@RequestMapping(path = "/search", method = RequestMethod.POST)
	public ResponseEntity<List<String>> searchWord(@RequestBody final WordSearch wordSearch) throws IOException {
		final List<String> words = dictionaryService.findWords(wordSearch).parallelStream()
				.map(Word::getWord).toList();
		return new ResponseEntity<List<String>>(words, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/count", method = RequestMethod.GET)
	public ResponseEntity<List<CharCount>> charCount() throws IOException {
		return new ResponseEntity<List<CharCount>>(dictionaryService.findCharCount(), HttpStatus.OK);
	}
}
