package net.minespree.pirate.cosmetics.games.thimble;

import net.minespree.pirate.cosmetics.Cosmetic;
import net.minespree.pirate.cosmetics.CosmeticType;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class LandBlockCosmetic extends Cosmetic {

    private MaterialData data;

    public LandBlockCosmetic(String id, MaterialData data) {
        super(id, CosmeticType.TH_LAND_BLOCK);

        this.data = data;
    }

    public ItemStack build() {
        return new ItemStack(data.getItemType(), 1, data.getData());
    }

    public void build(Location location) {
        location.getBlock().setType(data.getItemType());
        location.getBlock().setData(data.getData());
    }

}
