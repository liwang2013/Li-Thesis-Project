package com.li.TParse;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TabModifier {

	private static BufferedReader in;
	private static BufferedWriter bw;
	
	public static void main(String[] args) {
		
		File input = new File("/Users/Li/File/ParsedTweets/12_2012.tsv");
		File output = new File("/Users/Li/File/ParsedTweets/new12_2012.tsv");

		String line = null;
		String[] str = null;
		int count = 0;

		try {
			in = new BufferedReader(new FileReader(input));
			
			while ((line = in.readLine()) != null) {
				
				++count;
				str = line.split("\t");
				
				//write to file
				FileWriter fw = new FileWriter(output, true);
				bw = new BufferedWriter(fw);
				bw.write(str[1] + "\t"+ str[2] + "\t" + str[3]);
			
				bw.newLine();
				bw.flush();
					
			}
			bw.close();
			System.out.println("Done");
			System.out.println("Num: " + count);

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
