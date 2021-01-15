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
    		 // Attributes that do not need much formatting or are not used for calculations
    		 Text name = new Text(arr[1].trim());
    		 Text club = new Text(arr[2].trim());
    		 Date rawBirthDate = new SimpleDateFormat("MM/dd/yyyy").parse(arr[5].trim());
    		 Text birthDate = new Text(new SimpleDateFormat("yyyy-MM-dd").format(rawBirthDate));
    		 Text marketValue = new Text(arr[28].trim());
    		 
    		 // Attributes for calculating the rate of change in a player's value
    		 int currentValue = Integer.parseInt(arr[28].trim());
    		 int highestValue = arr[31].trim().isEmpty() ? 0 : Integer.parseInt(arr[31].trim());
    		 
    		 String strMvDate = arr[29].replace("\"", "").replace("Last update: ", "");
    		 Date currentValueDate = new SimpleDateFormat("MMM dd, yyyy").parse(strMvDate);
    		 Date highestValueDate = arr[32].trim().isEmpty() ? null : new SimpleDateFormat("MM/dd/yyyy").parse(arr[32].trim());
    		 
    		 double valueRoChange = 0;
    		 if (highestValue!=0&&highestValueDate!=null) {
    			 double current = (currentValueDate.compareTo(highestValueDate) >= 0) ? currentValue : highestValue;
    			 double previous = (current==currentValue) ? highestValue : currentValue;
    			 valueRoChange = (current/previous) - 1.0;
    		 }
    		 
    		 Text valueChange = new Text(Double.toString(valueRoChange));
    		 
    		 // Array for building a multiple field value
    		 Writable[] values = {name, birthDate, marketValue, valueChange};
    		 TextArrayWritable writable = new TextArrayWritable(values);
    		 
    		 context.write(club, writable);
		 }
     } catch(Exception e) {
    	 System.out.println(e);
     }
   }
}
