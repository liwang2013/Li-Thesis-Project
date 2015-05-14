package com.li;

public class Main {

	private static long startTime = System.currentTimeMillis();
	
	private static int startmonth = 9;
	private static int totalmonth = 6;
	
	private static String filename;
	
	public static void main(String[] args) {
		
		ScoreCalculate sc = new ScoreCalculate();
		
		for(int i = startmonth; i < startmonth + totalmonth; i++) {
			
			if(i > 12)
				filename = (i - 12) + ".json";
			else
				filename = i + ".json";
			
			sc.ReadFile(filename);
		}
		
		sc.timeelapsed(startTime);
	}

}
