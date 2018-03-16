package net.minespree.pirate.cosmetics.types.gadgets;

import net.minespree.pirate.PiratePlugin;
import net.minespree.pirate.cosmetics.types.GadgetCosmetic;
import net.minespree.pirate.cosmetics.types.gadgets.events.GadgetActionEvent;
import net.minespree.pirate.crates.Rarity;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.spigotmc.event.entity.EntityDismountEvent;

public class SaddleGadget extends GadgetCosmetic {

    public SaddleGadget() {
        super("gsaddle", 5000);

        ammo = null;
    }

    @EventHandler
    public void interact(PlayerInteractAtEntityEvent event) {

        if(useGadget(event.getPlayer())) {

            if (!(event.getRightClicked() instanceof Player)) {
                return;
            }

            Player player = event.getPlayer();
            Player clicked = (Player) event.getRightClicked();

            if (player.getItemInHand().getType() != Material.SADDLE) {
                return;
            }

            GadgetActionEvent gadgetActionEvent = new GadgetActionEvent(this, player.getLocation(), GadgetActionEvent.ActionType.PLAYER_TRANSPORT);
            Bukkit.getServer().getPluginManager().callEvent(gadgetActionEvent);

            if (gadgetActionEvent.isCancelled()) {
                return;
            }

            Player editable = clicked;

            while (editable.getPassenger() != null) {
                editable = (Player) editable.getPassenger();
            }

            clicked.setPassenger(player);

            player.setMetadata("riding", new FixedMetadataValue(PiratePlugin.getPlugin(), player.getName()));

            clicked.setMetadata("hasriders", new FixedMetadataValue(PiratePlugin.getPlugin(), clicked.getName()));
        }
    }

    @EventHandler
    public void launch(PlayerInteractEvent event) {

        Player player = event.getPlayer();

        if (player.hasMetadata("hasriders")) {

            if (player.getPassenger() != null) {

                if (player.getPassenger().hasMetadata("riding")) {

                    player.getPassenger().eject();
                }
            }
        }
    }

    @EventHandler
    public void dismount(EntityDismountEvent event) {

        if (!(event.getDismounted() instanceof Player) || !(event.getEntity() instanceof Player)) {
            return;
        }

        Player entity = (Player) event.getEntity();

        entity.setVelocity(entity.getEyeLocation().getDirection().multiply(2.0));
    }

    @Override
    public void use(Player player) {}
}
