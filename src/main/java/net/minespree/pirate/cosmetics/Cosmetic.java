package net.minespree.pirate.cosmetics;

import lombok.Getter;
import lombok.Setter;
import net.minespree.babel.Babel;
import net.minespree.feather.player.rank.Rank;
import net.minespree.pirate.PiratePlugin;
import net.minespree.pirate.crates.LootItem;
import net.minespree.pirate.crates.Rarity;
import net.minespree.wizard.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Getter @Setter
public abstract class Cosmetic implements Listener {

    protected Set<UUID> equipped = new HashSet<>();

    @Getter @Setter
    private Rank rankNeeded;
    @Getter @Setter
    private String achievementNeeded, event;
    @Getter @Setter
    private int price;
    @Getter @Setter
    private boolean loot;
    @Getter @Setter
    private long endMillis;

    protected final String id;
    private final CosmeticType type;
    @Getter @Setter
    protected ItemBuilder itemBuilder;

    public Cosmetic(String id, CosmeticType type) {
        this.id = id;
        this.type = type;
    }

    public void registerLootItem(Rarity rarity) {

        LootItem.getItems().add(rarity.getWeight(), new LootItem(this.itemBuilder.build(), rarity));
    }

    public void initialize() {
        Bukkit.getPluginManager().registerEvents(this, PiratePlugin.getPlugin());
    }

    public void interact(Player player, ClickType type) {
        if(equipped.contains(player.getUniqueId())) {
            unequip(player, true);
        } else {
            equip(player, true);
        }
    }

    public void equip(Player player, boolean save) {
        if(save) {
            CosmeticManager manager = CosmeticManager.getCosmeticManager();
            CosmeticPlayer cp = manager.getPlayer(player);

            manager.getCosmeticsByType(type).stream()
                    .filter(c -> cp.isEquipped(c.getId()))
                    .forEach(c -> c.unequip(player, true));

            cp.setEquipped(id, true);
        }

        equipped.add(player.getUniqueId());
    }

    public void unequip(Player player, boolean save) {
        if (save) {
            CosmeticManager.getCosmeticManager().getPlayer(player).setEquipped(id, false);
        }

        equipped.remove(player.getUniqueId());
    }

    protected boolean isEquipped(Player player) {
        return CosmeticManager.getCosmeticManager().getPlayer(player).isEquipped(id);
    }

    public void onJoin(Player player) {}

    public void onQuit(Player player) {}

    public void itemData(Player player, ItemBuilder builder) {}

    public ItemStack setup(Player player) {
        CosmeticPlayer cp = CosmeticManager.getCosmeticManager().getPlayer(player);

        ItemBuilder builder = itemBuilder.clone();
        builder.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
        builder.unenchant(Enchantment.DURABILITY);
        builder.lore(" ");
        itemData(player, builder);

        if(cp.hasCosmetic(id)) {
            if(cp.isEquipped(id)) {
                builder.enchant(Enchantment.DURABILITY, 1);
                builder.lore(Babel.translate("equipped"));
                builder.lore(Babel.translate("click_to_unequip"));
            } else {
                builder.lore(Babel.translate("owned"));
                builder.lore(Babel.translate("click_to_equip"));
            }
            builder.lore("");
        } else {
            builder.material(Material.INK_SACK);
            builder.durability((short) 8);
            if(price > 0) {
                builder.lore(Babel.translate("gems"), price);
                builder.lore(Babel.translate("click_to_purchase"));
                builder.lore(" ");
            }
        }

        if(loot) {
            builder.lore(Babel.translate("exclusive_loot"));
        }
        if(rankNeeded != null) {
            builder.lore(Babel.translate("exclusive_rank"), rankNeeded.getColoredTag().trim());
        }
        if(achievementNeeded != null) {
            builder.lore(Babel.translate("exclusive_achievement"), achievementNeeded);
        }
        if(event != null) {
            if(endMillis < System.currentTimeMillis()) {
                builder.lore(Babel.translate("was_available"), Babel.translate(event));
            } else {
                builder.lore(Babel.translate("available_during"), Babel.translate(event));
            }
        }
        return builder.build(player);
    }

    public boolean has(Player player) {
        CosmeticPlayer cp = CosmeticManager.getCosmeticManager().getPlayer(player);
        return cp.hasCosmetic(id) || ((rankNeeded == null || cp.getRank().has(rankNeeded))
                && (achievementNeeded == null || false) // TODO Rework elements
                && (event == null || System.currentTimeMillis() < endMillis)
                && price == 0
                && !loot);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        onJoin(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        unequip(event.getPlayer(), false);

        onQuit(event.getPlayer());
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
        unequip(event.getPlayer(), false);

        onQuit(event.getPlayer());
    }

    public void shutdown() {}

}
