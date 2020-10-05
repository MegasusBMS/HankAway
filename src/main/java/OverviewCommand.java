import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import net.dv8tion.jda.api.EmbedBuilder;

public class OverviewCommand implements ICommand{

	@Override
	public void handle(CommandContext ctx){
		
		
		
		Map<String, List<String>> cookie = new HashMap<>();
		
		try {
			
		//////////////////////////////////////////////////////////////////////////////////////////////////////////
			
		URL url = new URL("http://kiralycraft.com/0e8ba203-2a70-492a-914a-5de3c708958b/post/login");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setInstanceFollowRedirects(false);
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.setRequestProperty("Cookie", "tarantula=f5b01f94;");
		conn.connect();
		DataOutputStream out = new DataOutputStream(conn.getOutputStream());
		String username = AccountFinder.ObjectByID("username", ctx.getEvent().getMember().getIdLong());
		String password = AccountFinder.ObjectByID("password", ctx.getEvent().getMember().getIdLong());
		if(username == null) {
			ctx.getEvent().getChannel().sendMessage("You are not in ower data base pls use link command \n"+KiraBOT.prefix+"link (username) (password)");
		}
		String x = "{\"username\":\""+ username  +"\",\"password\":\""+ password +"\",\"totptoken\":\"\"}";
		out.writeBytes(x);
		out.flush();
		out.close();
		cookie = conn.getHeaderFields();
		
		URL url2 = new URL("http://kiralycraft.com/0e8ba203-2a70-492a-914a-5de3c708958b/post/accountoverview");
		HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
		conn2.setDoOutput(true);
		conn2.setInstanceFollowRedirects(false);
		conn2.setRequestMethod("POST");
			
			List<String> c = cookie.get("Set-Cookie");
			String coo = "";
			if(c==null) {
				ctx.getEvent().getChannel().sendMessage("Pls relink again , your password had changed").queue();
				return;
			}
			for(String s : c) {
				s=s.substring(0,s.indexOf(";"));
				if(coo.length()>0)
				coo+=","+s;
				else {
					coo+=s;
				}
			}
		conn2.addRequestProperty("Cookie", coo);
		conn2.connect();
		InputStream is;
		if(conn2.getResponseCode() == 200)
			is = conn2.getInputStream();
		else
			is = conn2.getErrorStream();
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(is));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) 
		{
			response.append(inputLine+"\n");
		}
		in.close();
		
		///////////////////////////////////////////////////////////////////////////
		
		JSONParser p = new JSONParser();
		Object o = p.parse(response.toString());
		JSONObject obj = (JSONObject) o;
		obj=(JSONObject) obj.get("object");
		
		EmbedBuilder e = new EmbedBuilder();
		e.setTitle((obj.get("isadmin").equals("true") ? "<:AdminLogo:751105804488278187>** Admin: **"+obj.get("username") : "<:UserLogo:751106498725413005> **User: **"+obj.get("username"))+"                     ");
		e.setColor(obj.get("isadmin").equals("true") ? 0xE4401D : 0x1DE462);
		e.setThumbnail("https://cdn.discordapp.com/icons/573164133948719104/cd2bc107b5db1f7de68a0c9828517639.webp?size=128");
		e.setDescription("**Credits: "+obj.get("credits")+"\n"+"NetworkISP :"+obj.get("networkisp")+"**");
		e.setFooter("**Power by Megasus**");
		ctx.getEvent().getChannel().sendMessage(e.build()).queue();
		
		}catch(IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public String getName() {
		return KiraBOT.prefix+"Overview";
	}

	@Override
	public String getHelp() {
		return "Show your stats.";
	}

	@Override
	public String getAliase() {
		return KiraBOT.prefix+"view";
	}

}
