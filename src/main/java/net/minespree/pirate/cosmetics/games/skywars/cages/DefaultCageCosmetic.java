package net.minespree.pirate.cosmetics.games.skywars.cages;

import net.minespree.pirate.cosmetics.games.skywars.CageCosmetic;
import net.minespree.pirate.cosmetics.games.skywars.CageShapeType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;

import java.util.List;

public class DefaultCageCosmetic extends CageCosmetic {

    public DefaultCageCosmetic() {
        super("cg_def");
    }

    @Override
    public List<Location> build(Location centre, BlockFace face) {
        List<Location> blocks = CageShapeType.DIAMOND.getShape().getBlocks(centre);
        blocks.forEach(location -> location.getBlock().setType(Material.GLASS));
        return blocks;
    }
}
