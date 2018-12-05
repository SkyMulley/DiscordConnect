package sky.mulley.DiscordConnect.GeneralLogic;

import sx.blah.discord.handle.obj.IMessage;

public class MsgEvent {
    private IMessage messages;
    public MsgEvent(IMessage message) {
        this.messages = message;
    }
    public IMessage getData() {
        return messages;
    }
}
