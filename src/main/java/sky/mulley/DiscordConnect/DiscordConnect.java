package sky.mulley.DiscordConnect;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import sky.mulley.DiscordConnect.Commands.CommandCore;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;

public class DiscordConnect extends JavaPlugin {
    private String token;
    private IDiscordClient client;
    private IChannel textChannel;
    private IChannel adminChannel;
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
}
