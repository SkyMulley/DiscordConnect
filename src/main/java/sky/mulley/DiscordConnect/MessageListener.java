package sky.mulley.DiscordConnect;

import sky.mulley.DiscordConnect.Commands.CommandCore;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class MessageListener {
    private CommandCore cc;
    public MessageListener(CommandCore cc) { this.cc =cc;}
    @EventSubscriber
    public void onMessageRecieved(MessageReceivedEvent event) {
        if(!cc.commandCheck(event)) {
            //Add more custom stuff here, not so sure
        }
    }
}
