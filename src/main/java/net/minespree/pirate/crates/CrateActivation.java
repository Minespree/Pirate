package net.minespree.pirate.crates;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class CrateActivation implements Listener {

    @EventHandler
    public void activate(PlayerInteractEvent event) {

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        
        if (event.getClickedBlock() == null) {
            return;
        }
        
        if (event.getClickedBlock().getType() != Material.ENDER_CHEST) {
            return;
        }

        Location blockLocation = event.getClickedBlock().getLocation();

        for (Location location : CrateRegistration.chestLocations) {

            if (!location.equals(blockLocation)) {
                return;
            }
        }

        for (Crate crate : Crate.cratesInUse) {

            if (crate.getLocation().equals(blockLocation)) {
                return;
            }
        }

        /*
        Open Menu with crates (amount, type, an potential expiry date)
            - Sorting crates will be done by me
            - Regular and Super crates item stack will remain in menu even if 0, (just the amount will change)
                - This also applies to seasonal crates
        Menu will contain anvil for crafting super crates
         */
    }
}
