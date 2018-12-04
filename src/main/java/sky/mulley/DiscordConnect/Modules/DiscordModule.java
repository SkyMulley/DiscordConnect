package sky.mulley.DiscordConnect.Modules;

import org.bukkit.Bukkit;
import sky.mulley.DiscordConnect.Commands.BaseCommand;
import sky.mulley.DiscordConnect.DiscordConnect;

import java.util.List;

public abstract class DiscordModule {
    private String name;
    private String author;
    private DiscordConnect main = (DiscordConnect) Bukkit.getPluginManager().getPlugin("DiscordConnect");
    public DiscordModule(String name, String author) {
        this.name = name;
        this.author = author;
    }

    public void addCommand(BaseCommand command) {
        main.getCommandCore().addCommand(command);
    }
    public void removeCommand(BaseCommand command) {
        main.getCommandCore().removeCommand(command);
    }
    public List<BaseCommand> getCommands() {
        return main.getCommandCore().getCommands();
    }
    public String getName() {
        return name;
    }
    public String getAuthor() {
        return author;
    }
}
