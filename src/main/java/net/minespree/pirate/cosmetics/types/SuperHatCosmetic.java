package net.minespree.pirate.cosmetics.types;

import net.minespree.pirate.cosmetics.CosmeticType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public abstract class SuperHatCosmetic extends HatCosmetic {

    public SuperHatCosmetic(String id, ItemStack item) {
        super(id, CosmeticType.SUPER_HAT, item);
    }

    public abstract void tick(Player player);

    public void tick() {
        for (UUID uuid : equipped) {
            Player player = Bukkit.getPlayer(uuid);
            if(player != null && player.isOnline()) {
                tick(player);
            }
        }
    }

}
