package net.minespree.pirate.cosmetics.games.thimble;

import net.minespree.pirate.cosmetics.Cosmetic;
import net.minespree.pirate.cosmetics.CosmeticType;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class MarkerCosmetic extends Cosmetic {

    public MarkerCosmetic(String id) {
        super(id, CosmeticType.TH_MARKER);
    }

    public abstract void build(Player player, Location location);

}
