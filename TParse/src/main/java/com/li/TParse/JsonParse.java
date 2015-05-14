package com.li.TParse;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import twitter4j.internal.org.json.JSONArray;
import twitter4j.internal.org.json.JSONException;
import twitter4j.internal.org.json.JSONObject;

public class JsonParse {
	private static BufferedWriter bw;
	private static BufferedReader in;
	public static int tweetnum;
	
	public void parse(String filename, String outputfile, int num) {
		
		tweetnum = num;
		double filesize = 0;
		String line = null;
		
		String text = null;
		String tweetid = null;
		String createtime = null;
		String lang = null;
		
		int hashtagcount = 0;
		int retweetcount = 0;
		int statuscount = 0;
		int followercount = 0;
		int friendcount = 0;
		String name = null;
		int listedcount = 0;
		int[] count = new int[6];
		int score;
		
		File output = new File("/Users/Li/File/ParsedTweets/" + outputfile + ".tsv"); //refer the file path
		File input = new File("/Users/Li/File/RawTweets/" + filename + ".json");
		//File input = new File("/home/li/RawTweets/01_28_2014.json");
		
		ScoreCalculate sc = new ScoreCalculate();
		
		if(!input.exists())
			System.out.println(filename + ".json does not exist!\n");
		else
		{
			System.out.println("Please wait while processing file " + filename + ".json");
			try {
				output.createNewFile();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
			
			try {
				in = new BufferedReader(new FileReader(input));
				while((line = in.readLine()) != null){
					
					String[] hashtag = null;
					
					try {
						JSONObject jsontwt = new JSONObject(line);
						JSONObject jsonentity = jsontwt.getJSONObject("entities");
						JSONArray jahash = jsonentity.getJSONArray("hashtags");
						JSONObject jsonuser = jsontwt.getJSONObject("user");
						
						lang = jsonuser.getString("lang");
						if(lang.equals("en")) {
							
							//check if tweet contains any non-ASCII characters
							text = jsontwt.getString("text");
							String subtext = null;
							subtext = text.replaceAll("[^\\x00-\\x7F]", "");
							if(!text.equals(subtext))
							{
								text = subtext;
							}
							
							//check if the first character is white space
							else if(text.indexOf(" ") != 0)
							{
								
								tweetid = jsontwt.getString("id");
								
								//check if text contains \n, \r, " or ,
								if(text.indexOf("\n") != -1)
									text = text.replaceAll("\n", " ");
								if(text.indexOf("\r") != -1)
									text = text.replaceAll("\r", " ");
								if(text.indexOf("\"") != -1)
									text = text.replaceAll("\"", "");
								if(text.indexOf(",") != -1)
									text = text.replaceAll(",", "");
								if(text.indexOf("\t") != -1)
									text = text.replaceAll("\t", " ");
								if(text.indexOf(";") != -1)
									text = text.replaceAll(";", "");
								text = text + " "; 
								
								//create time & date
								createtime = jsontwt.getString("created_at");
								String[] ctime = createtime.split(" ");
								int ddate = Integer.parseInt(ctime[2]);
								
								if(ctime[1].equals("Sep"))
									createtime = ctime[3] + " 9 " + Integer.toString(ddate) + " 2012";
								else if(ctime[1].equals("Oct"))
									createtime = ctime[3] + " 10 " + Integer.toString(ddate) + " 2012";
								else if(ctime[1].equals("Nov"))
									createtime = ctime[3] + " 11 " + Integer.toString(ddate) + " 2012";
								else if(ctime[1].equals("Dec"))
									createtime = ctime[3] + " 12 " + Integer.toString(ddate) + " 2012";
								else if(ctime[1].equals("Jan"))
									createtime = ctime[3] + " 1 " + Integer.toString(ddate) + " 2013";
								else if(ctime[1].equals("Feb"))
									createtime = ctime[3] + " 2 " + Integer.toString(ddate) + " 2013";
								
								if(jsontwt.isNull("retweeted_status") == false)
								{
									JSONObject jsonretweet = jsontwt.getJSONObject("retweeted_status");
									retweetcount = jsonretweet.getInt("retweet_count");
								}
								else
									retweetcount = jsontwt.getInt("retweet_count");
								
								statuscount = jsonuser.getInt("statuses_count");
								followercount = jsonuser.getInt("followers_count");
								friendcount = jsonuser.getInt("friends_count");
								name = jsonuser.getString("screen_name");
								if(!jsonuser.isNull("listed_count"))
									listedcount = jsonuser.getInt("listed_count");
								else
									listedcount = 0;
								
								count[0] = HashtagCheck(jahash);
								count[1] = retweetcount;
								count[2] = statuscount;
								count[3] = followercount;
								count[4] = friendcount;
								count[5] = listedcount;
								
								score = sc.getScore(count);
								
								if(statuscount >= 0) {
									
									tweetnum++;
									
									//write to file
									FileWriter fw = new FileWriter(output, true);
									bw = new BufferedWriter(fw);
									bw.write(tweetid + "\t" + text + "\t" + score);
								
									bw.newLine();
									bw.flush();
								}
							}
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			bw.close();
			
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					in.close();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			System.out.println("Date: " + filename);
			System.out.println("Tweet num: " + tweetnum);
			filesize = output.length()/1048576;  //unit: mb (1024*1024=1048576)
			System.out.println("Filesize: " + filesize + " MB\n");
		}
	}
	
	/** get non-repeated hashtag#, treat all the repeated hashtags as one **/
	public int HashtagCheck(JSONArray arr) {
		
		int num = 0;
		int leng = arr.length();
		JSONObject jo;
		String hash = null;
		String nexthash = null;
		
		if(leng != 0) {
			
			num++;
			
			for(int i = 0; i < leng - 1; i++) {
				
				try {
					jo = arr.getJSONObject(i);
					hash = jo.getString("text");
				
					for(int j = i + 1; j < leng; j++) {
						
						jo = arr.getJSONObject(j);
						nexthash = jo.getString("text");
						
						if(!hash.equals(nexthash)) // check if two hashtags are the same
							num++;
					
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}
		}
		
		return num;
	}
}