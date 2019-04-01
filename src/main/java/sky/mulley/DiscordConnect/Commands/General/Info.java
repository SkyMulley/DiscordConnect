package sky.mulley.DiscordConnect.Commands.General;

import sky.mulley.DiscordConnect.Commands.BaseCommand;
import sky.mulley.DiscordConnect.GeneralLogic.MsgEvent;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.RequestBuffer;

public class Info extends BaseCommand {
    public Info() {
        super("Info");
        helpMessage = "Discover more about this bot!";
        Usage = "info";
        helpViewable = true;
    }

    @Override
    public boolean executeCommand(MsgEvent event, String[] argArray) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.withAuthorName("DiscordConnect");
        builder.appendField("OwO whats this?","This is a Spigot Plugin Discord Bot, take a look at it at it's Git Repo if you'd like",false);
        builder.appendField("Github Link","[Click Me](https://github.com/SkySmith2/DiscordConnect)",false);
        builder.withColor(0,0,255);
        builder.withFooterText("Made by Sky#2134 | Version 1.0B");
        RequestBuffer.request(() -> event.getData().getChannel().sendMessage(builder.build()));
        return true;
    }
}
