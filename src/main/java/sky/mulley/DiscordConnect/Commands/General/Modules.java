package sky.mulley.DiscordConnect.Commands.General;

import sky.mulley.DiscordConnect.Commands.BaseCommand;

public class Modules extends BaseCommand {
    public Modules () {
        super("Modules");
        helpMessage = "List all modules linked into DiscordConnect";
        Usage = "!modules <list/enable/disable>";
        helpViewable = true;
    }
}
