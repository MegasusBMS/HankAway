import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class KiraBOT {
	
	
	public static JDA jda;
	public static String prefix = "!";
	
	public static void main(String[] args) throws LoginException {
		
		jda = JDABuilder.createDefault("NzUxMDAwNjAyMjc1ODcyNzY4.X1Ct0A.zRX8OJlkd2YqBHBgWFog28NaQNQ").addEventListeners(new Listener()).build();
		
	}
}
