package train_multiple;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class TrainFinder extends Configured implements Tool {

	@Override
	public int run(String[] args) throws Exception {

		String inPath = args[0];
		String outPath = args[1];
		/*
		* Job 1 To find the slowest train in each direction
		 */
		Configuration conf = getConf();
//		FileSystem fs = FileSystem.get(conf);
//		Job job = new Job(conf,"Job1");
		Job job = Job.getInstance(conf,"TrainFinder");

		FileInputFormat.addInputPath(job, new Path(inPath));
		FileOutputFormat.setOutputPath(job, new Path(outPath));

		job.setJarByClass(TrainFinder.class);
		job.setMapperClass(TrainMapper.class);
		job.setReducerClass(TrainReducer.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		MultipleOutputs.addNamedOutput(job,"North",TextOutputFormat.class,Text.class,Text.class);
		MultipleOutputs.addNamedOutput(job,"South",TextOutputFormat.class,Text.class,Text.class);
		MultipleOutputs.addNamedOutput(job,"East",TextOutputFormat.class,Text.class,Text.class);
		MultipleOutputs.addNamedOutput(job,"West",TextOutputFormat.class,Text.class,Text.class);


		int returnValue = job.waitForCompletion(true) ? 0:1;
		System.out.println("job.isSuccessful " + job.isSuccessful());
		return returnValue;
	}

	public static void main(String[] args) throws Exception {
		Tool job = new TrainFinder();
		int exitCode = ToolRunner.run(job, args);
		System.exit(exitCode);
		
	}
}
