package net.minespree.pirate.cosmetics.types.wineffects;

import com.google.common.collect.Lists;
import net.minespree.pirate.PiratePlugin;
import net.minespree.pirate.cosmetics.CosmeticType;
import net.minespree.pirate.cosmetics.types.WinEffectCosmetic;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @since 22/09/2017
 */
public class HulkSmashWinEffect extends WinEffectCosmetic {

    private List<FallingBlock> fallingBlocks = Lists.newArrayList();

    public HulkSmashWinEffect() {
        super("hulk_smash", CosmeticType.SW_WIN_EFFECT);
    }

    @Override
    public void use(Player... players) {
        Arrays.stream(players).forEach(this::hulkSmash);
    }

    @EventHandler
    public void on(EntityDamageEvent event) {
        if (event.getEntity().hasMetadata("smashing")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockChangeState(EntityChangeBlockEvent event) {
        if (event.getEntity() instanceof FallingBlock) {
            FallingBlock block = (FallingBlock) event.getEntity();
            if (fallingBlocks.contains(block)) {
                fallingBlocks.remove(event.getEntity());
                event.getEntity().remove();
            }
        }
    }

    /*
                            ____...
                 .-"--"""".__    `.
                |            `    |
      (         `._....------.._.:      o< TUNGUSKA WAS NO METEOR
       . )       (   <o>  <o>    g)
        )         )     /        J         I THINK WE KNOW WHO CAUSED IT
       (          |.   /      . (
       $$         (.  (_'.   , )|`         CLEARLY THE WORK OF ALIENS TRYING
       ||         |\`-....--'/  ' \        TO WARN US ABOUT THE WORLD EATER
      /||.         \\ | | | /  /   \.
     //||(\         \`-===-'  '     \o.
    .//7' |)         `. --   / (     OObaaaad888b.
    (<<. / |     .a888b`.__.'d\     OO888888888888a.   OR THE HULK
     \  Y' |    .8888888aaaa88POOOOOO888888888888888.
      \  \ |   .888888888888888888888888888888888888b     WHATEVER
       |   |  .d88888P88888888888888888888888b8888888.
       b.--d .d88888P8888888888888888a:f888888|888888b
       88888b 888888|8888888888888888888888888\8888888
        */
    private void hulkSmash(Player player) {
        player.setMetadata("smashing", new FixedMetadataValue(PiratePlugin.getPlugin(), true));

        player.getWorld().playSound(player.getLocation(), Sound.FIREWORK_LAUNCH, 2.0F, 1.0F);
        player.setVelocity(new Vector(0, 3, 0));

        AtomicBoolean active = new AtomicBoolean(true); // needed a wrapper no need for atomicy.

        final int[] ranMost = {6};

        new BukkitRunnable() {
            int ticks = 0;
            int mostTicks = 25;

            @Override
            public void run() {
                ticks++;
                if (ticks == mostTicks) {
                    player.setVelocity(new Vector(0, -3, 0));
                } else if (ticks == 50) {
                    ranMost[0]--;

                    player.setVelocity(new Vector(0, 3, 0));
                    ticks = 0;
                    if (ranMost[0] <= 0) {
                        player.setVelocity(new Vector(0, -3, 0));
                        active.set(false);
                        cancel();
                    }
                }
            }
        }.runTaskTimer(PiratePlugin.getPlugin(), 0L, 1L);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (active.get()) {
                    if (ranMost[0] < 7 && player.isOnGround()) {
                        player.getWorld().playSound(player.getLocation(), Sound.ANVIL_LAND, 2.0F, 1.0F);

                        for (Block block : getNearbyBlocks(player.getLocation(), 1)) {
                            if (block.getType() != Material.AIR && block.getType().isSolid()) {
                                FallingBlock fb = player.getWorld().spawnFallingBlock(block.getLocation().clone().add(0, 1.1f, 0), block.getType(), block.getData());
                                fb.setVelocity(new Vector(0, ThreadLocalRandom.current().nextFloat(), 0));
                                fb.setDropItem(false);
                                fallingBlocks.add(fb);
                            }
                        }
                    }
                } else {
                    cancel();
                }
            }
        }.runTaskTimer(PiratePlugin.getPlugin(), 0L, 5L);
    }

    private List<Block> getNearbyBlocks(Location location, int radius) {
        List<Block> blocks = new ArrayList<>();
        for(int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
            for(int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
                for(int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                    blocks.add(location.getWorld().getBlockAt(x, y, z));
                }
            }
        }
        return blocks;
    }
}
