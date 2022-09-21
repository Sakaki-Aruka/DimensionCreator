package dimensioncreator.dimensioncreator;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class DimensionCreator extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("dc").setExecutor(new GeneratorCore());

    }

    @Override
    public void onDisable() {
        Bukkit.getServer().getLogger().info("See you worlds.");
    }
}
