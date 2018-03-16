package net.minespree.pirate.cosmetics.games.thimble;

import com.google.common.collect.Sets;
import net.minespree.pirate.PiratePlugin;
import net.minespree.pirate.cosmetics.Cosmetic;
import net.minespree.pirate.cosmetics.CosmeticType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.Set;
import java.util.UUID;

public abstract class JumpTrailCosmetic extends Cosmetic {

    private BukkitTask task;

    private Set<UUID> using = Sets.newConcurrentHashSet();

    public JumpTrailCosmetic(String id) {
        super(id, CosmeticType.TH_JUMP_TRAIL);
    }

    public void initialize() {
        super.initialize();

        task = Bukkit.getScheduler().runTaskTimer(PiratePlugin.getPlugin(), () -> {
            for (UUID uuid : using) {
                display(Bukkit.getPlayer(uuid).getLocation());
            }
        }, 1L, 1L);
    }

    public abstract void display(Location location);

    @Override
    public void shutdown() {
        if(task != null) {
            task.cancel();
        }
    }

    public void use(Player player) {
        using.add(player.getUniqueId());
    }

    public void stopUsing(Player player) {
        using.remove(player.getUniqueId());
    }

}
