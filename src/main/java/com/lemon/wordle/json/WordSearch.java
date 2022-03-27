/**
 * 
 */
package com.lemon.wordle.json;

import java.io.Serializable;

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
public class WordSearch implements Serializable {
	private static final long serialVersionUID = 5533095559126233682L;

	private Character char1;
	private Character char2;
	private Character char3;
	private Character char4;
	private Character char5;
	private String wanted = "";
	private String discarded = "";
}
