package sky.mulley.DiscordConnect.GeneralLogic;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IMessage;

public class MessageEvent extends MessageReceivedEvent {
    public MessageEvent(MessageReceivedEvent event) {
        super((IMessage) event);
    }
}
