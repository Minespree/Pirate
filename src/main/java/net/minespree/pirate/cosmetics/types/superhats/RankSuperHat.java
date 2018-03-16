package net.minespree.pirate.cosmetics.types.superhats;

import net.minespree.pirate.PiratePlugin;
import net.minespree.pirate.cosmetics.types.SuperHatCosmetic;
import net.minespree.pirate.crates.Rarity;
import net.minespree.wizard.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public abstract class RankSuperHat extends SuperHatCosmetic {

    private Map<UUID, List<Item>> items = new ConcurrentHashMap<>();

    private final Material material;
    private final int amount;

    public RankSuperHat(String id, ItemStack hat, Material material, int amount) {
        super(id, hat);

        this.material = material;
        this.amount = amount;

        Bukkit.getScheduler().runTaskTimer(PiratePlugin.getPlugin(), this::tick, 1L, 1L);
    }

    @Override
    public void tick(Player player) {
        if(!items.containsKey(player.getUniqueId())) {
            items.put(player.getUniqueId(), new ArrayList<>());
            for (int i = 0; i < amount - 1; i++) {
                drop(player);
            }
        }
        drop(player);
        Item item = items.get(player.getUniqueId()).get(0);
        items.get(player.getUniqueId()).remove(item);
        item.remove();
    }

    private void drop(Player player) {
        Item item = player.getWorld().dropItem(player.getEyeLocation().add(0, 0.3, 0),
                new ItemBuilder(material).lore(UUID.randomUUID().toString()).build(player));
        item.setPickupDelay(100);
        items.get(player.getUniqueId()).add(item);
    }

    @Override
    public void unequip(Player player, boolean save) {
        super.unequip(player, save);

        if(items.containsKey(player.getUniqueId())) {
            items.get(player.getUniqueId()).forEach(Entity::remove);
        }
    }

    @Override
    public void onQuit(Player player) {
        if(items.containsKey(player.getUniqueId())) {
            items.get(player.getUniqueId()).forEach(Entity::remove);
            items.remove(player.getUniqueId());
        }
    }

}
