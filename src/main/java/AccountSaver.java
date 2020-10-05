import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import net.dv8tion.jda.api.EmbedBuilder;

public class AccountSaver {
	@SuppressWarnings("unchecked")
	public static void AccountSave(long id,String user,String password,CommandContext ctx) throws FileNotFoundException, IOException {
		if(exist(id)) {
			EmbedBuilder e = new EmbedBuilder();
			e.setTitle("Your account is allready linked!");
			e.setDescription("if you want to unlinked , use "+KiraBOT.prefix+"unlink");
			ctx.getEvent().getChannel().sendMessage(e.build()).queue();
			return;
		}
		JSONObject list = new JSONObject();
		JSONArray a = new JSONArray();
		list.put("id", id);
		list.put("username", user);
		list.put("password", password);
		FileReader fr = new FileReader("LinkedFile.json");
		if(fr.read()!=-1) {
		JSONArray e = existedAcconts();
		for(int i=0;i<e.size();i++) {
			a.add(e.get(i));
		}
		}
		fr.close();
		a.add(list);
		try(FileWriter f = new FileWriter("LinkedFile.json")){
			f.write(a.toString());
			f.flush();
			f.close();
		}catch(IOException er) {
			er.printStackTrace();
		}
		ctx.getEvent().getChannel().sendMessage("Account: "+user+" is linked on your discord id").queue();
	}
	public static JSONArray existedAcconts() {
		JSONParser p = new JSONParser();
		Object o;
		try {
			o=p.parse(new FileReader("LinkedFile.json"));
			JSONArray a = (JSONArray) o;
			return a;
		}catch(IOException|ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean exist(long id) {
		JSONArray a = existedAcconts();
		if(a!=null) {
			for(int i=0;i<a.size();i++) {
				JSONObject e = (JSONObject) a.get(i);
				String key = e.get("id").toString();
				if(key.contentEquals(id+""))
					return true;
			}
			return false;
		}
		return false;
	}
	
}
