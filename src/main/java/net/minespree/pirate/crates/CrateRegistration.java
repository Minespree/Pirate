package net.minespree.pirate.crates;

import net.minespree.feather.command.system.annotation.Command;
import net.minespree.feather.player.rank.Rank;
import net.minespree.pirate.PiratePlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

public class CrateRegistration {

    public static Set<Location> chestLocations = new HashSet<>();
    private static List<Map<String, Object>> serializedLocations = new ArrayList<>();

    private static FileConfiguration config = PiratePlugin.getPlugin().getConfig();

    @Command(names = "register crate", requiredRank = Rank.ADMIN, hideFromHelp = true)
    public static void register(Player player) {

        player.sendMessage(chestLocations.add(player.getLocation().getBlock().getLocation()) ? "Added Location" : "¯\\_(ツ)_/¯");

        chestLocations.forEach(location -> serializedLocations.add(location.getBlock().getLocation().serialize()));

        config.set("crate.locations", serializedLocations);
    }

    public static void loadCrateLocations() {

        List<Map<String, Object>> serializedLocations = (List) config.get("crate.locations");

        if (serializedLocations != null) {
            serializedLocations.forEach(stringObjectMap -> chestLocations.add(Location.deserialize(stringObjectMap).getBlock().getLocation()));
            CrateRegistration.serializedLocations = serializedLocations;
        }
    }
}
