package com.jerrydev.java8.parallelstream;

import java.util.concurrent.RecursiveTask;

/**
 * example from Java 8 in Action
 *
 */

public class ForkJoinSumCalculator extends RecursiveTask<Long> {

	private static final long serialVersionUID = -3625638892740594645L;
	
	private final long[] numbers;
	private final int start;
	private final int end;
	
	public static final long THRESHOLD = 10_000;
	
	public ForkJoinSumCalculator(long[] numbers) {
		this(numbers, 0, numbers.length);
	}
	
	public ForkJoinSumCalculator(long[] numbers, int start, int end) {
		this.numbers = numbers;
		this.start = start;
		this.end = end;
	}
	
	@Override
	protected Long compute() {
		int length = end - start;
		if(length <= THRESHOLD) {
			return computeSequentially();
		}
		
		ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(numbers, start, start + length/2);
		leftTask.fork(); // asynchronously execute leftTask in another thread from ForkJoinPool
		
		ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(numbers, start + length/2, end);
		Long rightResult = rightTask.compute();  // execute rightTask in current thread
		
		Long leftResult = leftTask.join();  // wait until leftTask complete
		
		return leftResult + rightResult;
	}
	
	private long computeSequentially() {
		long sum = 0;
		for(int i = start; i < end; i++) {
			sum += numbers[i];
		}
		return sum;
	}

}
