package sky.mulley.DiscordConnect.Modules;

import sky.mulley.DiscordConnect.Commands.General.Help;
import sky.mulley.DiscordConnect.Commands.General.Ping;
import sky.mulley.DiscordConnect.Commands.General.Status;

public class DCModule extends DiscordModule {
    public DCModule() {
        super("DCModule","Sky");
        addCommand(new Ping());
        addCommand(new Help(getCommands()));
        addCommand(new Status());
    }
}
