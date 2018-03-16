package net.minespree.pirate.util;

import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.List;

public class ArcEffect extends DrawnEffect {

    private float height;
    private float density;

    private Location start;
    private Location target;

    public ArcEffect(EnumParticle particle, FloatingItem floatingItem, float r, float g, float b, long speed, float height, float density, List<Player> viewers, Location start, Location target) {
        super(particle, floatingItem, r, g, b, speed, viewers);

        this.height = height;
        this.density = density;
        this.start = start;
        this.target = target;

        this.populateLocations();
    }

    public ArcEffect(EnumParticle particle, float r, float g, float b, long speed, float height, float density, List<Player> viewers, Location start, Location target) {
        super(particle, r, g, b, speed, viewers);

        this.height = height;
        this.density = density;
        this.start = start;
        this.target = target;

        this.populateLocations();
    }

    public ArcEffect(FloatingItem floatingItem, long speed, float height, float density, List<Player> viewers, Location start, Location target) {
        super(floatingItem, speed, viewers);

        this.height = height;
        this.density = density;
        this.start = start;
        this.target = target;

        this.populateLocations();
    }

    @Override
    public void populateLocations() {

        Location location = start;
        Location target = this.target;

        if (target == null) {
            return;
        }

        Vector link = target.toVector().subtract(location.toVector());

        float length = (float) link.length();
        float pitch = (float) (4 * height / Math.pow(length, 2));

        for (int i = 0; i < density; i++) {

            Vector v = link.clone().normalize().multiply(length * i / density);

            float x = ((float) i / density) * length - length / 2;
            float y = (float) (-pitch * Math.pow(x, 2) + height);

            location.add(v).add(0, y, 0);

            super.addLocation(location.clone());

            location.subtract(0, y, 0).subtract(v);
        }
    }
}