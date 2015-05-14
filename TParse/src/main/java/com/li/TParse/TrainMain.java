package com.li.TParse;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class TrainMain {

	private static int twtnum = 0;

	public static void main(String[] args) {

		JsonParse jp = new JsonParse();
		
		long startTime = System.currentTimeMillis();
		String keyword = "brain";
		
		File outputfile = new File("/Users/Li/File/BackupTrainData/" + keyword + ".tsv");
		
		if (outputfile.exists()) {
			System.out.println(outputfile + ".tsv already exists!");
			System.out.println("Filesize: " + outputfile.length() / 1048576 + " MB\n");
		} else {
			
			jp.parse(keyword, keyword, twtnum);
			twtnum = jp.tweetnum;
				
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
