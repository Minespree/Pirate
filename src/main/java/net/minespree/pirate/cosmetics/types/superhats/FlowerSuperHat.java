package net.minespree.pirate.cosmetics.types.superhats;

import net.minespree.pirate.PiratePlugin;
import net.minespree.pirate.cosmetics.types.SuperHatCosmetic;
import net.minespree.wizard.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class FlowerSuperHat extends SuperHatCosmetic {

	private final short[] flowers = new short[]{1, 2, 3, 4, 5, 6, 7, 8};

	private Map<UUID, List<Item>> items = new ConcurrentHashMap<>();
	private Map<UUID, Integer> current = new HashMap<>();

	public FlowerSuperHat() {
		super("shflower", new ItemStack(Material.LEAVES, 1));

		Bukkit.getScheduler().runTaskTimer(PiratePlugin.getPlugin(), this::tick, 1L, 1L);
	}

	public void tick(Player player) {
		if (!items.containsKey(player.getUniqueId())) {
			items.put(player.getUniqueId(), new ArrayList<>());

			for (int i = 0; i < 3; i++) {
				dropFlower(player);
			}
		}

		dropFlower(player);

		Item item = items.get(player.getUniqueId()).get(0);
		items.get(player.getUniqueId()).remove(item);
		item.remove();
	}

	private void dropFlower(Player player) {
		int index = current.getOrDefault(player.getUniqueId(), 1);
		short flowerData = flowers[index];

		Item item = player.getWorld().dropItem(player.getEyeLocation().add(0, 0.3, 0),
				new ItemBuilder(Material.RED_ROSE).durability(flowerData).lore(UUID.randomUUID().toString()).build(player));

		item.setPickupDelay(100);
		items.get(player.getUniqueId()).add(item);

		if (current.containsKey(player.getUniqueId()) && current.get(player.getUniqueId()) + 1 >= flowers.length) {
			current.put(player.getUniqueId(), 0);
		} else {
			current.put(player.getUniqueId(), index + 1);
		}
	}

	@Override
	public void unequip(Player player, boolean save) {
		super.unequip(player, save);

		if (items.containsKey(player.getUniqueId())) {
			items.get(player.getUniqueId()).forEach(Entity::remove);
		}
	}

	@Override
	public void onQuit(Player player) {
		current.remove(player.getUniqueId());

		if (items.containsKey(player.getUniqueId())) {
			items.get(player.getUniqueId()).forEach(Entity::remove);
			items.remove(player.getUniqueId());
		}
	}
}
