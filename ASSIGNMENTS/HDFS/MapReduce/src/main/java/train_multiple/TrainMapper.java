package train_multiple;

import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class TrainMapper extends Mapper<LongWritable, Text, Text, Text> {

	Text word = new Text("TestString");

	@Override
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

		String line = value.toString();
		String[] splitRecord = line.split("\\|");
		word.set(splitRecord[4]);
		context.write(word, new Text(line));
	}
}
