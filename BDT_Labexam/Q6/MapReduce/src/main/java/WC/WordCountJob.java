package WC;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class WordCountJob extends Configured implements Tool{

	// This is the main class that gets called ( provided as an argument from the command line)
	public static void main(String[] args) throws Exception {
		
		// Tool is a hadoop utility class used to run jobs
		// Takes care of the input arguments etc
		Tool job = new WordCountJob();
		int exitCode = ToolRunner.run(job, args);
		System.exit(exitCode);
	}
				
 
	public int run(String[] args) throws Exception {
		
		// This is the input argument coming from hadoop jar command
		String inPath = args[0];
		String outPath = args[1];

		// This is the job instance that will initiate the job
		Job job = Job.getInstance();
		// We need to register the job class with the job in order to let job find the class
		job.setJarByClass(WordCountJob.class);
		// This is how we set the job name, this will come up on the YARN WebUI console
		job.setJobName("eDBDA_WordCountJob");
		
		// We need to set the mapper class and the reducer class as below for this particular job
		job.setMapperClass(WordCountMapper.class);
		//job.setCombinerClass(WordCountReducer.class);
		job.setReducerClass(WordCountReducer.class);
		
		// For the mapper and reducer, we need to specify the type of key and value we will be using
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);

		// We also need to provide the output format of the job, this is the format Reducer will use to write 
		// onto HDFS
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		
		// Set the input location and the output location as received from the 
		FileInputFormat.addInputPath(job, new Path(inPath));
		FileOutputFormat.setOutputPath(job, new Path(outPath));
		
		int returnValue = job.waitForCompletion(true) ? 0:1;
		System.out.println("job.isSuccessful " + job.isSuccessful());
		return returnValue;
	}

}
