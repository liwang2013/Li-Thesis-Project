package com.li.TParse;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NonclinicParse {

	private static BufferedWriter bw;
	private static BufferedReader in;

	private static String input = "/Users/Li/File/ParsedTweets/1_14_2014.tsv";
	private static String output = "/Users/Li/Dropbox/Thesis/TrainingData/nonclinicaldata.tsv";
	private static String clinicallist = "/Users/Li/Dropbox/Thesis/TrainingData/clinicallist.tsv";

	public static void main(String[] args) {

		new NonclinicParse().nonclinicalparse(input, output, clinicallist);
	}

	public void nonclinicalparse(String inp, String outp, String clist) {

		File inputfile = new File(inp);
		File outputfile = new File(outp);
		File nonclinicalfile = new File(clist);

		String termlist = null;
		String line = null;
		String[] split = null;
		List<String> lines = new ArrayList<String>();
		String[] terms = null;

		int count = 0;

		try {

			in = new BufferedReader(new FileReader(nonclinicalfile));
			while ((line = in.readLine()) != null) {

				lines.add(line);
			}
			terms = lines.toArray(new String[lines.size()]);

			in = new BufferedReader(new FileReader(inputfile));

			while ((line = in.readLine()) != null) {

				split = line.split("\t");

				if ((nonclickcheck(split[1], terms)) == false) {
					
					if (split[1].length() > 80) {
						
						if (++count <= 400) {

							FileWriter fw = new FileWriter(outputfile, true);
							bw = new BufferedWriter(fw);
							bw.write("nonclinical" + "\t" + split[1]);

							bw.newLine();
							bw.flush();
						}
					}
				}
			}
			System.out.println("Done. count records parsed.");
			in.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean nonclickcheck(String text, String[] terms) {

		boolean include = false;

		for (String s : terms) {

			String[] split = s.split("\t");

			for (String term : split) {
				//System.out.println(term);
				if (text.toLowerCase().contains(term.toLowerCase()))
					include = true;
				else if (text.toLowerCase().contains("#" + term.toLowerCase()))
					include = true;
			}
		}
		return include;
	}
}
