package sky.mulley.DiscordConnect;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import sky.mulley.DiscordConnect.Commands.CommandCore;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;

public class DiscordConnect extends JavaPlugin {
    private String token;
    private IDiscordClient client;
    private IChannel textChannel;
    private IChannel adminChannel;
    private IGuild guild;
    private String BOT_PREFIX;
    private CommandCore cc;

    @Override
    public void onEnable() {
        //Get all the config file grabs here
        cc = new CommandCore(BOT_PREFIX);
        client = buildDiscordClient(token);
        client.getDispatcher().registerListener(new MessageListener(cc));
        client.login();
        Bukkit.getLogger().info("[DiscordConnect] We have Discord and are logged in!");
        checkStatus();
    }

    @Override
    public void onDisable() {
        client.logout();
    }

    private static IDiscordClient buildDiscordClient(String token) {
        return new ClientBuilder()
                .withToken(token)
                .withRecommendedShardCount()
                .build();
    }

    public CommandCore getCommandCore() { return cc;}

    public IDiscordClient getBotClient() { return client;}

    private void checkStatus() {
        Boolean statusNotMet = true;
        Boolean adminFound = false;
        Boolean guildFound = false;
        for(IGuild guilds : client.getGuilds()) { if(guilds.equals(guild)) { guildFound = true;} }
        if(guildFound = true) { for(IChannel channel : client.getGuildByID(guild.getLongID()).getChannels()) { if(channel.equals(textChannel)) { statusNotMet = false; } if (channel.equals(adminChannel)) { adminFound = true; } } }
        if(statusNotMet=true) { Bukkit.getLogger().info("[DiscordConnect] The specified channel for Minecraft Chat was not found! Disabling this function"); textChannel = null;}
        if(adminFound=false) { Bukkit.getLogger().info("[DiscordConnect] No Admin Console Channel was found, disabling this function."); adminChannel = null;}
    }
}
