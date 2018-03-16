package net.minespree.pirate.util;

import lombok.Data;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import net.minespree.wizard.WizardPlugin;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class DrawnEffect {

    private EnumParticle particle;
    private float r, g, b;
    private FloatingItem floatingItem;

    private long speedPerParticle;

    private BukkitTask bukkitTask;

    private boolean running;

    private List<Player> viewers = new ArrayList<>();
    private List<Location> particleLocations = new ArrayList<>();

    DrawnEffect(EnumParticle particle, FloatingItem floatingItem, float r, float g, float b, long speedPerParticle, List<Player> viewers) {

        this.particle = particle;
        this.floatingItem = floatingItem;
        this.r = r;
        this.g = g;
        this.b = b;
        this.speedPerParticle = speedPerParticle;
        this.viewers = viewers;
    }

    DrawnEffect(EnumParticle particle, float r, float g, float b, long speedPerParticle, List<Player> viewers) {

        this.particle = particle;
        this.r = r;
        this.g = g;
        this.b = b;
        this.speedPerParticle = speedPerParticle;
        this.viewers = viewers;
    }

    DrawnEffect(FloatingItem floatingItem, long speedPerParticle, List<Player> viewers) {

        this.particle = null;
        this.floatingItem = floatingItem;
        this.speedPerParticle = speedPerParticle;
        this.viewers = viewers;
    }

    public void run() {

        this.bukkitTask = new BukkitRunnable() {

            int i = particleLocations.size();
            Location location;

            @Override
            public void run() {

                if (i == 1) {

                    this.cancel();
                    return;
                }

                location = particleLocations.get(i-1);

                if (floatingItem != null) {

                    floatingItem.teleport(location);

                    if (particle != null) {

                        PacketPlayOutWorldParticles worldParticles = new PacketPlayOutWorldParticles(
                                EnumParticle.valueOf(particle.toString()), true, (float) location.getX(), (float) location.getY(), (float) location.getZ(), r, g, b, (float) 0.004, 0);
                        viewers.forEach(player -> ((CraftPlayer) player).getHandle().playerConnection.sendPacket(worldParticles));
                    }

                } else {

                    if (particle != null) {

                        PacketPlayOutWorldParticles worldParticles = new PacketPlayOutWorldParticles(
                                EnumParticle.valueOf(particle.toString()), true, (float) location.getX(), (float) location.getY(), (float) location.getZ(), r, g, b, (float) 0.004, 0);
                        viewers.forEach(player -> ((CraftPlayer) player).getHandle().playerConnection.sendPacket(worldParticles));

                    } else {

                        System.out.println("¯\\_(ツ)_/¯");
                    }
                }

                i--;
            }

        }.runTaskTimerAsynchronously(WizardPlugin.getPlugin(), 0L, speedPerParticle);
    }

    /**
     *
     * Should contain math pertaining to generating
     * the locations at which the particles should
     * spawn at.
     *
     * <p><strong>Should contain calls to super</strong></p>
     * <code>super.addLocation(location)</code>
     */
    public abstract void populateLocations();

    /**
     *
     * Adds a location to the list of locations to
     * spawn particles at.
     *
     * @param location Location to add to list.
     */
    void addLocation(Location location) {
        this.particleLocations.add(location);
    }
}
