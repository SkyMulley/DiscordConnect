package sky.mulley.DiscordConnect;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import sky.mulley.DiscordConnect.Commands.CommandCore;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;

public class MessageListener {
    private CommandCore cc;
    private IChannel channel = null;
    private MessageReceivedEvent eventMsg;
    DiscordMsgEvent msgEvent = new DiscordMsgEvent(eventMsg);

    public MessageListener(CommandCore cc) { this.cc =cc;}

    @EventSubscriber
    public void onMessageRecieved(MessageReceivedEvent event) {
        eventMsg = event;
        if(!cc.commandCheck(event)) {
            Bukkit.getPluginManager().callEvent(msgEvent);
        } if (channel!=null && event.getChannel().equals(channel)) {
            Bukkit.getServer().broadcastMessage(ChatColor.BLUE+"[Discord] "+ChatColor.WHITE+event.getAuthor().getName()+":"+event.getMessage().toString());
        }
    }

    public void setChannel(IChannel channel) { this.channel=channel;}
}
