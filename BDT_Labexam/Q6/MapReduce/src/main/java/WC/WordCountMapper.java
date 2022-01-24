package WC;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

	private final static IntWritable one = new IntWritable(1);
	private Text word = new Text();

	@Override
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		// I am learning at CDAC ACTS
		String line = value.toString();
		System.out.println("This is a Mapper sysout : " + line);
		
		String[] splittedString = line.split("\\ ");
		for (String split : splittedString) {
		
			if ("and".equalsIgnoreCase(split)){
				context.getCounter("MyeDBDACounterGroup", "AndCounter").increment(1);
			}
			
			word.set(split);
			context.write(word, one);
		}

		// Add a custom counter				
		context.getCounter("MyeDBDACounterGroup", "MyeDBDAMapperCounter").increment(1);

		
	}
}
