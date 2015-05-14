package com.chimpler.example.bayes2;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class MapReduceClassifier {

	public static void main(String[] args) throws Exception {
		if (args.length < 5) {
			System.out.println("Arguments: [model] [dictionnary] [document frequency] [tweet file] [output directory]");
			return;
		}
		String modelPath = args[0];
		String dictionaryPath = args[1];
		String documentFrequencyPath = args[2];
		String tweetsPath = args[3];
		String outputPath = args[4];
	
		Configuration conf = new Configuration();
	
		conf.setStrings(Classifier.MODEL_PATH_CONF, modelPath);
		conf.setStrings(Classifier.DICTIONARY_PATH_CONF, dictionaryPath);
		conf.setStrings(Classifier.DOCUMENT_FREQUENCY_PATH_CONF, documentFrequencyPath);
	
		// do not create a new jvm for each task
		conf.setLong("mapred.job.reuse.jvm.num.tasks", -1);
	
		Job job = new Job(conf, "MapReduceClassifier");
		job.setJarByClass(ClassifierMap.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		job.setMapperClass(ClassifierMap.class);
	
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
	
		FileInputFormat.addInputPath(job, new Path(tweetsPath));
		FileOutputFormat.setOutputPath(job, new Path(outputPath));
	
		job.waitForCompletion(true);
	}
}
