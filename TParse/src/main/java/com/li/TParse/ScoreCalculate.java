package com.li.TParse;

import java.text.DecimalFormat;

import twitter4j.internal.org.json.JSONArray;
import twitter4j.internal.org.json.JSONException;
import twitter4j.internal.org.json.JSONObject;

/**
 * calculate user score based on 6 fields:
 * hashtag#, retweet#, status#, follower#, friend#, list#
 * write score to new Json file and csv file
 * 
 * @author Li
 *
 */

public class ScoreCalculate {
	
	private static long startTime = System.currentTimeMillis();
	
	private String line;
	private double filesize;
	
	private int hashtagInterval;
	private float[] retweetInterval; // weight for each interval of retweet
	private float[] statusInterval; // weight for each interval of status
	private float[] followerInterval; // weight for each interval of follower
	private float[] friendInterval; // weight for each interval of friend
	private float[] listInterval; // weight for each interval of list
	
	private float[] Weight; // weight for each field: hashtag, follower ...
	
	private int[] count; // store numbers for for each field: hashtag, follower ...
	private int[] score; // store scores for for each field and total socre
	
	public ScoreCalculate() {

		line = null;
		
		hashtagInterval = 25;
		retweetInterval =  new float[] { 92.12f, 3.0f, 3.37f, 1.28f }; // rest 0.23% 
		statusInterval = new float[] { 1.39f, 4.28f, 15.54f, 45.31f, 32.52f }; //rest 0.96%
		followerInterval = new float[] {4.35f, 18.51f, 64.50f, 11.40f, 1.1f }; // rest 0.14%
		friendInterval = new float[] {3.24f, 15.31f, 71.85f, 9.26f}; // rest 0.34%
		listInterval = new float[] { 90.58f, 7.63f, 1.62f }; // rest 0.17%
		
		// assign wight to hashtag, retweet, status, follower, friend, list
		Weight = new float[] { 0.1f, 0.05f, 0.2f, 0.25f, 0.25f, 0.15f };
		
		count = new int[6];
		score = new int[7];
	}

	
	
	/** method to calculate score **/
	public int getScore(int[] num) {
		
		for(int i = 0; i < 6; i++) {
			count[i] = num[i];
		}
			
			score[0] = HashtagScore(count[0], hashtagInterval, Weight[0]);
			
			score[1] = RetweetScore(count[1], retweetInterval, Weight[1]);
			
			score[2] = StatusScore(count[2], statusInterval, Weight[2]);
			
			score[3] = FollowerScore(count[3], followerInterval, Weight[3]);
			
			score[4] = FriendScore(count[4], friendInterval, Weight[4]);
			
			score[5] = ListScore(count[5], listInterval, Weight[5]);
			
			score[6] =0;
			for(int i=0; i < 6; i++) {
				//System.out.println("Score: " + score[i]);
				score[6] = score[6] + score[i];
			}
			//System.out.println("Score: " + score[6]);
			return score[6];
	} 
	
	/** calculate hashtag score **/
	public int HashtagScore(int n, int hshInterval, float hshWeight) {
		
		int score = 0;
		
		score = n * hshInterval;
		
		if(score > 100)
			score = 100; // set limit to hashtag score: 100
		
		return Math.round(score * hshWeight); //round (the closest integer to given float)
	}
	
	/** calculate retweet score **/
	public int RetweetScore(int n, float[] rttInterval, float rttWeight) {
		
		float score = 0;
		
		if (n <= 10)
			// score range: 0 - 
			score = (n * rttInterval[0]) / 10;
		
		else if (n <= 100)
			// score range: 
			score = ((n * rttInterval[1]) / 100) + rttInterval[0];
		
		else if (n <= 1000)
			// score range: 
			score = ((n * rttInterval[2]) / 1000) + rttInterval[0] + rttInterval[1];
		
		else if (n <= 10000)
			// score range: 
			score = ((n * rttInterval[3]) / 10000) + rttInterval[0] + rttInterval[1] + rttInterval[2];
		
		else
			score = 100f;
		
		//return Math.round(score);
		return Math.round(score * rttWeight);
		
	}
	
