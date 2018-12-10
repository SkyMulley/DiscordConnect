package sky.mulley.DiscordConnect.Modules;

import org.bukkit.Bukkit;
import sky.mulley.DiscordConnect.Commands.BaseCommand;
import sky.mulley.DiscordConnect.DiscordConnect;

import java.util.ArrayList;
import java.util.List;

public abstract class DiscordModule {
    private String name;
    private String author;
    private boolean enabled = true;
    private DiscordConnect main = (DiscordConnect) Bukkit.getPluginManager().getPlugin("DiscordConnect");
    public DiscordModule(String name, String author) {
        this.name = name;
        this.author = author;
    }

    public void addCommand(BaseCommand command) {
        command.setModule(this);
        main.getCommandCore().addCommand(command);
    }
    public void removeCommand(BaseCommand command) {
        main.getCommandCore().removeCommand(command);
    }
    public void setState(boolean bool) { this.enabled = bool;}

    public List<BaseCommand> getCommands() {
        return main.getCommandCore().getCommands();
    }
    public String getName() {
        return name;
    }
    public String getAuthor() {
        return author;
    }
    public boolean getState() { return enabled;}
}
