package sky.mulley.DiscordConnect.Listeners;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import sky.mulley.DiscordConnect.GeneralLogic.MsgEvent;

public class DiscordMsgEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private MsgEvent event;
    public DiscordMsgEvent(MsgEvent event) { event = event; }
    public HandlerList getHandlers() { return HANDLERS; }
    public MsgEvent getMessage() { return event; }
}