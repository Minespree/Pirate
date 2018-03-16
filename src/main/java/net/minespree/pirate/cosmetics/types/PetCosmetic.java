package net.minespree.pirate.cosmetics.types;

import net.minespree.pirate.cosmetics.Cosmetic;
import net.minespree.pirate.cosmetics.CosmeticType;

public abstract class PetCosmetic extends Cosmetic {

    public PetCosmetic(String id) {
        super(id, CosmeticType.PET);
    }
}
