package com.twitterstream;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusAdapter;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.json.DataObjectFactory;

public final class FilterStream extends StatusAdapter {
	
	public static int k = 0;   // value passed to progressbar()
	public static double maxfilesize = 750.0;
	public static long startTime = System.currentTimeMillis();
	public static String filepath = "/Users/Li/File/NewRawTrainingData/";
	
	public static void main(String[] args) throws TwitterException {
		
		// Configuration for authentication - twitter oAuth
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true);
		cb.setJSONStoreEnabled(true);
		cb.setOAuthConsumerKey("9DJZbeMNFVKcnGOs2Y5pw");
		cb.setOAuthConsumerSecret("0foOWaPQgTj6oZZYGY0smqvgJzBrCoMB7dNjnz67s");
		cb.setOAuthAccessToken("550516920-ykMiape8Gb7BKBH28RFnG1Kju9jblAIyKidbm53A");
		cb.setOAuthAccessTokenSecret("RKx9Q6vNiQEbJW0zyolntmR9e2CqksQbS83AYneQ");

		TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
		
		//get current date to name the file
		Date d = new Date();
		SimpleDateFormat df = new SimpleDateFormat("MM_dd_yyyy");
		String date = df.format(d);
		
		//format change: 01 -> 1
		String[] str = date.split("_");
		int intdate = Integer.parseInt(str[1]);
		date = str[0] + "_" + intdate + "_" + str[2];
		
		// 
		filepath = filepath + "lung" + ".json";
		final File file = new File(filepath);
		
		StatusListener listener = new StatusListener() {

			public void onStatus(Status status) {
				
				if (file.length() / 1000 < maxfilesize) {   //10485760
					double fsize = file.length() / 2500;
					if(fsize >= k) {
						try {
							progressbar(k);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						k++;
					}
					try {
						String rawJSON = DataObjectFactory.getRawJSON(status);

						storeJSON(rawJSON, filepath);
					} 
					catch (IOException ioe) {
						ioe.printStackTrace();
						System.out.println("Failed to store tweets: " + ioe.getMessage());
					}
				} 
				else {
					
					try {
						progressbar(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					System.out.println("Done. File size: 1GB ");
					
					System.out.println("Total time: " + timeelapsed(startTime));
					System.exit(0);
				}
			}

			private void storeJSON(String rawJSON, String fileName) throws IOException {
				FileOutputStream fos = null;
				OutputStreamWriter osw = null;
				BufferedWriter bw = null;

				try {
					fos = new FileOutputStream(fileName, true);
					// set true to continually write to an existing file
					osw = new OutputStreamWriter(fos, "UTF-8");
					bw = new BufferedWriter(osw);
					bw.write(rawJSON);
					bw.newLine();
					bw.flush();
					
				} finally {
					if (bw != null) {
						try {
							bw.close();
						} catch (IOException ignore) {
						}
					}
					if (osw != null) {
						try {
							osw.close();
						} catch (IOException ignore) {
						}
					}
					if (fos != null) {
						try {
							fos.close();
						} catch (IOException ignore) {
						}
					}
				}
			}
			
			private void progressbar (int percent) throws InterruptedException {
				
				StringBuilder bar = new StringBuilder("[");

			    for(int i = 0; i < 50; i++){
			        if( i < (percent/2)){
			            bar.append("=");
			        }else if( i == (percent/2)){
			            bar.append(">");
			        }else{
			            bar.append(" ");
			        }
			    }
			    
			    bar.append("]   " + percent + "%   " + "Time elapsed: " + timeelapsed(startTime));
			    System.out.print("\r" + bar.toString());
			}
			
			private String timeelapsed (long startTime) {
				long endTime   = System.currentTimeMillis();
				long totalTime = endTime - startTime;
				
				String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(totalTime),
			            TimeUnit.MILLISECONDS.toMinutes(totalTime) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(totalTime)),
			            TimeUnit.MILLISECONDS.toSeconds(totalTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(totalTime)));
				return hms;
			}

			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
				//System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
			}

			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
				System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
			}

			public void onScrubGeo(long userId, long upToStatusId) {
				System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
			}

			public void onException(Exception ex) {
				ex.printStackTrace();
			}

			public void onStallWarning(StallWarning arg0) {
				// TODO Auto-generated method stub
				
			}
		};

		FilterQuery fq = new FilterQuery();
	    
        String keywords[] = {"lung disease", "lung"};
		//String keywords[] = {"#stroke", "brain stroke", "#brainstroke", "brainstroke"};
		fq.track(keywords);

        twitterStream.addListener(listener);
        twitterStream.filter(fq);  
	}
}