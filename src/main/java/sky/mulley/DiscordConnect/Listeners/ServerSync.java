package sky.mulley.DiscordConnect.Listeners;

import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiManager;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import sky.mulley.DiscordConnect.DiscordConnect;
import sx.blah.discord.handle.obj.IChannel;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ServerSync implements org.bukkit.event.Listener {
    private DiscordConnect plugin;
    private IChannel channel;
    private Emoji plus = EmojiManager.getForAlias("heavy_plus_sign");
    private Emoji minus = EmojiManager.getForAlias("heavy_minus_sign");
    private Emoji skull = EmojiManager.getForAlias("skull");
    private Emoji trophy = EmojiManager.getForAlias("trophy");
    public ServerSync(DiscordConnect plugin, IChannel channel) {
        this.plugin = plugin;
        this.channel = channel;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if(getPlayerGroup(event.getPlayer()) != null) {channel.sendMessage("**"+plus.getUnicode()+"["+getPlayerGroup(event.getPlayer())+"] "+event.getPlayer().getName()+" joined the game**"); } else {
            channel.sendMessage("**"+plus.getUnicode()+event.getPlayer().getName()+" joined the game**");
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        if(getPlayerGroup(event.getPlayer()) != null) {channel.sendMessage("**"+minus.getUnicode()+"["+getPlayerGroup(event.getPlayer())+"] "+event.getPlayer().getName()+" left the game**"); } else {
            channel.sendMessage("**"+minus.getUnicode()+event.getPlayer().getName()+" left the game**");
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        channel.sendMessage("**"+skull.getUnicode()+event.getDeathMessage()+"**");
    }

    @EventHandler
    public void onPlayerTalk(AsyncPlayerChatEvent event) {
        if(getPlayerGroup(event.getPlayer()) != null) {channel.sendMessage("**["+getPlayerGroup(event.getPlayer())+"]** "+event.getPlayer().getName()+" » "+event.getMessage()); } else {
            channel.sendMessage("**["+event.getPlayer().getName()+"]** "+event.getMessage());
        }
    }

    @EventHandler
    public void onPlayerAdvancement(PlayerAdvancementDoneEvent event) {
        try {
            Object craftAdvancement = ((Object) event.getAdvancement()).getClass().getMethod("getHandle").invoke(event.getAdvancement());
            Object advancementDisplay = craftAdvancement.getClass().getMethod("c").invoke(craftAdvancement);
            boolean display = (boolean) advancementDisplay.getClass().getMethod("i").invoke(advancementDisplay);
            if (!display) return;
        } catch (NullPointerException e) {
            return;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        if (event.getAdvancement() == null || event.getAdvancement().getKey().getKey().contains("recipe/") || event.getPlayer() == null) return;
        String rawAdvancementName = event.getAdvancement().getKey().getKey();
        String advancementName = Arrays.stream(rawAdvancementName.substring(rawAdvancementName.lastIndexOf("/") + 1).toLowerCase().split("_"))
                .map(s -> s.substring(0, 1).toUpperCase() + s.substring(1))
                .collect(Collectors.joining(" "));
        if(getPlayerGroup(event.getPlayer()) != null) {channel.sendMessage("**"+trophy.getUnicode()+"["+getPlayerGroup(event.getPlayer())+"]"+event.getPlayer().getName()+" | Achivement Unlocked! ["+advancementName+"]**"); } else {
            channel.sendMessage("**"+trophy.getUnicode()+event.getPlayer().getName()+" | Achivement Unlocked! ["+advancementName+"]**");
        }
    }

    public String getPlayerGroup(Player player) {
        try {
            Permission permissionProvider = (net.milkbowl.vault.permission.Permission) Bukkit.getServer().getServicesManager().getRegistration(Class.forName("net.milkbowl.vault.permission.Permission")).getProvider();
            if (permissionProvider == null) {
                return null;
            }
            String group = permissionProvider.getPrimaryGroup(player).substring(0, 1).toUpperCase() + permissionProvider.getPrimaryGroup(player).substring(1);
            return group;
        } catch(Exception e) {
            return null;
        }
    }
}
