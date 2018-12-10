package sky.mulley.DiscordConnect.Commands.General;

import sky.mulley.DiscordConnect.Commands.BaseCommand;
import sky.mulley.DiscordConnect.GeneralLogic.MsgEvent;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.RequestBuffer;

import java.util.List;

public class Help extends BaseCommand {
    private List<BaseCommand> CC;
    public Help(List<BaseCommand> CC) {
        super("Help");
        helpMessage = "See all the commands in the bot";
        Usage = "!Help";
        helpViewable = false;
        this.CC = CC;
    }

    @Override
    public boolean executeCommand(MsgEvent event, String[] argArray) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.withAuthorName("JeremieBot Help");
        builder.withAuthorIcon(event.getData().getAuthor().getAvatarURL());
        builder.withFooterText("DiscordConnect | Called by "+event.getData().getAuthor().getName());
        for(BaseCommand CG : CC) {
            if(CG.isHelpViewable()) {builder.appendField(""+CG.getCommandName(),"Description: "+CG.getHelpMessage()+"\nUsage: "+CG.getUsage(),false);}
        }
        RequestBuffer.request(() -> event.getData().getChannel().sendMessage(builder.build()));
        return true;
    }
}
