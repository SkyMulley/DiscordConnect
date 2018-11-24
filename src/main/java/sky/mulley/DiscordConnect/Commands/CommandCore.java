package sky.mulley.DiscordConnect.Commands;

import org.bukkit.Bukkit;
import sky.mulley.DiscordConnect.Commands.General.Help;
import sky.mulley.DiscordConnect.Commands.General.Ping;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;

public class CommandCore {
    private List<BaseCommand> commandList = new ArrayList<>();
    private String BOT_PREFIX;
    private boolean hasStarted = false;
    public CommandCore(String BOT_PREFIX) { this.BOT_PREFIX = BOT_PREFIX; }

    public void addCommand(BaseCommand command) {commandList.add(command);}
    public void removeCommand(BaseCommand command) {commandList.remove(command);}
    public List<BaseCommand> getCommands() {return commandList;}

    public boolean commandCheck(MessageReceivedEvent event) {
        if(!hasStarted) { setupCommands(); hasStarted = false; }
        String[] argArray = event.getMessage().getContent().split(" ");
        if (argArray.length == 0 || !argArray[0].startsWith(BOT_PREFIX)) {
            return false;
        }
        String commandStr = argArray[0].substring(BOT_PREFIX.length());
        for (BaseCommand command : commandList) {
            if(commandStr.toLowerCase().contains(command.getCommandName().toLowerCase())) {
                Bukkit.getLogger().info("[DiscordConnect] " + event.getAuthor().getName() + " ran command " + command.getCommandName());
                command.executeCommand(event, argArray);
                return true;
            }
        }
        return false;
    }

    private void setupCommands() {
        addCommand(new Ping());
        addCommand(new Help(commandList));
    }
}
