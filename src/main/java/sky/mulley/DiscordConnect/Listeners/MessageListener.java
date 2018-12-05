package sky.mulley.DiscordConnect.Listeners;

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
    private MsgEvent eventMsg;
    private IRole role;
    DiscordMsgEvent msgEvent = new DiscordMsgEvent(eventMsg);

    public MessageListener(CommandCore cc) { this.cc =cc;}

    @EventSubscriber
    public void onMessageRecieved(MessageReceivedEvent event) {
        eventMsg = new MsgEvent(event.getMessage());
        if(!cc.commandCheck(eventMsg)) {
            Bukkit.getPluginManager().callEvent(msgEvent);
        } else if (channel!=null && event.getChannel().equals(channel)) {
            if(!event.getAuthor().isBot()) {
                if (event.getAuthor().getRolesForGuild(event.getGuild()).size() != 0) {
                    IRole role = event.getAuthor().getRolesForGuild(event.getGuild()).get(0);
                    Bukkit.getServer().broadcastMessage(ChatColor.BLUE + "[Discord] " + ChatColor.WHITE + event.getAuthor().getName() + " | " + role + ": " + event.getMessage().toString());
                } else {
                    Bukkit.getServer().broadcastMessage(ChatColor.BLUE + "[Discord] " + ChatColor.WHITE + event.getAuthor().getName()+": " + event.getMessage().toString());
                }
            }
        }
    }

    public void setChannel(IChannel channel) { this.channel=channel;}
}