	/** calculate status score **/
	public int StatusScore(int n, float[] sttInterval, float sttWeight) {
		
		float score = 0;
		
		if (n <= 10)
			// score range: 0 - 
			score = (n * sttInterval[0]) / 10;
		
		else if (n <= 100)
			// score range:  - 
			score = ((n * sttInterval[1]) / 100) + sttInterval[0];
		
		else if (n <= 1000)
			// score range: 
			score = ((n * sttInterval[2]) / 1000) + sttInterval[0] + sttInterval[1];
		
		else if (n <= 10000)
			// score range: 
			score = ((n * sttInterval[3]) / 10000) + sttInterval[0] + sttInterval[1] + sttInterval[2];
		
		else if (n <= 100000)
			// score range: 
			score = ((n * sttInterval[4]) / 100000) + sttInterval[0] + sttInterval[1] + sttInterval[2] + sttInterval[3];
		
		else
			score = 100f;
		
		//return Math.round(score);
		return Math.round(score * sttWeight);
		
	}
	
	/** calculate follower score **/
	public int FollowerScore(int n, float[] flwInterval, float flwWeight) {
		
		float score = 0;
		
		if (n <= 10)
			// score range: 0 - 4.11
			score = (n * flwInterval[0]) / 10;
		
		else if (n <= 100)
			// score range: 4.11 - 23.93
			score = ((n * flwInterval[1]) / 100) + flwInterval[0];
		
		else if (n <= 1000)
			// score range: 23.93 - 88.18
			score = ((n * flwInterval[2]) / 1000) + flwInterval[0] + flwInterval[1];
		
		else if (n <= 10000)
			// score range: 88.18 - 98.87
			score = ((n * flwInterval[3]) / 10000) + flwInterval[0] + flwInterval[1] + flwInterval[2];
		
		else if (n <= 100000)
			// score range: 98.87 - 99.89
			score = ((n * flwInterval[4]) / 100000) + flwInterval[0] + flwInterval[1] + flwInterval[2] + flwInterval[3];
		
		else
			score = 100f;
		
		//return Math.round(score);
		return Math.round(score * flwWeight);
		
	}
	
	/** calculate friend score **/
	public int FriendScore(int n, float[] frdInterval, float frdWeight) {
		
		float score = 0;
		
		if (n <= 10)
			// score range: 0 - 
			score = (n * frdInterval[0]) / 10;
		
		else if (n <= 100)
			// score range: 
			score = ((n * frdInterval[1]) / 100) + frdInterval[0];
		
		else if (n <= 1000)
			// score range: 
			score = ((n * frdInterval[2]) / 1000) + frdInterval[0] + frdInterval[1];
		
		else if (n <= 10000)
			// score range: 
			score = ((n * frdInterval[3]) / 10000) + frdInterval[0] + frdInterval[1] + frdInterval[2];
		
		else
			score = 100f;
		
		//return Math.round(score);
		return Math.round(score * frdWeight);
		
	}
	
	/** calculate list score **/
	public int ListScore(int n, float[] lstInterval, float lstWeight) {
		
		float score = 0;
		
		if (n <= 10)
			// score range: 0 - 
			score = (n * lstInterval[0]) / 10;
		
		else if (n <= 100)
			// score range: 
			score = ((n * lstInterval[1]) / 100) + lstInterval[0];
		
		else if (n <= 1000)
			// score range: 
			score = ((n * lstInterval[2]) / 1000) + lstInterval[0] + lstInterval[1];
		
		else
			score = 100f;
		
		//return Math.round(score);
		return Math.round(score * lstWeight);
		
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
	
	/** count hashtag#, if repeated hashtag# >=2, count it as 2 **/
	public int RepeatedHashtagCount(JSONArray arr) {
		// to-do
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
		
	/** return float format result - XX.XX **/
	public static float floatfmt(float n) {
		
		// format: XX.XX
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(2);
		
		return Float.parseFloat(df.format(n));
	}
}
