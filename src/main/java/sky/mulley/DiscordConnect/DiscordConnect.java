package sky.mulley.DiscordConnect;

import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import sky.mulley.DiscordConnect.Commands.CommandCore;
import sky.mulley.DiscordConnect.GeneralLogic.BotInstance;
import sky.mulley.DiscordConnect.Listeners.Log4JAppender;
import sky.mulley.DiscordConnect.Listeners.MessageListener;
import sky.mulley.DiscordConnect.Listeners.ServerSync;
import sky.mulley.DiscordConnect.Modules.DCModule;
import sky.mulley.DiscordConnect.Modules.ModuleManager;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.io.File;

public class DiscordConnect extends JavaPlugin {
    private String token;
    private IDiscordClient client;
    private long textChannel;
    private long adminChannel;
    private long guild;
    private String BOT_PREFIX;
    private CommandCore cc;
    private ServerSync listener;
    private MessageListener ml;
    private boolean gotTextChannel = false;
    private boolean gotAdminChannel = false;
    private ModuleManager moduleManager = new ModuleManager();
    private int timeout;

    @Override
    public void onEnable() {
        loadConfig();
        cc = new CommandCore(BOT_PREFIX);
        try {
            client = buildDiscordClient(token);
            client.getDispatcher().registerListener(ml = new MessageListener(cc));
            client.login();
            Bukkit.getLogger().info("[DiscordConnect] We have Discord and are logged in!");
        } catch(Exception e) {
            Bukkit.getLogger().info("[DiscordConnect] Something went horribly wrong, maybe your token is invalid? "+e);
            gotTextChannel = false;
            Bukkit.getPluginManager().disablePlugin(this);
        }
        loadMoreConfig();
        getModuleManager().addModule(new DCModule());
        if(gotTextChannel) {
            BukkitScheduler scheduler = getServer().getScheduler();
            scheduler.scheduleSyncDelayedTask(this, new Runnable() {
                @Override
                public void run() {
                    ml.setChannel(client.getGuildByID(guild).getChannelByID(textChannel));
                    Emoji check = EmojiManager.getForAlias("white_check_mark");
                    client.getGuildByID(guild).getChannelByID(textChannel).sendMessage(check.getUnicode()+" **Server has started**");
                    getEvents();
                    if(gotAdminChannel) {
                        Logger log = (Logger) LogManager.getRootLogger();
                        log.addAppender(new Log4JAppender());
                        ml.setAdminconsole(client.getGuildByID(guild).getChannelByID(adminChannel));
                    }
                }
            }, 100L);
        }
    }

    @Override
    public void onDisable() {
        if(gotTextChannel) {
            Emoji octagonal = EmojiManager.getForAlias("octagonal_sign");
            client.getChannelByID(textChannel).sendMessage(octagonal.getUnicode()+" **Server has stopped**");
        }
        try {
            client.logout();
        } catch(Exception e) { }
    }

    private static IDiscordClient buildDiscordClient(String token) {
        return new ClientBuilder()
                .withToken(token)
                .withRecommendedShardCount()
                .build();
    }

    public CommandCore getCommandCore() { return cc;}

    public BotInstance getBotClient() { return new BotInstance(client);}

    public ModuleManager getModuleManager() { return moduleManager;}

    public Long getAdminConsole() { return adminChannel;}

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
                timeout = (int) this.getConfig().getConfigurationSection("Bot Timeout Amount (Leave this default unless you know what you're doing)").getValues(false).get("timeout");
                if(token.length()==0) {Bukkit.getLogger().info("[DiscordConnect] No token was found, shutting down"); Bukkit.getPluginManager().disablePlugin(this);}
                if(BOT_PREFIX.length()==0) {Bukkit.getLogger().info("[DiscordConnect] No Bot Prefix was found, setting it to !"); BOT_PREFIX="!";}
            }
        } catch(Exception e) {
            getLogger().info("[DiscordConnect] An issue has occured loading the config file: "+e);
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    private void loadMoreConfig() {
        gotAdminChannel = true;
        gotTextChannel = true;
        try {
            guild = Long.parseLong( (String) this.getConfig().getConfigurationSection("Minecraft Text Channel Guild ID (Leave blank if you're not using the server link)").getValues(false).get("mcguild")); } catch(Exception e) {
            Bukkit.getLogger().info("Failed to get Guild details from config (Could be empty or invalid, disabling MC Links): "+e);
            gotTextChannel = false;gotAdminChannel = false;textChannel=0; }
        try {
            textChannel = Long.parseLong( (String) this.getConfig().getConfigurationSection("Minecraft Text Channel ID (Leave blank to disable)").getValues(false).get("textchannel")); } catch(Exception e) {
            Bukkit.getLogger().info("Failed to get MC Channel details from config (Could be empty or invalid, disabling MC Chat): "+e);
            gotTextChannel = false; textChannel=0; }
        try {
            adminChannel = Long.parseLong(this.getConfig().getConfigurationSection("Minecraft Admin Channel ID (Leave blank to disable)").getValues(false).get("adminchannel").toString()); } catch (Exception e) {
            Bukkit.getLogger().info("Failed to get Admin Console details from config (Could be empty or invalid, disabling MC Admin Console)"+e);
            gotAdminChannel = false;}
    }

    private void getEvents() {
        listener = new ServerSync(this, client.getGuildByID(guild).getChannelByID(textChannel));
        getServer().getPluginManager().registerEvents(listener,this);
    }
}