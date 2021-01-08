package mapper;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.ArrayList;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class SimpleMaper extends Mapper<Object, Text, Text, TextArrayWritable> {
   public void map(Object key, Text value, Context context) throws IOException, InterruptedException
   {
     StringTokenizer itr = new StringTokenizer(value.toString(), ",");
     ArrayList<String> columns = new ArrayList<String>();
     while (itr.hasMoreTokens()) 
     {
    	 try 
    	 {
    		 columns.clear();
    		 
    		 for (int col = 0; col < 35; col++) 
    		 {
    			 columns.add(itr.nextToken());
    		 }
    		 
    		 if (columns.get(26)=="England") {	    			 
	    		 Text name = new Text(columns.get(1));
	    		 Text club = new Text(columns.get(2));
	    		 Text birthDate = new Text(columns.get(5));
	    		 Text position = new Text(columns.get(11));
//	    		 Text nationality = new Text(columns.get(26));
//	    		 IntWritable marketValue = new IntWritable(Integer.parseInt(columns.get(28)));
	    		 Text marketValue = new Text(columns.get(28));
	    		 Text mvDate = new Text(columns.get(29));
//	    		 IntWritable highestValue = new IntWritable(Integer.parseInt(columns.get(31)));
	    		 Text highestValue = new Text(columns.get(31));
	    		 Text hvDate = new Text(columns.get(32));
	    		 
//	    		 MapWritable writable = new MapWritable();
//	    		 writable.put(name, birthDate);
//	    		 writable.put(club, position);
//	    		 writable.put(marketValue, mvDate);
//	    		 writable.put(highestValue, hvDate);
	    		 
	    		 Writable[] values = {name, birthDate, position, marketValue, mvDate, highestValue, hvDate};
	    		 TextArrayWritable writable = new TextArrayWritable(values);
	    		 
	    		 context.write(club, writable);
    		 }
    	 } 
    	 catch (Exception ex) 
    	 {
    		 System.out.println(ex.getMessage());
    	 }
     }
   }
}
