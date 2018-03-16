package net.minespree.pirate.crates;

import lombok.Getter;
import net.minespree.feather.util.collections.WeightedTable;
import org.bukkit.inventory.ItemStack;

public class LootItem {

    @Getter
    private static WeightedTable<LootItem> items = new WeightedTable<>();

    private ItemStack itemStack;
    private Rarity rarity;

    public LootItem(ItemStack itemStack, Rarity rarity) {

        this.itemStack = itemStack;
        this.rarity = rarity;

        items.add(rarity.getWeight(), this);
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public static LootItem getRandomItem() {
        return items.getRandom(false, false).orElse(null);
    }
}