package net.minespree.pirate.cosmetics.types.superhats;

import net.minespree.pirate.PiratePlugin;
import net.minespree.pirate.cosmetics.types.SuperHatCosmetic;
import net.minespree.pirate.crates.Rarity;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RainbowSuperHat extends SuperHatCosmetic {

    private Map<UUID, Integer> current = new HashMap<>();
    private short[] colours = new short[] { 14,1,4,5,11,2,10 };

    public RainbowSuperHat() {
        super("shrainbow", new ItemStack(Material.STAINED_GLASS, 1, (short) 14));

        Bukkit.getScheduler().runTaskTimer(PiratePlugin.getPlugin(), this::tick, 5L, 5L);
    }

    public void tick(Player player) {
        player.getInventory().setHelmet(new ItemStack(Material.STAINED_GLASS, 1, colours[current.getOrDefault(player.getUniqueId(), 1)]));
        if (current.containsKey(player.getUniqueId()) && current.get(player.getUniqueId()) + 1 >= colours.length)
            current.put(player.getUniqueId(), 0);
        else current.put(player.getUniqueId(), current.getOrDefault(player.getUniqueId(), 1) + 1);
    }

    @Override
    public void onQuit(Player player) {
        current.remove(player.getUniqueId());
    }
}
