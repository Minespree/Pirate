package net.minespree.pirate.cosmetics.games.skywars.cages;

import com.google.common.collect.Lists;
import net.minespree.pirate.cosmetics.games.skywars.CageCosmetic;
import net.minespree.pirate.cosmetics.games.skywars.CageShape;
import net.minespree.pirate.cosmetics.games.skywars.CageShapeType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.material.Pumpkin;

import java.util.Arrays;
import java.util.List;

public class Halloween17CageCosmetic extends CageCosmetic {

    public Halloween17CageCosmetic() {
        super("cg_halloween2017");
    }

    @Override
    public List<Location> build(Location centre, BlockFace face) {
        CageShape shape = CageShapeType.DIAMOND.getShape();
        List<Location> locations = Lists.newArrayList();
        List<Location> middle = shape.getMiddleRows(centre);

        locations.addAll(setPumpkins(centre, CageShape.BOTTOM));
        locations.addAll(setPumpkins(centre, CageShape.TOP));

        middle.forEach(location -> {
            location.getBlock().setType(Material.STAINED_GLASS);
            location.getBlock().setData((byte) 1);
        });
        locations.addAll(middle);
        return locations;
    }

    private List<Location> setPumpkins(Location centre, int height) {
        Location base = centre.clone().add(0, height, 0);
        setPumpkinBlock(base, BlockFace.NORTH);
        Location south = base.clone().subtract(0, 0, 1);
        setPumpkinBlock(south, BlockFace.SOUTH);
        Location west = base.clone().add(1, 0, 0);
        setPumpkinBlock(west, BlockFace.WEST);
        Location north = base.clone().add(0, 0, 1);
        setPumpkinBlock(north, BlockFace.NORTH);
        Location east = base.clone().subtract(1, 0, 0);
        setPumpkinBlock(east, BlockFace.EAST);
        return Arrays.asList(base, north, east, south, west);
    }

    private void setPumpkinBlock(Location location, BlockFace face) {
        Block block = location.getBlock();
        block.setType(Material.PUMPKIN);
        BlockState state = block.getState();
        Pumpkin pumpkin = (Pumpkin) state.getData();
        pumpkin.setFacingDirection(face);
        state.update();
    }

}
