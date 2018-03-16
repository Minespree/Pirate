package net.minespree.pirate.cosmetics.games.blockwars;

import net.minespree.pirate.cosmetics.Cosmetic;
import net.minespree.pirate.cosmetics.CosmeticType;
import org.bukkit.block.Block;

public abstract class CoreDestructionCosmetic extends Cosmetic {

    public CoreDestructionCosmetic(String id) {
        super(id, CosmeticType.BW_CORE_DESTROY);
    }

    public abstract void destroy(Block core);

    public abstract void clear(Block core);

}
