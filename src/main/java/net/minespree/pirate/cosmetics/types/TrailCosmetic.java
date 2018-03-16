package net.minespree.pirate.cosmetics.types;

import net.minespree.pirate.cosmetics.Cosmetic;
import net.minespree.pirate.cosmetics.CosmeticType;

public abstract class TrailCosmetic extends Cosmetic {

    public TrailCosmetic(String id) {
        super(id, CosmeticType.TRAIL);
    }

}
