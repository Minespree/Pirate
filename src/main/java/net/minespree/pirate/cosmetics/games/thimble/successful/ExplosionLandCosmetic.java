package net.minespree.pirate.cosmetics.games.thimble.successful;

import com.comphenix.protocol.wrappers.EnumWrappers;
import net.minespree.pirate.cosmetics.games.thimble.LandCosmetic;
import net.minespree.wizard.particle.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Collection;

public class ExplosionLandCosmetic extends LandCosmetic {

    private ParticleEffect effect = new ParticleEffect();

    public ExplosionLandCosmetic() {
        super("sl_explosion");

        effect.setParticle(EnumWrappers.Particle.EXPLOSION_LARGE);
        effect.setCount(3);
    }

    @Override
    public void land(Player player, Location location) {
        effect.sendParticle(location.clone().add(0, 1, 0), (Collection<Player>) Bukkit.getOnlinePlayers());
        Bukkit.getOnlinePlayers().forEach(p -> p.playSound(p.getLocation(), Sound.EXPLODE, 1F, 1F));
    }
}
