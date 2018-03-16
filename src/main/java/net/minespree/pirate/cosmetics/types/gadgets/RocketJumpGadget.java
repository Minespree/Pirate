package net.minespree.pirate.cosmetics.types.gadgets;

import net.minespree.pirate.cosmetics.types.GadgetCosmetic;
import net.minespree.pirate.cosmetics.types.gadgets.events.GadgetActionEvent;
import net.minespree.pirate.crates.Rarity;
import net.minespree.wizard.util.FireworkUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;

public class RocketJumpGadget extends GadgetCosmetic {

	public RocketJumpGadget() {
		super("grocketjump", 25000);

		ammo = new GadgetAmmo();
	}

	@Override
	public void initialize() {
		super.initialize();
		super.registerLootItem(Rarity.COMMON);
	}

	public void use(Player player) {

		Location location = player.getLocation();

		GadgetActionEvent actionEvent = new GadgetActionEvent(this, location, GadgetActionEvent.ActionType.PLAYER_TRANSPORT);
		Bukkit.getServer().getPluginManager().callEvent(actionEvent);

		if (actionEvent.isCancelled()) {
			return;
		}

		Firework firework = FireworkUtil.randomFirework(location);
		firework.setPassenger(player);
	}

	@Override
	public void onQuit(Player player) {
		if (player.isInsideVehicle()) {
			player.getVehicle().remove();
		}
	}
}
