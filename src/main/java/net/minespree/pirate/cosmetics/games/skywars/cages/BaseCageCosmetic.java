package net.minespree.pirate.cosmetics.games.skywars.cages;

import net.minespree.pirate.cosmetics.games.skywars.CageCosmetic;
import net.minespree.pirate.cosmetics.games.skywars.CageShape;
import net.minespree.pirate.cosmetics.games.skywars.CageShapeType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;

import java.util.ArrayList;
import java.util.List;

public class BaseCageCosmetic extends CageCosmetic {

    private CageShape shape;
    private Material base, middle;
    private byte baseData, middleData;

    public BaseCageCosmetic(String id, CageShapeType type, Material base, byte baseData, Material middle, byte middleData) {
        super(id);

        this.shape = type.getShape();
        this.base = base;
        this.middle = middle;
        this.baseData = baseData;
        this.middleData = middleData;
    }

    @Override
    public List<Location> build(Location centre, BlockFace face) {
        List<Location> locations = new ArrayList<>();

        List<Location> base = shape.getTop(centre);
        List<Location> middle = shape.getMiddleRows(centre);

        base.addAll(shape.getBottom(centre));

        base.forEach(location -> {
            location.getBlock().setType(this.base);
            location.getBlock().setData(baseData);
        });
        middle.forEach(location -> {
            location.getBlock().setType(this.middle);
            location.getBlock().setData(middleData);

        });

        locations.addAll(base);
        locations.addAll(middle);
        return locations;
    }
}
