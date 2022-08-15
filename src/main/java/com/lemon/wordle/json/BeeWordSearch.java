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
public class BeeWordSearch implements Serializable {
	private static final long serialVersionUID = 5533095559126233682L;

	private String keyLetter = "";
	private String extraLetters = "";
}
