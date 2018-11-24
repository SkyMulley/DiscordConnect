package sky.mulley.DiscordConnect.Commands.General;

import sky.mulley.DiscordConnect.Commands.BaseCommand;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.RequestBuffer;

import java.util.List;

public class Help extends BaseCommand {
    private List<BaseCommand> CC;
    public Help(List<BaseCommand> CC) {
        commandName = "Help";
        helpMessage = "See all the commands in the bot";
        Usage = "!Help";
        helpViewable = false;
        this.CC = CC;
    }

    @Override
    public boolean executeCommand(MessageReceivedEvent event, String[] argArray) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.withAuthorName("JeremieBot Help");
        builder.withAuthorIcon(event.getAuthor().getAvatarURL());
        builder.withFooterText("JeremieBot 3.0 | Called by "+event.getAuthor().getName());
        for(BaseCommand CG : CC) {
            if(CG.isHelpViewable()) {builder.appendField(""+CG.getCommandName(),"Description: "+CG.getHelpMessage()+"\nUsage: "+CG.getUsage(),false);}
        }
        RequestBuffer.request(() -> event.getChannel().sendMessage(builder.build()));
        return true;
    }
}
