package reducer;

import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;
import java.util.Hashtable;

public class SimpleReducer extends Reducer<Text, MapWritable, Text, Text> {
	private Hashtable<String,Float> sumas = new Hashtable<String,Float>();
	private Text result = new Text();
	
	
    public void reduce(Text key, Iterable<MapWritable> values, Context context ) throws IOException, InterruptedException {
      
      for (MapWritable mes : values) 
      {
    	  Text tkey = (Text) mes.keySet().toArray()[0];
    	  String llave = tkey.toString();
    	  
    	  if (sumas.containsKey(llave)){
    		  sumas.put(llave, sumas.get(llave).floatValue() + ((FloatWritable) mes.get(tkey)).get());
    	  } else {
    		  sumas.put(llave, ((FloatWritable) mes.get(tkey)).get());
    	  }
      }
      
      String mes = (String) sumas.keySet().toArray()[0];
      float amount = sumas.get(mes);
      
      
      for(String mesK : sumas.keySet()) {
    	  float iAmount = sumas.get(mesK);
    	  if (iAmount > amount) {
    		  mes = mesK;
    		  amount = iAmount;
    	  }
      }
      
      String resultStr = mes+","+amount;

    	result.set(resultStr);
    	context.write(key, result);
    }
}