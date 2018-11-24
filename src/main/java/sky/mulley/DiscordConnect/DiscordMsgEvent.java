package sky.mulley.DiscordConnect;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class DiscordMsgEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final MessageReceivedEvent event;
    public DiscordMsgEvent(MessageReceivedEvent event) { this.event = event; }
    public HandlerList getHandlers() { return HANDLERS; }
    public MessageReceivedEvent getMessage() { return this.event; }
}