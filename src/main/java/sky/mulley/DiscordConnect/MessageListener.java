package sky.mulley.DiscordConnect;

import org.bukkit.Bukkit;
import sky.mulley.DiscordConnect.Commands.CommandCore;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class MessageListener {
    private CommandCore cc;
    private MessageReceivedEvent eventMsg;
    DiscordMsgEvent msgEvent = new DiscordMsgEvent(eventMsg);

    public MessageListener(CommandCore cc) { this.cc =cc;}

    @EventSubscriber
    public void onMessageRecieved(MessageReceivedEvent event) {
        eventMsg = event;
        if(!cc.commandCheck(event)) {
            Bukkit.getPluginManager().callEvent(msgEvent);
        }
    }
}
