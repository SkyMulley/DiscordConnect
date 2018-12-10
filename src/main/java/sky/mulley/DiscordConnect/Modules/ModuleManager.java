package sky.mulley.DiscordConnect.Modules;

import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    private List<DiscordModule> modules;
    public ModuleManager() {
        this.modules = new ArrayList<>();
    }

    public void addModule(DiscordModule module) {
        if(modules.contains(module)) {
            Bukkit.getLogger().info("A duplicate module of "+module.getName()+" attempted to be added!");
        } else {
            modules.add(module);
        }
    }

    public List<DiscordModule> getModules() {return modules;}
}
