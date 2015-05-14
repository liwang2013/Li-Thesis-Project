package com.li.TParse;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class Main {

	private static int twtnum = 0;

	public static void main(String[] args) {

		JsonParse jp = new JsonParse();
		// jp.parse("2_1_2014", 0);

		long startTime = System.currentTimeMillis();
		String intput = null;
		String output = null;
		intput = "1_14"; // format: MM_DD_year
		output = "1_14_2014"; // format: MM_year
		File outputfile = new File("/Users/Li/File/ParsedTweets/" + output + ".tsv");

		if (outputfile.exists()) {
			System.out.println(outputfile + ".tsv already exists!");
			System.out.println("Filesize: " + output.length() / 1048576
					+ " MB\n");
		} else {
			String[] str = intput.split("_");
			int date = Integer.parseInt(str[1]);
			for (int i = 0; i < 1; i++) {
				if (date + i > 130) {
					str[0] = "10";
					intput = str[0] + "_" + Integer.toString(date + i - 30)
							+ "_" + "2014";
				} else
					intput = str[0] + "_" + Integer.toString(date + i) + "_"+ "2014";
					//intput = str[0] + "_" + Integer.toString(date + i);
				jp.parse(intput, output, twtnum);
				twtnum = jp.tweetnum;
				
			}

			long endTime = System.currentTimeMillis();
			long totalTime = endTime - startTime;

			String hms = String.format(
					"%02d:%02d:%02d",
					TimeUnit.MILLISECONDS.toHours(totalTime),
					TimeUnit.MILLISECONDS.toMinutes(totalTime)
							- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
									.toHours(totalTime)),
					TimeUnit.MILLISECONDS.toSeconds(totalTime)
							- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
									.toMinutes(totalTime)));

			System.out.println("Done. Total time is: " + hms);
			System.out.println("Total tweet num: " + twtnum);
		}
	}

}