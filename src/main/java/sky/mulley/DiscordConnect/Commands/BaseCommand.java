package sky.mulley.DiscordConnect.Commands;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public abstract class BaseCommand {
    protected String commandName;
    protected String helpMessage;
    protected String Usage;
    protected boolean helpViewable;

    public String getCommandName() { return commandName;}
    public String getHelpMessage() { return helpMessage;}
    public String getUsage() { return Usage;}
    public boolean isHelpViewable() { return helpViewable;}

    public boolean executeCommand(MessageReceivedEvent e, String[] argArray) { return true; }
}
