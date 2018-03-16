package net.minespree.pirate.cosmetics.types.gadgets;

import net.minespree.pirate.PiratePlugin;
import net.minespree.pirate.cosmetics.types.GadgetCosmetic;
import net.minespree.pirate.cosmetics.types.gadgets.events.GadgetActionEvent;
import net.minespree.pirate.crates.Rarity;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PaintballGadget extends GadgetCosmetic {

	private final Random random = new Random();
	private final List<BlockState> toRevert = new ArrayList<>();

	public PaintballGadget() {
		super("gpaintballgun", 10000);

		ammo = new GadgetAmmo();
	}

	@Override
	public void initialize() {
		super.initialize();
		super.registerLootItem(Rarity.COMMON);
	}

	public void use(Player player) {
		Snowball snowball = (Snowball) player.getWorld().spawnEntity(player.getEyeLocation(), EntityType.SNOWBALL);
		snowball.setMetadata("paintball", new FixedMetadataValue(PiratePlugin.getPlugin(), player.getName()));
		snowball.setShooter(player);
		snowball.setVelocity(player.getLocation().getDirection().multiply(2.2f));
	}

	@EventHandler
	public void onProjectileHit(ProjectileHitEvent event) {

		if (!(event.getEntity() instanceof Snowball) || !event.getEntity().hasMetadata("paintball")) {
			return;
		}

		Location location = event.getEntity().getLocation();

		List<Block> blocksToUpdate = new ArrayList<>();
		List<BlockState> data = new ArrayList<>();
		for (double y = location.getY() - 2.0; y < location.getY() + 2.0; y++) {
			for (double z = location.getZ() - 2.0; z < location.getZ() + 2.0; z++) {
				for (double x = location.getX() - 2.0; x < location.getX() + 2.0; x++) {
					if (random.nextBoolean()) {
						Block block = location.getWorld().getBlockAt((int) x, (int) y, (int) z);
						if (block.getType() != Material.AIR) {
							for (BlockState s : data) {
								if (s.getLocation().equals(block.getLocation())) {
									return;
								}
							}

							if (block.getType() == Material.SIGN || block.getType() == Material.SIGN_POST || block.getType() == Material.WALL_SIGN) {
							    return;
                            }

							blocksToUpdate.add(block);
						}
					}
				}
			}
		}

		GadgetActionEvent actionEvent = new GadgetActionEvent(this, location, GadgetActionEvent.ActionType.BLOCK_CHANGE, blocksToUpdate);
		Bukkit.getServer().getPluginManager().callEvent(actionEvent);

		if (actionEvent.isCancelled()) {
			return;
		}

		actionEvent.getBlocks().forEach(block -> {
			BlockState previousState = block.getState();
			if(toRevert.stream().noneMatch(s -> s.getLocation().equals(block.getLocation()))) {
				toRevert.add(previousState);
				data.add(previousState);
				block.setType(Material.STAINED_CLAY);
				block.setData((byte) random.nextInt(16));
			}
		});

		new BukkitRunnable() {
			@Override
			public void run() {
				if (data.isEmpty()) {
					cancel();
					return;
				}

				BlockState state = data.remove(0);
				state.update(true, true);
				toRevert.remove(state);
			}
		}.runTaskTimer(PiratePlugin.getPlugin(), 100L, 5L);
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();

		if(event.getAction() != Action.PHYSICAL && event.getItem() != null && event.getItem().isSimilar(player.getInventory().getItem(slot))) {
			useGadget(player);
		}
	}

	@Override
	public void shutdown() {
		toRevert.forEach(bs -> bs.update(true, true));
	}

}
