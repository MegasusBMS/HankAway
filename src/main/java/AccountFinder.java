import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class AccountFinder {
	public static String ObjectByID(String s,long id) {
		JSONParser p = new JSONParser();
		
		try {
			Object o = p.parse(new FileReader("LinkedFile.json"));
			JSONArray a = (JSONArray) o;
			for(int i=0;i<a.size();i++) {
				JSONObject obj = (JSONObject)a.get(i);
				long key = (long)obj.get("id");
				if(key==id) {
					return (String) obj.get(s);
				}
			}
		}catch(IOException | ParseException e) {
			e.printStackTrace();
		}
		return s;
	}
}
