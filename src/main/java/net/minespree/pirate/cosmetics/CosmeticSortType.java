package net.minespree.pirate.cosmetics;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Getter;
import net.minespree.wizard.gui.MultiPageGUI;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;
import java.util.Map;
import java.util.function.Function;

public enum CosmeticSortType {

    ALPHABETICAL_OWNED((player, items) -> {
        Map<String, Function<Player, ItemStack>> ownedCosmetics = Maps.newTreeMap(), unownedCosmetics = Maps.newTreeMap();
        for (Function<Player, ItemStack> function : items.keySet()) {
            if(function != null) {
                Cosmetic cosmetic = (Cosmetic) items.get(function);
                ItemStack item = function.apply(player);
                if (cosmetic != null && item != null) {
                    String name = item.getItemMeta().hasDisplayName() ? item.getItemMeta().getDisplayName() : item.getType().name();
                    if (cosmetic.has(player)) {
                        ownedCosmetics.put(name, function);
                    } else {
                        unownedCosmetics.put(name, function);
                    }
                }
            }
        }
        LinkedList<Function<Player, ItemStack>> orderedItems = Lists.newLinkedList();
        orderedItems.addAll(ownedCosmetics.values());
        orderedItems.addAll(unownedCosmetics.values());
        return orderedItems;
    });

    @Getter
    private MultiPageGUI.SortMethod method;

    CosmeticSortType(MultiPageGUI.SortMethod method) {
        this.method = method;
    }

}
