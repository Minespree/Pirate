package net.minespree.pirate.cosmetics.games.thimble.markers;

import net.minespree.pirate.cosmetics.games.thimble.MarkerCosmetic;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

public class FlowerMarkerCosmetic extends MarkerCosmetic {

    private MaterialData data;

    public FlowerMarkerCosmetic(String id, MaterialData data) {
        super(id);

        this.data = data;
    }

    @Override
    public void build(Player player, Location location) {
        location.getBlock().setType(data.getItemType());
        location.getBlock().setData(data.getData());
    }
}
