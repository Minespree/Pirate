package net.minespree.pirate.cosmetics.games.skywars;

import net.minespree.pirate.cosmetics.Cosmetic;
import net.minespree.pirate.cosmetics.CosmeticType;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;

import java.util.List;

public abstract class CageCosmetic extends Cosmetic {

    public CageCosmetic(String id) {
        super(id, CosmeticType.SW_CAGE);
    }

    public abstract List<Location> build(Location centre, BlockFace direction);
}
