package reducer;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import mapper.TextArrayWritable;

import java.io.IOException;

public class SimpleReducer extends Reducer<Text, TextArrayWritable, Text, Text> {
	private Text result = new Text();
	
    public void reduce(Text key, Iterable<TextArrayWritable> values, Context context ) throws IOException, InterruptedException {
    	
    	try {
    		for (TextArrayWritable player : values) {
        		String[] playerInfo = player.toStrings();
        		String resultStr = "";
        		int index = 0;
        		while (index < playerInfo.length-1) {
        			resultStr+=playerInfo[index]+",";
        			index++;
        		}
        		resultStr+=playerInfo[index];
        		result.set(resultStr);
        		context.write(key, result);
        	}
    	} catch(Exception e) {
    		System.out.println(e);
    	}
    }
}