package net.minespree.pirate.cosmetics.types.superhats;

import net.minespree.pirate.crates.Rarity;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.minespree.pirate.PiratePlugin;
import net.minespree.pirate.cosmetics.types.SuperHatCosmetic;

import java.util.*;

public class BreastCancerSuperHat extends SuperHatCosmetic {

	private List<ItemStack> items = new ArrayList<>();
	private Map<UUID, Integer> current = new HashMap<>();

	public BreastCancerSuperHat() {
		super("shbreastcancer", new ItemStack(Material.WOOL, (short) 2));

		items.add(new ItemStack(Material.WOOL, (short) 2)); //Magenta Wool
		items.add(new ItemStack(Material.WOOL, (short) 6)); //Pink Wool
		items.add(new ItemStack(Material.STAINED_GLASS, (short) 6)); //Pink Glass
		items.add(new ItemStack(Material.STAINED_CLAY, (short) 6)); //Pink Stained Clay

		Bukkit.getScheduler().runTaskTimer(PiratePlugin.getPlugin(), this::tick, 1L, 1L);
	}

	public void tick(Player player) {
		player.getInventory().setHelmet((ItemStack) items.toArray()[current.getOrDefault(player.getUniqueId(), 1)]);

		if (current.containsKey(player.getUniqueId()) && current.get(player.getUniqueId()) + 1 >= items.toArray().length) {
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
