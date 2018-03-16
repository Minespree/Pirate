package net.minespree.pirate.cosmetics.types.gadgets;

import net.minespree.pirate.cosmetics.types.GadgetCosmetic;
import net.minespree.pirate.cosmetics.types.gadgets.events.GadgetActionEvent;
import net.minespree.pirate.crates.Rarity;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.List;

public class TNTThrowerGadget extends GadgetCosmetic {

    public TNTThrowerGadget() {
        super("gtntthrower", 5000);

        ammo = new GadgetAmmo();
    }

    private List<TNTPrimed> tntPrimedList = new ArrayList<>();

    @Override
    public void initialize() {
        super.initialize();
        super.registerLootItem(Rarity.EPIC);
    }

    @EventHandler
    public void click(PlayerInteractEvent event) {
        if(useGadget(event.getPlayer())) {
            if (event.getAction() != Action.RIGHT_CLICK_AIR) {
                return;
            }

            if (event.getPlayer().getItemInHand().getType() != Material.TNT) {
                return;
            }

            GadgetActionEvent gadgetActionEvent = new GadgetActionEvent(this, event.getPlayer().getLocation(), GadgetActionEvent.ActionType.COSMETIC);
            Bukkit.getServer().getPluginManager().callEvent(gadgetActionEvent);

            if (gadgetActionEvent.isCancelled()) {
                return;
            }

            TNTPrimed tntPrimed = event.getPlayer().getWorld().spawn(event.getPlayer().getLocation(), TNTPrimed.class);
            tntPrimedList.add(tntPrimed);

            tntPrimed.setVelocity(event.getPlayer().getEyeLocation().getDirection().multiply(1.2));

            use(event.getPlayer());
        }
    }

    @EventHandler
    public void damage(EntityDamageByEntityEvent event) {

        if (!(event.getDamager() instanceof TNTPrimed)) {
            return;
        }

        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        TNTPrimed tntPrimed = (TNTPrimed) event.getDamager();
        Player player = (Player) event.getEntity();

        if (!tntPrimedList.contains(tntPrimed)) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void explode(EntityExplodeEvent event) {

        if (!(event.getEntity() instanceof TNTPrimed)) {
            return;
        }

        TNTPrimed tntPrimed = (TNTPrimed) event.getEntity();

        if (!tntPrimedList.contains(tntPrimed)) {
            return;
        }

        event.blockList().clear();
    }

    @Override
    public void use(Player player) {

    }
}
