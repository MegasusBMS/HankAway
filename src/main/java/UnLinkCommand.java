
public class UnLinkCommand implements ICommand{

	@Override
	public void handle(CommandContext ctx) {
		AccontRemover.AccountRemove(ctx.getEvent().getMember().getIdLong(), ctx);
	}

	@Override
	public String getName() {
		return KiraBOT.prefix + "unlink";
	}

	@Override
	public String getHelp() {
		return KiraBOT.prefix+"unlink";
	}

	@Override
	public String getAliase() {
		return KiraBOT.prefix + "unlink";
	}

}
