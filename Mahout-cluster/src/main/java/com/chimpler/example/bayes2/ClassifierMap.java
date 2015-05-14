package com.chimpler.example.bayes2;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class ClassifierMap extends Mapper<LongWritable, Text, Text, IntWritable> {
	private final static Text outputKey = new Text();
	private final static IntWritable outputValue = new IntWritable();
	private static Classifier classifier;

	@Override
	protected void setup(Context context) throws IOException {
		initClassifier(context);
	}

	private static void initClassifier(Context context) throws IOException {
		if (classifier == null) {
			synchronized (ClassifierMap.class) {
				if (classifier == null) {
					classifier = new Classifier(context.getConfiguration());
				}
			}
		}
	}

	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		String line = value.toString();
		String[] tokens = line.split("\t", 2);
		if (tokens.length < 2) {
			return;
		}
		String tweetId = tokens[0];
		String tweet = tokens[1];

		int bestCategoryId = classifier.classify(tweet);
		outputValue.set(bestCategoryId);

		outputKey.set(tweetId);
		context.write(outputKey, outputValue);
	}
}
