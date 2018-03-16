package net.minespree.pirate.cosmetics.types.superhats;

import net.minespree.pirate.crates.Rarity;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class GoldSuperHat extends RankSuperHat {

    public GoldSuperHat() {
        super("shgold", new ItemStack(Material.GOLD_BLOCK), Material.GOLD_INGOT, 4);
    }
}
