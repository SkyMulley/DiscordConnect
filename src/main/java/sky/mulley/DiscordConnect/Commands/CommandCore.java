package sky.mulley.DiscordConnect.Commands;

import org.bukkit.Bukkit;
import sky.mulley.DiscordConnect.Commands.General.Info;
import sky.mulley.DiscordConnect.DiscordConnect;
import sky.mulley.DiscordConnect.GeneralLogic.MsgEvent;

import java.util.ArrayList;
import java.util.List;

public class CommandCore {
    private List<BaseCommand> commandList = new ArrayList<>();
    private String BOT_PREFIX;
    private boolean hasStarted = false;
    public CommandCore(String BOT_PREFIX) {
        this.BOT_PREFIX = BOT_PREFIX;
    }

    public void addCommand(BaseCommand command) {commandList.add(command);}
    public void removeCommand(BaseCommand command) {commandList.remove(command);}
    public List<BaseCommand> getCommands() {return commandList;}

    public boolean commandCheck(MsgEvent event) {
        String[] argArray = event.getData().getContent().split(" ");
        if (argArray.length == 0 || !argArray[0].startsWith(BOT_PREFIX)) {
            return false;
        }
        String commandStr = argArray[0].substring(BOT_PREFIX.length());
        for (BaseCommand command : commandList) {
            if(commandStr.toLowerCase().contains(command.getCommandName().toLowerCase())) {
                Bukkit.getLogger().info("[DiscordConnect] " + event.getData().getAuthor().getName() + " ran command " + command.getCommandName());
                command.executeCommand(event, argArray);
                return true;
            }
        }
        return false;
    }
}
