/**
 * 
 */
package com.lemon.wordle.test;

import java.io.IOException;

import org.junit.Test;

import com.lemon.wordle.service.DictionaryService;

/**
 * @author Mattia Accinelli
 *
 */
public class DictionaryServiceTest {
	
	private final DictionaryService service = new DictionaryService();
	
	@Test
	public void loadDictionary_shouldLoad() throws IOException {
		final String fileName = "src/main/resources/words_alpha.txt";
		service.loadDictionary(fileName);
	}

}
