package sky.mulley.DiscordConnect.Commands.General;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import sky.mulley.DiscordConnect.Commands.BaseCommand;
import sky.mulley.DiscordConnect.GeneralLogic.MsgEvent;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.RequestBuffer;

public class Status extends BaseCommand {
    public Status () {
        super("Status");
        helpMessage = "Get server player count and list";
        Usage = "status";
        helpViewable = true;
    }

    @Override
    public boolean executeCommand(MsgEvent event, String[] argArray) {
        int PlayerCount = Bukkit.getServer().getOnlinePlayers().size();
        IUser Author = event.getData().getAuthor();
        String Players = "";
        EmbedBuilder builder = new EmbedBuilder();
        if (Bukkit.getOnlinePlayers().size() == 0) {
            builder.appendField(Bukkit.getServerName()+" is Online!", "No players are online right now!", true);
        } else {
            if (Bukkit.getOnlinePlayers().size() != 1) {
                builder.appendField(Bukkit.getServerName()+" is Online!", "with " + PlayerCount + " players connected!", true);
            } else {
                builder.appendField(Bukkit.getServerName()+" is Online!", "with " + PlayerCount + " player connected!", true);
            }
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (Bukkit.getOnlinePlayers().size() != 1) {
                    Players = Players + player.getName() + ", ";
                } else {
                    Players = player.getName();
                }
            }
            builder.appendField("Connected Players", Players, false);
        }
        builder.withAuthorName("Server Status");
        RequestBuffer.request(() -> event.getData().getChannel().sendMessage(builder.build()));
        return true;
    }
}
