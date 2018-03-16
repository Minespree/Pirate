package net.minespree.pirate.cosmetics;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.mongodb.client.model.Filters;
import lombok.Getter;
import lombok.Setter;
import net.minespree.babel.Babel;
import net.minespree.feather.db.mongo.MongoManager;
import net.minespree.feather.player.NetworkPlayer;
import net.minespree.feather.player.PlayerManager;
import net.minespree.feather.player.rank.Rank;
import net.minespree.feather.util.Scheduler;
import net.minespree.pirate.PiratePlugin;
import net.minespree.pirate.cosmetics.types.GadgetCosmetic;
import net.minespree.wizard.executors.BukkitSyncExecutor;
import net.minespree.wizard.gui.AuthenticationGUI;
import net.minespree.wizard.util.ItemBuilder;
import org.bson.Document;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class CosmeticManager {

    @Getter
    private final static CosmeticManager cosmeticManager = new CosmeticManager();

    @Getter @Setter
    private boolean disabled;
    @Getter
    private Map<String, Cosmetic> cosmetics = new HashMap<>();

    public void add(Cosmetic cosmetic) {
        cosmetics.put(cosmetic.getId(), cosmetic);
    }

    public void load() {
        System.out.println("Loading Cosmetics");
        ListenableFuture<Document> future = Scheduler.getPublicExecutor().submit(() ->
                MongoManager.getInstance().getCollection("cosmetics").find(Filters.eq("_id", "cosmetics")).first());
        Futures.addCallback(future, new FutureCallback<Document>() {
            @SuppressWarnings("unchecked")
            @Override
            public void onSuccess(Document document) {
                List<Document> ids = (List<Document>) document.get("ids");
                for (Document doc : ids) {
                    String id = doc.getString("id");
                    String type = doc.getString("type");
                    try {
                        CosmeticType cosmeticType = CosmeticType.valueOf(type.toUpperCase());
                        Cosmetic cosmetic = cosmeticType.loadConsumer.apply(id, doc);
                        if(cosmetic != null) {
                            if(doc.containsKey("loot"))
                                cosmetic.setLoot(doc.getBoolean("loot"));
                            if(doc.containsKey("rank")) {
                                cosmetic.setRankNeeded(Rank.valueOf(doc.getString("rank").toUpperCase()));
                            }
                            if(doc.containsKey("achievement")) {
                                cosmetic.setAchievementNeeded(doc.getString("achievement"));
                            }
                            if(doc.containsKey("price")) {
                                cosmetic.setPrice(doc.getInteger("price"));
                            }
                            if(doc.containsKey("endMillis")) {
                                cosmetic.setEndMillis(doc.getLong("endMillis"));
                                cosmetic.setEvent(doc.getString("event"));
                            }
                            cosmetic.setItemBuilder(new ItemBuilder((Document) doc.get("item")));
                            cosmeticType.gui.addItem(cosmetic::setup, cosmetic, (p, clickType) -> {
                                CosmeticPlayer cp = getPlayer(p);
                                if (cp.hasCosmetic(id)) {
                                    cosmetic.interact(p, clickType);
                                    cosmeticType.gui.refresh(p);
                                } else {
                                    if (cosmetic.getPrice() > 0) {
                                        AuthenticationGUI.authenticate(p, Babel.translate("cosmetic_purchase"), cosmeticType.gui,
                                                cosmetic.getItemBuilder(), player -> {
                                            if(cp.getGems() >= cosmetic.getPrice()) {
                                                cp.removeGems(cosmetic.getPrice());
                                                cosmetic.equip(player, true);
                                                cosmeticType.gui.open(p);

                                                Integer ammo = null;

                                                if (cosmetic instanceof GadgetCosmetic) {
                                                    GadgetCosmetic.GadgetAmmo gadgetAmmo = ((GadgetCosmetic) cosmetic).getAmmo();

                                                    if (gadgetAmmo != null) {
                                                        ammo = gadgetAmmo.getAmmo(cp.getUuid());
                                                    }
                                                }

                                                cp.addUnlockedCosmetic(cosmetic.getId(), ammo);
                                            } else {
                                                Babel.translate("cant_afford").sendMessage(p);
                                                p.closeInventory();
                                            }}, player -> cosmeticType.gui.open(p));
                                    }
                                }
                            });
                            cosmetic.initialize();
                            add(cosmetic);
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid cosmetic type for cosmetic: " + id);
                        return;
                    }
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                throw new RuntimeException("Failed to load data");
            }
        }, BukkitSyncExecutor.create(PiratePlugin.getPlugin()));
    }

    public List<Cosmetic> getCosmeticsByType(CosmeticType type) {
        return cosmetics.values().stream().filter(c -> c.getType().equals(type)).collect(Collectors.toList());
    }

    public Optional<Cosmetic> getSelectedCosmetic(Player player, CosmeticType type) {
        return getCosmeticsByType(type).stream()
                .filter(cosmetic -> getPlayer(player).isEquipped(cosmetic.getId()))
                .findFirst();
    }

    public List<Cosmetic> getOwnedCosmeticsByType(Player player, CosmeticType type) {
        return getCosmeticsByType(type).stream().filter(c -> c.has(player)).collect(Collectors.toList());
    }

    public void open(Player player, CosmeticMenu menu) {
        menu.getGui().open(player);
    }

    public void open(Player player, CosmeticType type) {
        type.gui.open(player);
    }

    public void join(Player player) {
        CosmeticPlayer cp = getPlayer(player);

        if (cp == null || !cp.isLoaded() || disabled) {
            return;
        }

        Map<String, Boolean> cosmetics = cp.getCosmetics();

        // Equip current enable cosmetics if they are equipped
        cosmetics.keySet().stream().filter(id -> cosmetics.get(id) && this.cosmetics.containsKey(id)).forEach(s -> {
            this.cosmetics.get(s).equip(player, false);
        });

        // Add available list of cosmetics of each player to database
        this.cosmetics.values().stream().filter(c -> c.has(player) && !cp.hasCosmetic(c.getId())).forEach(c -> {
            cp.setEquipped(c.getId(), false);
        });
    }

    public CosmeticPlayer getPlayer(Player player) {
        return (CosmeticPlayer) PlayerManager.getInstance().getPlayer(player);
    }

}
