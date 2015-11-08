package com.jerrydev.java8.parallelstream;

import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * example from Java 8 in Action
 *
 */

public class SplitratorDemo {

	final static String SENTENCE = " ab cde fghi j klm no pq rs tu vw xyz 123 45 678 90  ";
	
	public static void main(String[] args) {
		Spliterator<Character> spliterator = new WordCounterSpliterator(SENTENCE);
		Stream<Character> stream = StreamSupport.stream(spliterator, true);
		System.out.println("Found " + countWord(stream) + " words");
	}

	private static int countWord(Stream<Character> stream) {
		WordCounter wordCounter = stream.reduce(new WordCounter(0, true), WordCounter::accumulate, WordCounter::combine);
		return wordCounter.getCounter();
	}
}
