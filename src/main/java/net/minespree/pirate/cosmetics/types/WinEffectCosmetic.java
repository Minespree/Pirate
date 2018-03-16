package net.minespree.pirate.cosmetics.types;

import net.minespree.pirate.cosmetics.Cosmetic;
import net.minespree.pirate.cosmetics.CosmeticType;
import org.bukkit.entity.Player;

public abstract class WinEffectCosmetic extends Cosmetic {

    public WinEffectCosmetic(String id, CosmeticType type) {
        super(id, type);
    }

    public abstract void use(Player... players);

}
