/**
 * 
 */
package com.lemon.wordle.json;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Mattia Accinelli
 *
 */
@Getter
@Setter
@ToString
public class CharCount implements Comparable<CharCount> {
	private Character character;
	private int count;
	
	public CharCount(Character character, int count) {
		super();
		this.character = character;
		this.count = count;
	}

	@Override
	public int compareTo(CharCount o) {
		return this.count - o.count;
	}
	
}
