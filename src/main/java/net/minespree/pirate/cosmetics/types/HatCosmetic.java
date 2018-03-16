package net.minespree.pirate.cosmetics.types;

import com.google.common.collect.Iterables;
import lombok.Getter;
import net.minespree.pirate.cosmetics.Cosmetic;
import net.minespree.pirate.cosmetics.CosmeticManager;
import net.minespree.pirate.cosmetics.CosmeticPlayer;
import net.minespree.pirate.cosmetics.CosmeticType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HatCosmetic extends Cosmetic {

    @Getter
    private final ItemStack item;

    public HatCosmetic(String id, ItemStack item) {
        this(id, CosmeticType.HAT, item);
    }

    public HatCosmetic(String id, CosmeticType type, ItemStack item) {
        super(id, type);

        this.item = item;
    }

    @Override
    public void equip(Player player, boolean save) {
        if(save) {
            CosmeticManager manager = CosmeticManager.getCosmeticManager();
            CosmeticPlayer cp = manager.getPlayer(player);

            Iterables.concat(
                manager.getCosmeticsByType(CosmeticType.HAT),
                manager.getCosmeticsByType(CosmeticType.SUPER_HAT)
            ).forEach(cosmetic -> {
                if (cp.isEquipped(cosmetic.getId())) {
                    cosmetic.unequip(player, true);
                }
            });

            cp.setEquipped(id, true);
        }

        equipped.add(player.getUniqueId());
        player.getInventory().setHelmet(item);
    }

    @Override
    public void unequip(Player player, boolean save) {
        super.unequip(player, save);

        player.getInventory().setHelmet(null);
    }
}
