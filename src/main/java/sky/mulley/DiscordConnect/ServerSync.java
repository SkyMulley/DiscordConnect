package sky.mulley.DiscordConnect;

import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import sx.blah.discord.handle.obj.IChannel;

public class ServerSync implements org.bukkit.event.Listener {
    private DiscordConnect plugin;
    private IChannel channel;
    private Emoji plus = EmojiManager.getForAlias("heavy_plus_sign");
    private Emoji minus = EmojiManager.getForAlias("heavy_minus_sign");
    private Emoji skull = EmojiManager.getForAlias("skull");
    public ServerSync(DiscordConnect plugin, IChannel channel) {
        this.plugin = plugin;
        this.channel = channel;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        //channel.sendMessage(plus+event.getJoinMessage());
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        //channel.sendMessage(minus+event.getQuitMessage());
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        //channel.sendMessage(skull+event.getDeathMessage());
    }
}
