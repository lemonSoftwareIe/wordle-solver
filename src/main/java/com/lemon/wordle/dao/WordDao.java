/**
 * 
 */
package com.lemon.wordle.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.lemon.wordle.beans.Word;
import com.lemon.wordle.json.WordSearch;

/**
 * @author Mattia Accinelli
 *
 */
@Repository
public class WordDao {
	
	private static final int BATCH_SIZE = 100;

	@PersistenceContext
    private EntityManager entityManger;

	public void saveBatch(final List<Word> words) {
		int i = 0;
		for (Word word: words) {
	        if (i > 0 && i % BATCH_SIZE == 0) {
	        	flush();
	        }
	        entityManger.persist(word);
	        i++;
	    }
		flush();
	}

	public List<Word> findWords(final WordSearch wordSearch) {
		final StringBuilder builder = new StringBuilder();
		builder.append("SELECT * FROM word w WHERE 1=1 ");
		final List<String[]> params = new ArrayList<String[]>();
		if (wordSearch.getChar1() != null) {
			builder.append("AND w.char1 = :char1 " );
			params.add(new String[] {"char1", String.valueOf(wordSearch.getChar1())});
		}
		if (wordSearch.getChar2() != null) {
			builder.append("AND w.char2 = :char2 " );
			params.add(new String[] {"char2", String.valueOf(wordSearch.getChar2())});
		}
		if (wordSearch.getChar3() != null) {
			builder.append("AND w.char3 = :char3 " );
			params.add(new String[] {"char3", String.valueOf(wordSearch.getChar3())});
		}
		if (wordSearch.getChar4() != null) {
			builder.append("AND w.char4 = :char4 " );
			params.add(new String[] {"char4", String.valueOf(wordSearch.getChar4())});
		}
		if (wordSearch.getChar5() != null) {
			builder.append("AND w.char5 = :char5 " );
			params.add(new String[] {"char5", String.valueOf(wordSearch.getChar5())});
		}
		if (!wordSearch.getWanted().isEmpty()) {
			for(Character x: wordSearch.getWanted().toCharArray()) {
				builder.append("AND w.word LIKE '%" + x + "%' ");
			}
		}
		
		final Query query = entityManger.createNativeQuery(builder.toString(), Word.class);
		for(String[] param: params) {
			query.setParameter(param[0], param[1]);
		}
		
		return query.getResultList();
	}
	
	public List<String> findAllWord() {
		final String query = "SELECT w.word from word w";
		return entityManger.createNativeQuery(query).getResultList();
	}

	public void flush() {
		entityManger.flush();
		entityManger.clear();
	}
}
