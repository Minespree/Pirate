package net.minespree.pirate.cosmetics.games.thimble.successful;

import net.minespree.babel.Babel;
import net.minespree.pirate.PiratePlugin;
import net.minespree.pirate.cosmetics.games.thimble.LandCosmetic;
import net.minespree.wizard.floatingtext.types.PublicFloatingText;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.concurrent.ThreadLocalRandom;

public class YayLandCosmetic extends LandCosmetic {

    public YayLandCosmetic() {
        super("sl_yay");
    }

    @Override
    public void land(Player player, Location location) {
        Location hologramLoc = location.clone().add(0.5, 0, 0.5);
        PublicFloatingText hologram = new PublicFloatingText(hologramLoc);
        StringBuilder builder = new StringBuilder("&a&lY");
        for (int i = 0; i < ThreadLocalRandom.current().nextInt(4, 9); i++) {
            builder.append("a");
        }
        builder.append("y!");
        hologram.setText(Babel.messageStatic(ChatColor.translateAlternateColorCodes('&', builder.toString())));
        hologram.show();
        Bukkit.getOnlinePlayers().forEach(p -> p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 1F, 0.5F));
        Bukkit.getScheduler().scheduleSyncDelayedTask(PiratePlugin.getPlugin(), hologram::remove, 60L);
    }
}
