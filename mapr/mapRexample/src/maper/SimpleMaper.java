package maper;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class SimpleMaper extends Mapper<Object, Text, Text, MapWritable> {
   public void map(Object key, Text value, Context context) throws IOException, InterruptedException
   {
     StringTokenizer itr = new StringTokenizer(value.toString(), ",");
     while (itr.hasMoreTokens()) 
     {
    	 try 
    	 {
    		 String fechastr = itr.nextToken();
    		 String monto = itr.nextToken();
    		 
    		 Float amount = Float.parseFloat(monto);
    		 FloatWritable amountToWrite = new FloatWritable(amount); 

    		 Date fecha = new SimpleDateFormat("MM/dd/yyyy").parse(fechastr);

    		 Text nameofmonth = new Text(new SimpleDateFormat("MMM").format(fecha));
    		 Text year = new Text(new SimpleDateFormat("yyyy").format(fecha));

        	 MapWritable writable = new MapWritable();
        	 writable.put(nameofmonth, amountToWrite);

        	 context.write(year, writable);
    	 } 
    	 catch (Exception ex) 
    	 {
    		 System.out.println(ex.getMessage());
    	 }
     }
   }
}
