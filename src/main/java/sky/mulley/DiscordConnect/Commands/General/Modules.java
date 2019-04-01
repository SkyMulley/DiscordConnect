package sky.mulley.DiscordConnect.Commands.General;

import com.fasterxml.jackson.databind.ser.Serializers;
import sky.mulley.DiscordConnect.Commands.BaseCommand;
import sky.mulley.DiscordConnect.Commands.CommandCore;
import sky.mulley.DiscordConnect.GeneralLogic.MsgEvent;
import sky.mulley.DiscordConnect.Modules.DiscordModule;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.RequestBuffer;

import java.util.ArrayList;
import java.util.List;

public class Modules extends BaseCommand {
    private List<DiscordModule> modules;
    public Modules (List<DiscordModule> modules) {
        super("Modules");
        helpMessage = "Allows you to list all modules being used by DiscordConnect";
        Usage = "modules";
        helpViewable = true;
        this.modules = modules;
    }

    @Override
    public boolean executeCommand(MsgEvent event, String[] argArray) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.withAuthorName("Module Menu");
        builder.withAuthorIcon(event.getData().getAuthor().getAvatarURL());
        builder.withFooterText("DiscordConnect | Called by "+event.getData().getAuthor().getName());
        for(DiscordModule DM : modules) {
            String allcommands = "";
            for(BaseCommand com : DM.getCommands()) {
                allcommands = allcommands + com.getCommandName() + "\n";
            }
            if(!allcommands.equals("")) {
                builder.appendField(DM.getName(), "Author: "+DM.getAuthor()+"\n**Commands: "+allcommands,false);
            } else {
                builder.appendField(DM.getName(), "Author: "+DM.getAuthor(),false);
            }
        }
        RequestBuffer.request(() -> event.getData().getChannel().sendMessage(builder.build()));
        return true;
    }
}
