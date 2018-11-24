package sky.mulley.DiscordConnect;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import sky.mulley.DiscordConnect.Commands.MessageCore;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;

public class DiscordConnect extends JavaPlugin {
    private static String token;
    private IDiscordClient client;
    private static IChannel textChannel;
    private static IChannel adminChannel;

    @Override
    public void onEnable() {
        //Get all the config file grabs here
        client = buildDiscordClient(token);
        client.getDispatcher().registerListener(new MessageCore());
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
}
