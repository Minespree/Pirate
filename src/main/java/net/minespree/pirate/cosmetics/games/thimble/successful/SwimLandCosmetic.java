package net.minespree.pirate.cosmetics.games.thimble.successful;

import net.minespree.pirate.cosmetics.games.thimble.LandCosmetic;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SwimLandCosmetic extends LandCosmetic {

    public SwimLandCosmetic() {
        super("sl_swim");
    }

    @Override
    public void land(Player player, Location location) {
        Bukkit.getOnlinePlayers().forEach(p -> p.playSound(p.getLocation(), Sound.SPLASH, 1F,1F));
    }
}
