package sky.mulley.DiscordConnect.Commands;

import sky.mulley.DiscordConnect.GeneralLogic.MsgEvent;
import sky.mulley.DiscordConnect.Modules.DiscordModule;

public abstract class BaseCommand {
    protected String commandName;
    protected String helpMessage = null;
    protected String Usage = null;
    protected boolean helpViewable = true;
    protected DiscordModule commandModule;
    public BaseCommand(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() { return commandName;}
    public String getHelpMessage() { return helpMessage;}
    public String getUsage() { return Usage;}
    public DiscordModule getModule() { return commandModule;}
    public boolean isHelpViewable() { return helpViewable;}

    public boolean executeCommand(MsgEvent e, String[] argArray) { return true; }

    public void setModule(DiscordModule module) {this.commandModule = module;}
}
