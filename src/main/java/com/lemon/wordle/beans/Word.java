/**
 * 
 */
package com.lemon.wordle.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Mattia Accinelli
 *
 */
@Entity
@Table(name="WORD", schema = "public")
@Getter
@Setter
@ToString
public class Word implements Serializable {

	private static final long serialVersionUID = -6289507069624953099L;

	public Word() {
		super();
	}
	
	public Word(String word) {
		super();
		this.word = word;
		this.char1 = word.charAt(0);
		this.char2 = word.charAt(1);
		this.char3 = word.charAt(2);
		this.char4 = word.charAt(3);
		this.char5 = word.charAt(4);
	}

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	long id;
	
	@Column(name="word", unique = true, nullable = false)
	private String word;
	
	@Column(name="char1", nullable = false)
	private Character char1;
	
	@Column(name="char2", nullable = false)
	private Character char2;
	
	@Column(name="char3", nullable = false)
	private Character char3;
	
	@Column(name="char4", nullable = false)
	private Character char4;
	
	@Column(name="char5", nullable = false)
	private Character char5;
}
