package net.minespree.pirate.cosmetics.types.gadgets.fishslapper;

import lombok.Data;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

@Data class FishSlapperData {

    private List<Player> players;
    private BukkitRunnable runnable;
    private int cooldown;

    FishSlapperData(List<Player> players, int cooldown, BukkitRunnable runnable) {
        this.players = players;
        this.runnable = runnable;
        this.cooldown = cooldown;
    }
}
