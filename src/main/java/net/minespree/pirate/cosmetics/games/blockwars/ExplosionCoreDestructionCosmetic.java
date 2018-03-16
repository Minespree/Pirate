package net.minespree.pirate.cosmetics.games.blockwars;

import com.comphenix.protocol.wrappers.EnumWrappers;
import net.minespree.wizard.particle.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Collection;

public class ExplosionCoreDestructionCosmetic extends CoreDestructionCosmetic {

    private ParticleEffect effect;

    public ExplosionCoreDestructionCosmetic() {
        super("bwcd_explosion");

        effect = new ParticleEffect();
        effect.setParticle(EnumWrappers.Particle.EXPLOSION_LARGE);
        effect.setCount(1);
    }

    @Override
    public void destroy(Block core) {
        effect.sendParticle(core.getLocation(), (Collection<Player>) Bukkit.getOnlinePlayers());
    }

    @Override
    public void clear(Block core) {
    }

}
