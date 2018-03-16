package net.minespree.pirate;

import lombok.Getter;
import net.minespree.feather.command.system.CommandManager;
import net.minespree.pirate.command.TestPirateCommands;
import net.minespree.pirate.cosmetics.Cosmetic;
import net.minespree.pirate.cosmetics.CosmeticManager;
import net.minespree.pirate.crates.CrateActivation;
import net.minespree.pirate.crates.CrateRegistration;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class PiratePlugin extends JavaPlugin {

    @Getter
    private static PiratePlugin plugin;

    public void onEnable() {
        plugin = this;

        CommandManager.getInstance().registerClass(TestPirateCommands.class);
        CommandManager.getInstance().registerClass(CrateRegistration.class);

        Bukkit.getServer().getPluginManager().registerEvents(new CrateActivation(), this);

        CrateRegistration.loadCrateLocations();
    }

    @Override
    public void onDisable() {
        for (Cosmetic cosmetic : CosmeticManager.getCosmeticManager().getCosmetics().values()) {
            cosmetic.shutdown();
        }
    }

}
