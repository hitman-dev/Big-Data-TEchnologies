package train_multiple;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

public class TrainReducer extends Reducer<Text, Text, Text, Text> {

    @Override
    protected void reduce(Text key, Iterable<Text> value, Context context) throws IOException, InterruptedException {
        MultipleOutputs<Text, Text> mos = new MultipleOutputs<>(context);

//		String minSpeedTrainNo = "";
//		int minSpeed = 10000;
//		String minDir = "";
        for (Text trainData : value) {
            String[] splitString = trainData.toString().split("\\|");
            String trainNo = splitString[0];
            int speed = Integer.parseInt(splitString[3]);
            String direction = splitString[4];
//			if (minSpeed >= speed) {
//				minSpeed = speed;
//				minSpeedTrainNo = trainNo;
//				minDir = direction;
//			}
            if(direction.equals("N")) {
                mos.write("North", key, new Text(trainNo + "\t" + direction + "\t" + speed));

            }else if(direction.equals("S")){
                mos.write("South", key, new Text(trainNo + "\t" + direction + "\t" + speed));

            }else if(direction.equals("E")){
                mos.write("East", key, new Text(trainNo + "\t" + direction + "\t" + speed));

            }else if(direction.equals("W")){
                mos.write("West", key, new Text(trainNo + "\t" + direction + "\t" + speed));
            }
        }
    }
//		context.write(new Text(minSpeedTrainNo), new Text(minSpeed + "   " + minDir));
}

