package net.minespree.pirate.cosmetics;

import lombok.Getter;
import net.minespree.babel.Babel;
import net.minespree.babel.BabelMessage;
import net.minespree.feather.player.NetworkPlayer;
import net.minespree.pirate.PiratePlugin;
import net.minespree.wizard.gui.PerPlayerInventoryGUI;
import net.minespree.wizard.util.ItemBuilder;
import org.bukkit.Material;

@Getter
public enum CosmeticMenu {

    MAIN(Babel.translate("cosmetic_main_title")),
    HATS(Babel.translate("cosmetic_hats_title"), MAIN, new ItemBuilder(Material.GOLD_HELMET).displayName(Babel.translate("cosmetic_hats"))
            .lore(Babel.translateMulti("cosmetic_hats_lore")), 11),
    THIMBLE(Babel.translate("cosmetic_thimble_title"), MAIN, new ItemBuilder(Material.WATER_BUCKET).displayName(Babel.translate("cosmetic_thimble"))
            .lore(Babel.translateMulti("cosmetic_thimble_lore")), 29),
    BLOCKWARS(Babel.translate("cosmetic_blockwars_title"), MAIN, new ItemBuilder(Material.STAINED_CLAY).displayName(Babel.translate("cosmetic_blockwars"))
            .lore(Babel.translateMulti("cosmetic_blockwars_lore")).durability((short) 14), 31),
    SKYWARS(Babel.translate("cosmetic_skywars_title"), MAIN, new ItemBuilder(Material.GRASS).displayName(Babel.translate("cosmetic_skywars"))
            .lore(Babel.translateMulti("cosmetic_skywars_lore")), 33),

    ;

    private BabelMessage title;
    private PerPlayerInventoryGUI gui;

    CosmeticMenu(BabelMessage title) {
        this.title = title;
        this.gui = new PerPlayerInventoryGUI(title, 54, PiratePlugin.getPlugin());

        gui.setItem(48, p -> new ItemBuilder(Material.DOUBLE_PLANT).displayName(Babel.translate("player_coins"), NetworkPlayer.of(p).getCoins()).build(p), (p, type) -> {});
        gui.setItem(49, p -> new ItemBuilder(Material.ENDER_CHEST).build(p), (p, type) -> {});
        gui.setItem(50, p -> new ItemBuilder(Material.EMERALD).displayName(Babel.translate("player_gems"), NetworkPlayer.of(p).getGems()).build(p), (p, type) -> {});
    }

    CosmeticMenu(BabelMessage title, CosmeticMenu menu, ItemBuilder builder, int slot) {
        this(title);

        gui.setItem(45, p -> new ItemBuilder(Material.BOOK).displayName(Babel.translate("cosmetic_back")).build(p), (p, type) -> menu.getGui().open(p));
        menu.getGui().setItem(slot, builder::build, (p, type) -> gui.open(p));
    }

}
