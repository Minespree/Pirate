package net.minespree.pirate.cosmetics.types.superhats;

import net.minespree.pirate.crates.Rarity;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.minespree.pirate.PiratePlugin;
import net.minespree.pirate.cosmetics.types.SuperHatCosmetic;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LeafSuperHat extends SuperHatCosmetic {

	private final short[] types = new short[]{0, 1, 2, 3};
	private Map<UUID, Integer> current = new HashMap<>();

	public LeafSuperHat() {
		super("shleaf", new ItemStack(Material.LEAVES, 1, (short) 0));

		Bukkit.getScheduler().runTaskTimer(PiratePlugin.getPlugin(), this::tick, 5L, 5L);
	}

	public void tick(Player player) {
		player.getInventory().setHelmet(new ItemStack(Material.STAINED_GLASS, 1, types[current.getOrDefault(player.getUniqueId(), 1)]));

		if (current.containsKey(player.getUniqueId()) && current.get(player.getUniqueId()) + 1 >= types.length) {
			current.put(player.getUniqueId(), 0);
		} else {
			current.put(player.getUniqueId(), current.getOrDefault(player.getUniqueId(), 1) + 1);
		}
	}

	@Override
	public void onQuit(Player player) {
		current.remove(player.getUniqueId());
	}
}
