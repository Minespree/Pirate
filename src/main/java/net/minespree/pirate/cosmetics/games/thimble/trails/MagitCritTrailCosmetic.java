package net.minespree.pirate.cosmetics.games.thimble.trails;

import com.comphenix.protocol.wrappers.EnumWrappers;
import net.minespree.pirate.cosmetics.games.thimble.JumpTrailCosmetic;
import net.minespree.wizard.particle.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collection;

public class MagitCritTrailCosmetic extends JumpTrailCosmetic {

    private ParticleEffect effect;

    public MagitCritTrailCosmetic() {
        super("thjt_magiccrit");
    }

    @Override
    public void initialize() {
        super.initialize();

        effect = new ParticleEffect();
        effect.setParticle(EnumWrappers.Particle.CRIT_MAGIC);
        effect.setCount(1);
    }

    @Override
    public void display(Location location) {
        effect.sendParticle(location, (Collection<Player>) Bukkit.getOnlinePlayers());
    }
}
