package com.li.TParse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TabTest {

	private static BufferedReader in;

	public static void main(String[] args) {

		File input = new File("/Users/Li/File/ParsedTweets/2_2013.tsv");

		String line = null;
		String[] str = null;

		try {
			in = new BufferedReader(new FileReader(input));
			
			/*while ((line = in.readLine()) != null) {

				str = line.split("\t");
				
				if(str.length != 3) {
					System.out.println(str.length);
					System.out.println(str[0] + "\t"+ str[1] + "\t"+ str[2] + "\t"+ str[3]);
				}
					
			}*/
			while ((line = in.readLine()) != null) {

				str = line.split("\t");
				
				if(str.length == 3) {
					//System.out.println(str.length);
					System.out.println(str[0] + "\t"+ str[2]);
				}
					
			}
			System.out.println("Done");
			

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
