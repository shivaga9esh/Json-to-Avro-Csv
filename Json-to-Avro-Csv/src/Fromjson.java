import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumWriter;
import org.apache.commons.io.IOUtils;
import org.json.simple.parser.ParseException;

public class Fromjson {

	public static void main(String[] args) throws ParseException, IOException {

		InputStream myJSONInputStream = null ;
		DataFileWriter<GenericRecord> dataFileWriter = null;
		try {
			
			// Step 1 : Read the avro schema
			Schema schema = new Schema.Parser().parse(new File("resources/meetup_rsvp.avsc"));
			
			// Step 2 : Build the input stream out of the JSON payload
			myJSONInputStream = new FileInputStream("resources/meetup_rsvp.json");
			byte[] myJSONBytes = IOUtils.toByteArray(myJSONInputStream);
   		  
			//Step 3: Give the location for AVRO file generation
			File file = new File("resources/meetup_rsvp.avro");
			
			//Step 4: Create the AVRO file writer with the right schema path and output path
			DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<GenericRecord>(schema);
			dataFileWriter = new DataFileWriter<GenericRecord>(datumWriter);
			dataFileWriter.create(schema, file);
			
			//Step 5: Write the data in input byte stream
			System.out.println("Started creating Avro file - Serialization Initiated....");
			
			ByteBuffer datum = ByteBuffer.wrap(myJSONBytes);
			dataFileWriter.appendEncoded(datum);
		
			System.out.println("Successfully created Avro file - Serialization Successful.");
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			myJSONInputStream.close();
			dataFileWriter.close();
		}
		
	}

}
