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
@Table(name="BEE_WORD", schema = "public")
@Getter
@Setter
@ToString
public class BeeWord implements Serializable {

	private static final long serialVersionUID = -6289507069624953099L;

	public BeeWord() {
		super();
	}
	
	public BeeWord(String word) {
		super();
		this.word = word;
	}

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	long id;
	
	@Column(name="word", unique = true, nullable = false)
	private String word;
}
