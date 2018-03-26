import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Tocsv {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		try (InputStream in = new FileInputStream(new File("resources/meetup_rsvp.json"));) {
		    CSV csv = new CSV(true, ',', in );
		    List < String > fieldNames = null;
		    if (csv.hasNext()) fieldNames = new ArrayList < > (csv.next());
		    
		    // fieldNames parsing error
		    
		    List < Map < String, String >> list = new ArrayList < > ();
		    
		    while (csv.hasNext()) {
		        List < String > x = csv.next();
		        Map < String, String > obj = new LinkedHashMap < > ();
		        
		        //System.out.println(fieldNames.size());
		        //for (int i = 0; i < fieldNames.size() ; i++) {
		        for (int i = 0; i < 24 ; i++) {
		            obj.put(fieldNames.get(i), x.get(i));
		        }
		        list.add(obj);
		    }
		    ObjectMapper mapper = new ObjectMapper();
		    mapper.enable(SerializationFeature.INDENT_OUTPUT);
		    
		    System.out.println("Started creating CSV file - Serialization Initiated....");
		    
		    mapper.writeValue(new File("resources/meetup_rsvp.csv"), list);
		    
		    System.out.println("CSV file successfully created - Serialization Complete.");
		   
		}
	}

}
