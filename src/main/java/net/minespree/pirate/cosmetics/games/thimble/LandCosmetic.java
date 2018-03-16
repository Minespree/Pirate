package net.minespree.pirate.cosmetics.games.thimble;

import net.minespree.pirate.cosmetics.Cosmetic;
import net.minespree.pirate.cosmetics.CosmeticType;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class LandCosmetic extends Cosmetic {

    public LandCosmetic(String id) {
        super(id, CosmeticType.TH_SUCCESSFUL_LAND);
    }

    public abstract void land(Player player, Location location);

}
