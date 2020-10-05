import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import net.dv8tion.jda.api.entities.Message;

public class LinkCommand implements ICommand {

	@Override
	public void handle(CommandContext ctx){
			try {
				URL(ctx.getArgs(),ctx);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	public void URL(List<String> list,CommandContext ctx) throws IOException{
		//List<Message> Messages = ctx.getEvent().getChannel().getHistory().retrievePast(1).complete();
		//ctx.getEvent().getChannel().deleteMessages(Messages).queue();
		if(list.size()!=2) {
			ctx.getEvent().getChannel().sendMessage(getHelp()).queue();
			return;
		}
		URL url = new URL("http://kiralycraft.com/0e8ba203-2a70-492a-914a-5de3c708958b/post/login");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setInstanceFollowRedirects(false);
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.setRequestProperty("Cookie", "tarantula=f5b01f94;");
		conn.connect();
		DataOutputStream out = new DataOutputStream(conn.getOutputStream());
		String x = "{\"username\":\""+ list.get(0) +"\",\"password\":\""+ list.get(1) +"\",\"totptoken\":\"\"}";
		out.writeBytes(x);
		out.flush();
		out.close();
		switch(conn.getResponseCode()) {
		case 200:
			AccountSaver.AccountSave(ctx.getEvent().getMember().getIdLong(), list.get(0), list.get(1), ctx);
			break;
		case 400:
			ctx.getEvent().getChannel().sendMessage("The username, password or totptoken is incorrect.").queue();
			break;
		case 403:
			ctx.getEvent().getChannel().sendMessage("The email address has not been confirmed.").queue();
		}

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return KiraBOT.prefix+"link";
	}

	@Override
	public String getHelp() {
		// TODO Auto-generated method stub
		return KiraBOT.prefix+"link (username) (password)";
	}

	@Override
	public String getAliase() {
		return KiraBOT.prefix+"l";
	}

}
