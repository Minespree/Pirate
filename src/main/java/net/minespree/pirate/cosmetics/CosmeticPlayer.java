package net.minespree.pirate.cosmetics;

import com.google.common.collect.Maps;
import lombok.Getter;
import net.minespree.feather.player.PlayerKey;
import net.minespree.feather.player.implementations.KittedPlayer;
import net.minespree.pirate.cosmetics.types.GadgetCosmetic;
import org.bson.Document;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class CosmeticPlayer extends KittedPlayer {
    private static final String UNLOCKED_KEY = PlayerKey.UNLOCKED_COSMETICS;

    @Getter
    private Map<String, Boolean> cosmetics = Maps.newHashMap();

    public CosmeticPlayer(UUID uuid) {
        super(uuid);
    }

    @Override
    public void bootstrap(Document document) {
        super.bootstrap(document);

        Object unlockedObj = document.get(UNLOCKED_KEY);

        // Don't need to initialize anything
        if (unlockedObj == null) {
            return;
        }

        List<Document> unlocked = (List<Document>) unlockedObj;

        for (Document doc : unlocked) {
            if (doc == null) continue;

            String id = doc.getString("cosmeticId");

            if (id == null) continue;

            cosmetics.putIfAbsent(id, doc.getBoolean("equipped"));
            Integer ammo = doc.getInteger("ammo");

            if (ammo != null) {
                GadgetCosmetic cosmetic = (GadgetCosmetic) CosmeticManager.getCosmeticManager().getCosmetics().get(id);

                if (cosmetic != null) {
                    cosmetic.getAmmo().setAmmo(this.uuid, ammo);
                }
            }
        }
    }

    private void addCosmetic(String id, boolean equipped, Integer ammo) {
        Document cosmeticDoc = getCosmeticDoc(id, equipped, ammo);

        addSetUpdate(UNLOCKED_KEY, cosmeticDoc);
    }

    public void addUnlockedCosmetic(String id, Integer ammo) {
        addCosmetic(id, true, ammo);
    }

    public void setEquipped(String id, boolean equipped) {
        if (cosmetics.containsKey(id)) {
            updateCosmetic(id, equipped, null);
        } else {
            addCosmetic(id, equipped, null);
        }

        cosmetics.put(id, equipped);
    }

    private Document getCosmeticDoc(String id, boolean equipped, Integer ammo) {
        Document cosmeticDoc = new Document();

        cosmeticDoc.put("cosmeticId", id);
        cosmeticDoc.put("equipped", equipped);

        if (ammo != null) {
            cosmeticDoc.put("ammo", ammo);
        }

        return cosmeticDoc;
    }

    public void updateCosmetic(GadgetCosmetic cosmetic) {
        GadgetCosmetic.GadgetAmmo ammo = cosmetic.getAmmo();
        Integer ammoAmount = null;

        if (ammo != null) {
            ammoAmount = ammo.getAmmo(this.uuid);
        }

        updateCosmetic(cosmetic.getId(), cosmetic.equipped.contains(this.uuid), ammoAmount);
    }

    private void updateCosmetic(String id, boolean equipped, Integer ammo) {
        int index = getMongoIndex(id);

        // Cosmetic not unlocked
        if (index == -1) {
            return;
        }

        addUpdate(UNLOCKED_KEY + "." + index, getCosmeticDoc(id, equipped, ammo));
    }

    private int getMongoIndex(String id) {
        // Mongo guarantees array item order, LinkedHashMap too
        Set<String> cosmeticIds = cosmetics.keySet();
        int index = 0;

        for (String cosmeticId : cosmeticIds) {
            if (cosmeticId.equals(id)) {
                return index;
            }

            index++;
        }

        return -1;
    }

    public boolean isEquipped(String id) {
        return cosmetics.getOrDefault(id, false);
    }

    public boolean hasCosmetic(String id) {
        return cosmetics.containsKey(id);
    }

    public List<Cosmetic> getOwnedCosmetics() {
        return cosmetics.keySet().stream().map(id -> CosmeticManager.getCosmeticManager().getCosmetics().get(id)).collect(Collectors.toList());
    }

}
