package WC;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

public class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable>{

	@Override
	protected void reduce(Text key, Iterable<IntWritable> values,
			Context context)
			throws IOException, InterruptedException {
	
		
		// Input = I, Array with values <1>
		
		// key = "and"
		// value = 1,1,1,1,1,1,1,1,1,1
		int sum = 0;
		Iterator<IntWritable> valuesIt = values.iterator();
		
		while(valuesIt.hasNext()){
			// sum = 0 + 1 
			sum = sum + valuesIt.next().get();
		}
		// sum = 10
		// Array ( I , 1 ) 
		
		
		context.write(key, new IntWritable(sum));
	
		context.getCounter("MyeDBDACounterGroup", "MyeDBDAReducerCounter").increment(1);
		
		System.out.println("This is a Reducer sysout");
	}	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}