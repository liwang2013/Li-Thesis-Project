package com.li;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

/**
 calculate score from DB
 **/

public class TScore {

	private static long startTime = System.currentTimeMillis();
	
	// configuration info for DB connection
	private static final String dbName = "tweets";
	// path to Mysql DB
	private static final String dburl = "jdbc:mysql://localhost:3306/" + dbName;

	// username and password for Mysql login
	private static final String username = "root";
	private static final String password = "root";	
	
	// 
	//private static final int totalScore = 100;
	private static final float Wfollower = 0.25f;
	//private static final float fScore = totalScore * Wfollower;
	//private static final int followerScore = (int)fScore;
	
	// assign weight for each interval
	private static float[] weight = new float[5];
	
	private static int k = 0;
	
	public static void main(String[] args) {
		
		weight[0] = 4.11f;   // interval 0-10
		weight[1] = 19.82f;  // interval 10-100
		weight[2] = 64.25f;  // interval 100-1k
		weight[3] = 10.69f;  // interval 1k-10k
		weight[4] = 1.02f;   // interval 10k-100k
		
		try {
			sqlExecute();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		System.out.println(timeelapsed(startTime));
	}
	
	/** Calculate the score of follower - Total **/
	private static float scoreCalculate(int n, float[] weight, float wtype) {
		
		float score = 0;
		
		if (n <= 10)
			// score range: 0 - 4.11
			score = (n * weight[0]) / 10;
		
		else if (n <= 100)
			// score range: 4.11 - 23.93
			score = ((n * weight[1]) / 100) + weight[0];
		
		else if (n <= 1000)
			// score range: 23.93 - 88.18
			score = ((n * weight[2]) / 1000) + weight[0] + weight[1];
		
		else if (n <= 10000)
			// score range: 88.18 - 98.87
			score = ((n * weight[3]) / 10000) + weight[0] + weight[1] + weight[2];
		
		else if (n <= 100000)
			// score range: 98.87 - 99.89
			score = ((n * weight[4]) / 100000) + weight[0] + weight[1] + weight[2] + weight[3];
		
		else
			score = 100f;
		return floatfmt(score);
		//return floatfmt(score * wtype);
	}
	
	private static void sqlExecute() throws SQLException {
		 
		Connection dbConnection = null;
		Statement statement = null;
		
		float score;
		
		String selectSQL = "SELECT * from tweetsep";
 
		try {
			dbConnection = getConnection();
			
			PreparedStatement preparedStatement = null;
			statement = dbConnection.createStatement();
 
			System.out.println(selectSQL);
 
			// execute select SQL stetement
			ResultSet rs = statement.executeQuery(selectSQL);
 
			while (rs.next()) {
 
				int ID = rs.getInt("ID");
				int followercount = rs.getInt("followercount");
				
				score = scoreCalculate(followercount, weight, Wfollower);
				
				String updateTableSQL = "UPDATE tweetsep SET followerScore = ? WHERE ID = ?";
				preparedStatement = dbConnection.prepareStatement(updateTableSQL);
				
				preparedStatement.setFloat(1, score);
				preparedStatement.setInt(2, ID);
				
				preparedStatement.executeUpdate();
				
				if (ID < 2151729) {   
					double perc = ID / 21517.29;
					if(perc >= k) {
						try {
							progressbar(k);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						k++;
					}
				} 
				else {
					
					try {
						progressbar(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					System.out.println("Done.");
					
				}
			}
			
		} catch (SQLException e) {
 
			System.out.println(e.getMessage());
 
		} finally {
 
			if (statement != null) {
				statement.close();
			}
 
			if (dbConnection != null) {
				dbConnection.close();
			}
 
		}
 
	}	
	
	/** Connection to DB **/
	private static Connection getConnection() {

		Connection connect = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your MySQL JDBC Driver?");
			System.out.println(e.getMessage());
		}

		try {
			connect = DriverManager.getConnection(dburl, username, password);

		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
		}

		if (connect != null) {
			System.out.println("Successful connection to DB: "+ dbName);
		} else {
			System.out.println("Failed to make connection!");
		}
		
		return connect;
	}
	
	/** return float format result - XX.XX **/
	public static float floatfmt(float n) {
		
		// format: XX.XX
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(2);
		
		return Float.parseFloat(df.format(n));
	}
	
	/** return percentage format result from division **/
	public static String percent(int n) {
		
		// format: XX.XX%
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(2);
		
		return df.format(n * 100.00 / 2151729) + "%";
	}
	
	/** show the progress with bar **/
	private static void progressbar (int percent) throws InterruptedException {
		
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
	
	/** calculate the time for query **/
	private static String timeelapsed (long startTime) {
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		
		String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(totalTime),
	            TimeUnit.MILLISECONDS.toMinutes(totalTime) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(totalTime)),
	            TimeUnit.MILLISECONDS.toSeconds(totalTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(totalTime)));
		return "Total time: " + hms;
	}

}