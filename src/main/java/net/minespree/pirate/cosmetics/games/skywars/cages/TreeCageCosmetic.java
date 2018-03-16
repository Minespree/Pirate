package net.minespree.pirate.cosmetics.games.skywars.cages;

import com.google.common.collect.Lists;
import net.minespree.pirate.cosmetics.games.skywars.CageCosmetic;
import net.minespree.pirate.cosmetics.games.skywars.CageShape;
import net.minespree.pirate.cosmetics.games.skywars.CageShapeType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;

import java.util.List;

public class TreeCageCosmetic extends CageCosmetic {

    public TreeCageCosmetic() {
        super("cg_tree");
    }

    @Override
    public List<Location> build(Location centre, BlockFace face) {
        List<Location> locations = Lists.newArrayList();

        centre.getBlock().setType(Material.LOG);
        centre.getBlock().setData((byte) 12);

        CageShape shape = CageShapeType.DIAMOND.getShape();
        Location top = centre.clone().add(0, 4, 0);
        List<Location> row3 = shape.getMiddle(centre, 3);
        List<Location> row2 = shape.getMiddle(centre, 2);
        List<Location> row1 = shape.getMiddle(centre, 1);

        top.getBlock().setType(Material.LEAVES);
        row3.forEach(location -> location.getBlock().setType(Material.LEAVES));
        row2.forEach(location -> location.getBlock().setType(Material.BARRIER));
        row1.forEach(location -> location.getBlock().setType(Material.BARRIER));

        locations.add(centre);
        locations.add(top);
        locations.addAll(row3);
        locations.addAll(row2);
        locations.addAll(row1);

        return locations;
    }
}
