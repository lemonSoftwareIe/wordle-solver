/**
 * 
 */
package com.lemon.wordle.service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lemon.wordle.beans.BeeWord;
import com.lemon.wordle.beans.Word;
import com.lemon.wordle.dao.BeeWordDao;
import com.lemon.wordle.dao.WordDao;
import com.lemon.wordle.json.BeeWordSearch;
import com.lemon.wordle.json.CharCount;
import com.lemon.wordle.json.WordSearch;

/**
 * @author Mattia Accinelli
 *
 */
@Service
public class DictionaryService {
	
	@Autowired
	private WordDao wordDao;
	
	@Autowired
	private BeeWordDao beeWordDao;
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void loadDictionary(final String fileName) throws IOException {
		final File file = new File(fileName);
		final List<String> lines = FileUtils.readLines(file, Charset.forName("UTF-8"));
		final List<Word> words = lines.parallelStream()
				.filter(w -> w.length() == 5)
				.map(w -> new Word(w))
				.collect(Collectors.toList());
		wordDao.saveBatch(words);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void loadBeeDictionary(final String fileName) throws IOException {
		final File file = new File(fileName);
		final List<String> lines = FileUtils.readLines(file, Charset.forName("UTF-8"));
		final List<BeeWord> words = lines.parallelStream()
				.filter(w -> w.length() >= 4)
				.map(w -> new BeeWord(w))
				.collect(Collectors.toList());
		beeWordDao.saveBatch(words);
	}
	
	@Transactional(readOnly = true)
	public List<Word> findWords(final WordSearch wordSearch) {
		final List<Word> words = wordDao.findWords(wordSearch);
		return words.parallelStream()
				.filter(w -> notContainChars(w.getWord(), wordSearch.getDiscarded().toCharArray()))
				.toList();
	}
	
	@Transactional(readOnly = true)
	public List<BeeWord> findBeeWords(final BeeWordSearch wordSearch) {
		return beeWordDao.findWords(wordSearch);
	}
	
	public boolean notContainChars(final String word, final char[] chars) {
		for(Character c : chars) {
			if (word.contains(String.valueOf(c))) {
				return false;
			}
		}
		return true;
	}
	
	public List<CharCount> findCharCount() {
		final List<String> words = wordDao.findAllWord();
		final Map<Character, Integer> count = new HashMap<Character, Integer>();
		words.parallelStream().forEach(w -> {
			for(Character c : w.toCharArray()) {
				if (!count.containsKey(c)) {
					count.put(c, 0);
				}
				count.put(c, count.get(c) + 1);
			}
		});
		return count.keySet().stream()
		.map(c -> new CharCount(c, count.get(c)))
		.sorted()
		.toList();
	}
}
