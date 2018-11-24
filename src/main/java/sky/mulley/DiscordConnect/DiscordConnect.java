package sky.mulley.DiscordConnect;

import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import sky.mulley.DiscordConnect.Commands.CommandCore;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;

import java.io.File;

public class DiscordConnect extends JavaPlugin {
    private String token;
    private IDiscordClient client;
    private IChannel textChannel;
    private IChannel adminChannel;
    private IGuild guild;
    private String BOT_PREFIX;
    private CommandCore cc;
    private ServerSync listener;
    private MessageListener ml;
    private Boolean gotTextChannel;
    private Boolean gotAdminChannel;

    @Override
    public void onEnable() {
        loadConfig();
        cc = new CommandCore(BOT_PREFIX);
        client = buildDiscordClient(token);
        client.getDispatcher().registerListener(ml = new MessageListener(cc));
        client.login();
        loadMoreConfig();
        Bukkit.getLogger().info("[DiscordConnect] We have Discord and are logged in!");
        if(gotTextChannel=true) {
            listener = new ServerSync(this, textChannel);
            getServer().getPluginManager().registerEvents(listener,this);
            ml.setChannel(textChannel);
            Emoji check = EmojiManager.getForAlias("white_check_mark");
            guild.getChannelByID(textChannel.getLongID()).sendMessage(check+" Server has started!");
        }
    }

    @Override
    public void onDisable() {
        if(gotTextChannel=true) {
            Emoji octagonal = EmojiManager.getForAlias("octagonal_sign");
            guild.getChannelByID(textChannel.getLongID()).sendMessage(octagonal+" Server has stopped!");
        }
        if(client!=null) { client.logout();}
    }

    private static IDiscordClient buildDiscordClient(String token) {
        return new ClientBuilder()
                .withToken(token)
                .withRecommendedShardCount()
                .build();
    }

    public CommandCore getCommandCore() { return cc;}

    public IDiscordClient getBotClient() { return client;}

    private void loadConfig() {
        try {
            if(!getDataFolder().exists()) {
                getDataFolder().mkdirs();
            }
            File file = new File(getDataFolder(),"config.yml");
            if(!file.exists()) {
                getLogger().info("[DiscordConnect] Configuration not found, making a new one and shutting down.");
                saveDefaultConfig();
                getServer().getPluginManager().disablePlugin(this);
            } else {
                token = (String) this.getConfig().getConfigurationSection("Discord Bot Token").getValues(false).get("token");
                BOT_PREFIX = (String) this.getConfig().getConfigurationSection("Discord Bot Prefix").getValues(false).get("botprefix");
            }
        } catch(Exception e) {
            getLogger().info("[DiscordConnect] An issue has occured loading the config file: "+e);
        }
    }

    private void loadMoreConfig() {
        gotTextChannel = true;
        gotAdminChannel = true;
        try {
            guild = client.getGuildByID(Long.parseLong(this.getConfig().getConfigurationSection("Minecraft Text Channel Guild ID (Leave blank if you're not using the server link)").getValues(false).get("mcguild").toString())); } catch(Exception e) {
            Bukkit.getLogger().info("Failed to get Guild details from config (Could be empty or invalid, disabling MC Links): "+e);
            gotTextChannel = false;gotAdminChannel = false;textChannel=null; }
        try {
            textChannel = client.getGuildByID(guild.getLongID()).getChannelByID(Long.parseLong(this.getConfig().getConfigurationSection("Minecraft Text Channel ID (Leave blank to disable)").getValues(false).get("textchannel").toString())); } catch(Exception e) {
            Bukkit.getLogger().info("Failed to get MC Channel details from config (Could be empty or invalid, disabling MC Chat): "+e.getStackTrace().toString());
            gotTextChannel = false; textChannel=null; }
        try {
            adminChannel = client.getGuildByID(guild.getLongID()).getChannelByID(Long.parseLong(this.getConfig().getConfigurationSection("Minecraft Admin Channel ID (Leave blank to disable)").getValues(false).get("adminchannel").toString())); } catch (Exception e) {
            Bukkit.getLogger().info("Failed to get Admin Console details from config (Could be empty or invalid, disabling MC Admin Console)"+e);
            gotAdminChannel = false;}
    }
}