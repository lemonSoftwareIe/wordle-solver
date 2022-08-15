/**
 * 
 */
package com.lemon.wordle.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.lemon.wordle.beans.BeeWord;
import com.lemon.wordle.json.BeeWordSearch;

/**
 * @author Mattia Accinelli
 *
 */
@Repository
public class BeeWordDao {
	
	private static final int BATCH_SIZE = 100;

	@PersistenceContext
    private EntityManager entityManger;

	public void saveBatch(final List<BeeWord> words) {
		int i = 0;
		for (BeeWord word: words) {
	        if (i > 0 && i % BATCH_SIZE == 0) {
	        	flush();
	        }
	        entityManger.persist(word);
	        i++;
	    }
		flush();
	}

	public List<BeeWord> findWords(final BeeWordSearch wordSearch) {
		final StringBuilder builder = new StringBuilder();
		builder.append("SELECT * FROM bee_word w WHERE w.word LIKE '%" + wordSearch.getKeyLetter() + "%' ");
		builder.append("AND w.word ~ '^[" + wordSearch.getExtraLetters() + wordSearch.getKeyLetter() + "]+$' " );
		
		final Query query = entityManger.createNativeQuery(builder.toString(), BeeWord.class);
		return query.getResultList();
	}
	
	public List<String> findAllWord() {
		final String query = "SELECT w.word from bee_word w";
		return entityManger.createNativeQuery(query).getResultList();
	}

	public void flush() {
		entityManger.flush();
		entityManger.clear();
	}
}
