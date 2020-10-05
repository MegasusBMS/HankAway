import java.awt.Color;
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
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import net.dv8tion.jda.api.EmbedBuilder;

public class PcListCommand implements ICommand{

	@Override
	public void handle(CommandContext ctx) {
		
		Map<String, List<String>> cookie = new HashMap<>();
		
		try {
		
		///////////////////////////////////////////////////////////////
		
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
		
		URL url2 = new URL("http://kiralycraft.com/0e8ba203-2a70-492a-914a-5de3c708958b/post/listpctypes");
		HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
		conn2.setDoOutput(true);
		conn2.setInstanceFollowRedirects(false);
		conn2.setRequestMethod("POST");
			
			List<String> c = cookie.get("Set-Cookie");
			String coo = "";
			if(c==null) {
				ctx.getEvent().getChannel().sendMessage("Pls relink again , your password maybe had changed").queue();
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
		
		////////////////////////////////////////////////////////////////////////
		
		JSONParser p = new JSONParser();
		Object o = p.parse(response.toString());
		JSONObject obj = (JSONObject) o;
		JSONArray a = new JSONArray();
		a = (JSONArray) obj.get("list");
		for(int i = 0;i<a.size();i++) {
			
			obj = (JSONObject) a.get(i);
			EmbedBuilder e = new EmbedBuilder();
			e.setThumbnail("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.shutterstock.com%2Fsearch%2Fchip%2Bprocessor%2Bcpu%2Blogo%2Bvector&psig=AOvVaw2MpBTFTlwZDb9o5PjcrYSh&ust=1599238405186000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCODbmb-5zesCFQAAAAAdAAAAABAD");
			e.setTitle(":desktop: ** "+obj.get("id")+")"+obj.get("codeName")+"**");
			
			e.setColor(getRandomColor());
			
			String uri = "";
			switch(Integer.parseInt((String) obj.get("id"))) {
			case 1:
				uri = "https://ark.intel.com/content/www/us/en/ark/products/97143/intel-pentium-processor-g4560-3m-cache-3-50-ghz.html";
				break;
			case 2:
				uri = "https://ark.intel.com/content/www/us/en/ark/products/126688/intel-core-i3-8100-processor-6m-cache-3-60-ghz.html";
				break;
			case 3:
				uri = "https://www.amd.com/en/products/cpu/amd-ryzen-7-1700x";
				break;
				
			}
			e.appendDescription("**CPU: "+String.format("[%s](%s)\n",obj.get("cpu"),uri));
			
			uri = "";
			switch(Integer.parseInt((String) obj.get("id"))) {
			case 1:
				uri = "https://www.nvidia.com/en-sg/geforce/products/10series/geforce-gtx-1080-ti/";
				break;
			case 2:
				uri = "https://www.nvidia.com/en-sg/geforce/products/10series/geforce-gtx-1080-ti/";
				break;
			case 3:
				uri = "https://www.nvidia.com/en-sg/geforce/products/10series/geforce-gtx-1080-ti/";				
				
			}
			e.appendDescription("GPU: "+String.format("[%s](%s)\n",obj.get("gpu"),uri));
			
			e.appendDescription("RAM: "+obj.get("ram")+"\n");
			e.appendDescription("SSD: "+obj.get("ssd")+"\n");
			e.appendDescription("HDD: "+obj.get("hdd")+"\n");
			e.appendDescription("Network speed: "+obj.get("net")+"\n");
			e.appendDescription("Price per kwh: "+obj.get("pricePerKwh")+"**");
			e.setFooter("**Power by Megasus**");
			
			ctx.getEvent().getChannel().sendMessage(e.build()).queue();
		}
		
		}catch(IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	 public Color getRandomColor() {
		 Random rand = new Random();
		 float r = rand.nextFloat();
		 float g = rand.nextFloat();
		 float b = rand.nextFloat();
		    return new Color(r, g, b) ;
		}

	@Override
	public String getName() {
		return KiraBOT.prefix+"pclist";
	}

	@Override
	public String getHelp() {
		return null;
	}

	@Override
	public String getAliase() {
		return KiraBOT.prefix+"pc";
	}
	
}
