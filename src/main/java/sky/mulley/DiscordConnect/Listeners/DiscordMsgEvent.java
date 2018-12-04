package sky.mulley.DiscordConnect.Listeners;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class DiscordMsgEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private MessageEvent event;
    public DiscordMsgEvent(MessageEvent event) { event = event; }
    public HandlerList getHandlers() { return HANDLERS; }
    public MessageEvent getMessage() { return event; }
}