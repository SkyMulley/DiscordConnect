package sky.mulley.DiscordConnect.Listeners;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.bukkit.Bukkit;
import sky.mulley.DiscordConnect.DiscordConnect;

@Plugin(name = "Log4JAppender", category = "Core", elementType = "appender", printObject = true)
public class Log4JAppender extends AbstractAppender {
    public Log4JAppender() {
        super("Log4JAppender", null,
                PatternLayout.createDefaultLayout());
    }

    @Override
    public boolean isStarted() {
        return true;
    }

    @Override
    public void append(LogEvent e) {
        DiscordConnect main = (DiscordConnect) Bukkit.getServer().getPluginManager().getPlugin("DiscordConnect");
        main.getBotClient().getData().getChannelByID(main.getAdminConsole()).sendMessage(e.getMessage().toString());
    }
}