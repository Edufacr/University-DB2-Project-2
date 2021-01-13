package mapper;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class SimpleMaper extends Mapper<Object, Text, Text, TextArrayWritable> {
   public void map(Object key, Text value, Context context) throws IOException, InterruptedException
   {
     String[] arr = value.toString().split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
     
     try {
    	 if (arr[26].equals("England")) {
    		 Text name = new Text(arr[1].trim());
    		 Text club = new Text(arr[2].trim());
    		 Text birthDate = new Text(arr[5].trim());
    		 Text position = new Text(arr[11].trim());
    		 Text marketValue = new Text(arr[28].trim());
    		 
    		 String strMvDate = arr[29].replace("\"", "").replace("Last update: ", "");
    		 Date rawMvDate = new SimpleDateFormat("MMM dd, yyyy").parse(strMvDate);
    		 
    		 Text mvDate = new Text(new SimpleDateFormat("MM/dd/yyyy").format(rawMvDate));
    		 Text highestValue = new Text(arr[31].trim());
    		 Text hvDate = new Text(arr[32].trim());
    		 
    		 Writable[] values = {name, birthDate, position, marketValue, mvDate, highestValue, hvDate};
    		 TextArrayWritable writable = new TextArrayWritable(values);
    		 
    		 context.write(club, writable);
		 }
     } catch(Exception e) {
    	 System.out.println(e);
     }
   }
}
