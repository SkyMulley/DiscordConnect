package sky.mulley.DiscordConnect.Modules;

import org.bukkit.Bukkit;
import sky.mulley.DiscordConnect.Commands.General.*;
import sky.mulley.DiscordConnect.DiscordConnect;

public class DCModule extends DiscordModule {
    private DiscordConnect main = (DiscordConnect) Bukkit.getPluginManager().getPlugin("DiscordConnect");
    public DCModule() {
        super("DCModule","Sky");
        addCommand(new Ping());
        addCommand(new Status());
        addCommand(new Info());
        addCommand(new Modules(main.getModuleManager().getModules()));
        addCommand(new Help(getCommands()));
    }
}
