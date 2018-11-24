package sky.mulley.DiscordConnect;

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
        if(gotTextChannel=true) {listener = new ServerSync(this, textChannel); getServer().getPluginManager().registerEvents(listener,this); ml.setChannel(textChannel);}
    }

    @Override
    public void onDisable() {
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
                this.getConfig().createSection("Discord Bot Token").set("token","");
                this.getConfig().createSection("Discord Bot Prefix").set("botprefix","");
                this.getConfig().createSection("Minecraft Text Channel ID (Leave blank to disable)").set("textchannel",0);
                this.getConfig().createSection("Minecraft Admin Channel ID (Leave blank to disable)").set("adminchannel",0);
                this.getConfig().createSection("Minecraft Text Channel Guild ID (Leave blank if you're not using the server link)").set("mcguild",0);
                saveConfig();
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
        try { long guildl = (Integer) this.getConfig().getConfigurationSection("Minecraft Text Channel Guild ID (Leave blank if you're not using the server link)").getValues(false).get("mcguild");
            guild = client.getGuildByID(guildl); } catch(Exception e) { gotTextChannel = false;gotAdminChannel = false; }
        try {long textChannell = (Integer) this.getConfig().getConfigurationSection("Minecraft Text Channel ID (Leave blank to disable)").getValues(false).get("textchannel");
            textChannel = guild.getChannelByID(textChannell); } catch(Exception e) { gotTextChannel = false; textChannel=null; }
        try { long adminChannell = (Integer) this.getConfig().getConfigurationSection("Minecraft Admin Channel ID (Leave blank to disable)").getValues(false).get("adminchannel");
            adminChannel = guild.getChannelByID(adminChannell); } catch (Exception e) { gotAdminChannel = false;}
    }
}
