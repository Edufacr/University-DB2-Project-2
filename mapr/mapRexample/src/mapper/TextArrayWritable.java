package mapper;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.Text;

public class TextArrayWritable extends ArrayWritable{

	public TextArrayWritable(Writable[] pValues) {
		super(Text.class, pValues);
	}

}
