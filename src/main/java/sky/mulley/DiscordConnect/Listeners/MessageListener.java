package sky.mulley.DiscordConnect.Listeners;

import com.vdurmont.emoji.EmojiManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import sky.mulley.DiscordConnect.Commands.CommandCore;
import sky.mulley.DiscordConnect.GeneralLogic.MsgEvent;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IRole;

public class MessageListener {
    private CommandCore cc;
    private IChannel channel = null;
    private IChannel adminconsole = null;
    private MsgEvent eventMsg;
    private IRole role;
    DiscordMsgEvent msgEvent = new DiscordMsgEvent(eventMsg);
    private int maxchar;

    public MessageListener(CommandCore cc, int maxchar) { this.cc =cc; this.maxchar=maxchar;}

    @EventSubscriber
    public void onMessageRecieved(MessageReceivedEvent event) {
        eventMsg = new MsgEvent(event.getMessage());
        if(!cc.commandCheck(eventMsg)) {
            Bukkit.getPluginManager().callEvent(msgEvent);
        } if (channel!=null && event.getChannel().equals(channel)) {
            if(!event.getAuthor().isBot()) {
                if(event.getMessage().getContent().length()!=maxchar) {
                    if (event.getAuthor().getRolesForGuild(event.getGuild()).size() != 0) {
                        IRole role = event.getAuthor().getRolesForGuild(event.getGuild()).get(0);
                        Bukkit.getServer().broadcastMessage(ChatColor.BLUE + "[Discord] " + ChatColor.WHITE + event.getAuthor().getName() + " | " + role.getName() + ": " + event.getMessage().toString());
                    } else {
                        Bukkit.getServer().broadcastMessage(ChatColor.BLUE + "[Discord] " + ChatColor.WHITE + event.getAuthor().getName() + ": " + event.getMessage().toString());
                    }
                } else {
                    event.getMessage().addReaction(EmojiManager.getForAlias("octagonal_sign"));
                }
            }
        } if (adminconsole!=null && event.getChannel().equals(adminconsole)) {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),event.getMessage().getContent());
        }
    }

    public void setChannel(IChannel channel) { this.channel=channel;}

    public void setAdminconsole(IChannel channel) {this.adminconsole=channel;}
}
