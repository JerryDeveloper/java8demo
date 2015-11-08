package com.jerrydev.java8.parallelstream;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

public class ForkJoinFrameworkDemo {
	
	// fork threads from this pool for task execution
	// number of threads available is determined by Runtime.availableProcessors
	public static ForkJoinPool pool = new ForkJoinPool();
	
	public static void main(String[] args) {
		long[] numbers = LongStream.rangeClosed(1, 1_000_000).toArray();
		ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);
		
		System.out.println(pool.invoke(task));
	}
}