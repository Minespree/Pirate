package net.minespree.pirate.cosmetics.games.thimble.successful;

import net.minespree.pirate.PiratePlugin;
import net.minespree.pirate.cosmetics.games.thimble.LandCosmetic;
import net.minespree.wizard.util.FireworkUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.FireworkExplodeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class ChickenLandCosmetic extends LandCosmetic {

    private Set<UUID> fireworks = new HashSet<>();

    public ChickenLandCosmetic() {
        super("sl_bawkboom");
    }

    @Override
    public void land(Player player, Location location) {
        Firework firework = FireworkUtil.randomFirework(location, 3, 1);
        Chicken chicken = (Chicken) location.getWorld().spawnEntity(location, EntityType.CHICKEN);
        firework.setPassenger(chicken);
        fireworks.add(firework.getUniqueId());
    }

    @EventHandler
    public void onFireworkExplode(FireworkExplodeEvent event) {
        if(fireworks.contains(event.getEntity().getUniqueId())) {
            if(event.getEntity().getPassenger() != null) {
                event.getEntity().getPassenger().remove();
            }
            Bukkit.getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), Sound.CHICKEN_HURT, 1F, 1F));
            List<Item> feathers = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                Item item = event.getEntity().getWorld().dropItem(event.getEntity().getLocation(), new ItemStack(Material.FEATHER));
                item.setVelocity(new Vector((ThreadLocalRandom.current().nextBoolean() ? -.3 : .3) * Math.random(),
                        (ThreadLocalRandom.current().nextBoolean() ? -.3 : .3) * Math.random(),
                        (ThreadLocalRandom.current().nextBoolean() ? -.3 : .3) * Math.random()));
                feathers.add(item);
            }
            Bukkit.getScheduler().scheduleSyncDelayedTask(PiratePlugin.getPlugin(), () -> feathers.forEach(Entity::remove), 60L);

            fireworks.remove(event.getEntity().getUniqueId());
        }
    }
}
