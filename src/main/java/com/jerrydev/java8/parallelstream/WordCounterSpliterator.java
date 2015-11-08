package com.jerrydev.java8.parallelstream;

import java.util.Spliterator;
import java.util.function.Consumer;

public class WordCounterSpliterator implements Spliterator<Character> {

	private final String string;
	private int currentChar = 0;
	
	public WordCounterSpliterator(String string) {
		this.string = string;
	}
	
	@Override
	public boolean tryAdvance(Consumer<? super Character> action) {
		
		System.out.println("tryAdvance [" + string + "] :  " + currentChar);
		
		action.accept(string.charAt(currentChar));
		currentChar++;
		return currentChar < string.length();
	}

	@Override
	public Spliterator<Character> trySplit() {
		
		System.out.println("trySplit [" + string + "] :  " + currentChar);
		
		int currentSize = string.length() - currentChar;
		
		// remaining string no need to split further
		if(currentSize < 10) {
			return null;
		}
		
		for(int splitPos = currentSize/2 + currentChar; splitPos < string.length(); splitPos++) {
			
			// only split at a whilespace
			if(Character.isWhitespace(string.charAt(splitPos))) {
				Spliterator<Character> spliterator = new WordCounterSpliterator(string.substring(currentChar, splitPos));
				currentChar = splitPos; // current spliter works from portion after split
				
				return spliterator;  // split out currentChar to first whitespace after currentChar + currentSize/2
			}
		}
		
		return null;
	}

	@Override
	public long estimateSize() {
		
		System.out.println("estimateSize [" + string + "] :  " + currentChar);
		
		return string.length() - currentChar;
	}

	@Override
	public int characteristics() {
		
		System.out.println("characteristics [" + string + "] :  " + currentChar);
		
		return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
	}

}
