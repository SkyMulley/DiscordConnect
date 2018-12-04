package sky.mulley.DiscordConnect.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import sky.mulley.DiscordConnect.Commands.CommandCore;
import sky.mulley.DiscordConnect.GeneralLogic.MessageEvent;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;

public class MessageListener {
    private CommandCore cc;
    private IChannel channel = null;
    private MessageEvent eventMsg;
    DiscordMsgEvent msgEvent = new DiscordMsgEvent(eventMsg);

    public MessageListener(CommandCore cc) { this.cc =cc;}

    @EventSubscriber
    public void onMessageRecieved(MessageReceivedEvent event) {
        eventMsg = (MessageEvent) event;
        if(!cc.commandCheck(eventMsg)) {
            Bukkit.getPluginManager().callEvent(msgEvent);
        } if (channel!=null && event.getChannel().equals(channel)) {
            Bukkit.getServer().broadcastMessage(ChatColor.BLUE+"[Discord] "+ChatColor.WHITE+event.getAuthor().getName()+": "+event.getMessage().toString());
        }
    }

    public void setChannel(IChannel channel) { this.channel=channel;}
}
