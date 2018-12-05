package sky.mulley.DiscordConnect.Commands.General;

import sky.mulley.DiscordConnect.Commands.BaseCommand;
import sky.mulley.DiscordConnect.GeneralLogic.MsgEvent;
import sx.blah.discord.handle.obj.IMessage;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Ping extends BaseCommand {
    public Ping () {
        commandName = "Ping";
        helpMessage = "Find out the latency between the server and client";
        Usage = "!ping";
        helpViewable = true;
    }

    @Override
    public boolean executeCommand(MsgEvent event, String[] argArray) {
        LocalDateTime sentTime = LocalDateTime.ofInstant(event.getData().getTimestamp(), ZoneId.systemDefault());
        IMessage probe = event.getData().getChannel().sendMessage("Waiting for reply..");
        LocalDateTime repliedTime = LocalDateTime.ofInstant(probe.getTimestamp(),ZoneId.systemDefault());
        long ping = Duration.between(sentTime,repliedTime).toMillis();
        probe.edit(String.format("Pong! %s (%d ms)",event.getData().getAuthor().mention(),ping));
        return true;
    }
}
