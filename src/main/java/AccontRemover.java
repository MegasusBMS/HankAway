import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class AccontRemover {
	@SuppressWarnings("unchecked")
	public static void AccountRemove(long id,CommandContext ctx) {
		JSONArray a = new JSONArray();
		JSONArray e = AccountSaver.existedAcconts();
		if(e==null) {
			ctx.getEvent().getChannel().sendMessage("You are't linked on this id 2").queue();
			return;
		}
		boolean found= false;
		for(int i=0;i<e.size();i++) {
			JSONObject o = (JSONObject) e.get(i);
			String member = o.get("id").toString();
			if(!member.equals(id+"")) {
				a.add(e.get(i));
			}else {
				found = true;
			}
			if(!found) {
				ctx.getEvent().getChannel().sendMessage("You are't linked on this id 1").queue();
			}
		}
		try(FileWriter f = new FileWriter("LinkedFile.json")){
			f.write(a.toString());
			f.flush();
			f.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		ctx.getEvent().getChannel().sendMessage("Unliked succesfully!").queue();
	}
}
