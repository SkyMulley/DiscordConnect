package sky.mulley.DiscordConnect.Listeners;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;
import sky.mulley.DiscordConnect.DiscordConnect;

@Plugin(name = "Log4JAppender", category = "Core", elementType = "appender", printObject = true)
public class Log4JAppender extends AbstractAppender {
    private String message = "";
    private DiscordConnect main = (DiscordConnect) Bukkit.getServer().getPluginManager().getPlugin("DiscordConnect");
    public Log4JAppender() {
        super("Log4JAppender", null,
                PatternLayout.createDefaultLayout());
        setupTimer();
    }

    @Override
    public boolean isStarted() {
        return true;
    }

    @Override
    public void append(LogEvent e) {
        if(message.isEmpty()) {
            message = e.getMessage().getFormattedMessage();
        } else {
            if(message.length() + e.getMessage().getFormattedMessage().length() > 1999) {
                main.getBotClient().getData().getChannelByID(main.getAdminConsole()).sendMessage(message);
                message = "";
            }
            message = message + "\n" + e.getMessage().getFormattedMessage();
        }
    }

    private void setupTimer() {
        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.scheduleSyncRepeatingTask(main, new Runnable() {
            @Override
            public void run() {
                if(!message.isEmpty()) {
                    main.getBotClient().getData().getChannelByID(main.getAdminConsole()).sendMessage(message);
                    message = "";
                }
            }
        }, 200L, 100L);
    }
}