package net.minespree.pirate.cosmetics;

import net.minespree.babel.Babel;
import net.minespree.babel.BabelMessage;
import net.minespree.pirate.cosmetics.games.skywars.CageShapeType;
import net.minespree.pirate.cosmetics.games.skywars.cages.BaseCageCosmetic;
import net.minespree.pirate.cosmetics.games.thimble.LandBlockCosmetic;
import net.minespree.pirate.cosmetics.games.thimble.markers.BasicMarkerCosmetic;
import net.minespree.pirate.cosmetics.games.thimble.markers.FlowerMarkerCosmetic;
import net.minespree.pirate.cosmetics.games.thimble.markers.SignMarkerCosmetic;
import net.minespree.pirate.cosmetics.types.HatCosmetic;
import net.minespree.pirate.cosmetics.types.TitleCosmetic;
import net.minespree.wizard.gui.MultiPageGUI;
import net.minespree.wizard.util.ItemBuilder;
import net.minespree.wizard.util.ScrollableItem;
import org.bson.Document;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

import java.util.List;
import java.util.function.BiFunction;

import static net.minespree.pirate.cosmetics.CosmeticMenu.*;

public enum CosmeticType {

    //LOBBY
    GADGET(MAIN, Babel.translate("cosmetic_gadget_title"), 14, new ItemBuilder(Material.DIODE)
                    .displayName(Babel.translate("cosmetic_gadget"))
                    .lore(Babel.translateMulti("cosmetic_gadget_lore"))),
    SUPER_HAT(HATS, Babel.translate("cosmetic_superhats_title"), 23, new ItemBuilder(Material.DIAMOND_HELMET)
                    .displayName(Babel.translate("cosmetic_superhat"))
                    .lore(Babel.translateMulti("cosmetic_superhat_lore"))),
    HAT(HATS, Babel.translate("cosmetic_hat_title"), 21, new ItemBuilder(Material.IRON_HELMET)
            .displayName(Babel.translate("cosmetic_hat"))
            .lore(Babel.translateMulti("cosmetic_hat_lore")),
            (id, document) -> new HatCosmetic(id, new ItemBuilder((Document) document.get("item")).build())),
    TITLE(MAIN, Babel.translate("cosmetic_title_title"), 15, new ItemBuilder(Material.NAME_TAG)
            .displayName(Babel.translate("cosmetic_titles"))
            .lore(Babel.translateMulti("cosmetic_titles_lore")),
            (id, document) -> new TitleCosmetic(id, document.getString("title"))),
    PET(MAIN, Babel.translate("cosmetic_pet_title"), 12, new ItemBuilder(Material.BONE)
            .displayName(Babel.translate("cosmetic_pet"))
            .lore(Babel.translateMulti("cosmetic_pet_lore"))),
    TRAIL(MAIN, Babel.translate("cosmetic_trail_title"), 13, new ItemBuilder(Material.NETHER_STALK)
            .displayName(Babel.translate("cosmetic_trail"))
            .lore(Babel.translateMulti("cosmetic_trail_lore"))),

    //SKYWARS
    SW_CAGE(SKYWARS, Babel.translate("cosmetic_cage_title"), 20, new ItemBuilder(Material.GLASS)
            .displayName(Babel.translate("cosmetic_cage"))
            .lore(Babel.translateMulti("cosmetic_cage_lore")), (id, document) -> {
        if(document.containsKey("cage")) {
            Document cage = (Document) document.get("cage");
            CageShapeType type = CageShapeType.valueOf(cage.getString("shape").toUpperCase());
            Material base = Material.valueOf(cage.getString("base").toUpperCase());
            byte baseData = cage.containsKey("baseData") ? cage.getInteger("baseData").byteValue() : (byte) 0;
            if(!cage.containsKey("middle")) {
                return new BaseCageCosmetic(id, type, base, baseData, base, baseData);
            } else {
                return new BaseCageCosmetic(id, type, base, baseData,
                        Material.valueOf(cage.getString("middle").toUpperCase()),
                        cage.containsKey("middleData") ? cage.getInteger("middleData").byteValue() : (byte) 0);
            }
        } else {
            for (CosmeticInstance instance : CosmeticInstance.values()) {
                if(instance.cosmetic.getId().equals(id)) {
                    return instance.cosmetic;
                }
            }
            return null;
        }
    }),
    SW_KILL_MESSAGE(SKYWARS, Babel.translate("cosmetic_sw_kill_message_title"), 21, new ItemBuilder(Material.SKULL_ITEM)
            .displayName(Babel.translate("cosmetic_sw_kill_message"))
            .lore(Babel.translateMulti("cosmetic_sw_kill_message_lore"))
            .durability((short) 3)
            .skinData("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWFlMzg1NWY5NTJjZDRhMDNjMTQ4YTk0NmUzZjgxMmE1OTU1YWQzNWNiY2I1MjYyN2VhNGFjZDQ3ZDMwODEifX19")),
    SW_ARROW_TRAIL(SKYWARS, Babel.translate("cosmetic_sw_arrow_trail_title"), 23, new ItemBuilder(Material.ARROW)
            .displayName(Babel.translate("cosmetic_sw_arrow_trail"))
            .lore(Babel.translateMulti("cosmetic_sw_arrow_trail_lore"))),
    SW_WIN_EFFECT(SKYWARS, Babel.translate("cosmetic_sw_win_effect_title"), 24, new ItemBuilder(Material.GOLD_BLOCK)
            .displayName(Babel.translate("cosmetic_sw_win_effect"))
            .lore(Babel.translateMulti("cosmetic_sw_win_effect_lore"))),

    //BLOCKWARS
    BW_CORE_DESTROY(BLOCKWARS, Babel.translate("cosmetic_coredestroy_title"), 20, new ItemBuilder(Material.WOOD_PICKAXE)
            .displayName(Babel.translate("cosmetic_coredestroy"))
            .lore(Babel.translateMulti("cosmetic_coredestroy_lore"))),
    BW_KILL_MESSAGE(BLOCKWARS, Babel.translate("cosmetic_bw_kill_message_title"), 21, new ItemBuilder(Material.SKULL_ITEM)
            .displayName(Babel.translate("cosmetic_bw_kill_message"))
            .lore(Babel.translateMulti("cosmetic_bw_kill_message_lore"))
            .durability((short) 3)
            .skinData("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWFlMzg1NWY5NTJjZDRhMDNjMTQ4YTk0NmUzZjgxMmE1OTU1YWQzNWNiY2I1MjYyN2VhNGFjZDQ3ZDMwODEifX19")),
    BW_ARROW_TRAIL(BLOCKWARS, Babel.translate("cosmetic_bw_arrow_trail_title"), 23, new ItemBuilder(Material.ARROW)
            .displayName(Babel.translate("cosmetic_bw_arrow_trail"))
            .lore(Babel.translateMulti("cosmetic_bw_arrow_trail_lore"))),
    BW_WIN_EFFECT(BLOCKWARS, Babel.translate("cosmetic_bw_win_effect_title"), 24, new ItemBuilder(Material.GOLD_BLOCK)
            .displayName(Babel.translate("cosmetic_bw_win_effect"))
            .lore(Babel.translateMulti("cosmetic_bw_win_effect_lore"))),

    //THIMBLE
    TH_LAND_BLOCK(THIMBLE, Babel.translate("cosmetic_landblock_title"), 20, new ItemBuilder(Material.NETHER_STAR)
            .displayName(Babel.translate("cosmetic_landblock"))
            .lore(Babel.translateMulti("cosmetic_landblock_lore")),
            ((id, document) -> {
        Document item = (Document) document.get("item");
        return new LandBlockCosmetic(id, new MaterialData(Material.valueOf(item.getString("material")),
                item.containsKey("durability") ? item.getInteger("durability").byteValue() : (byte) 0));
            })),
    TH_SUCCESSFUL_LAND(THIMBLE, Babel.translate("cosmetic_successfulland_title"), 21, new ItemBuilder(Material.BUCKET)
            .displayName(Babel.translate("cosmetic_successfulland"))
            .lore(Babel.translate("cosmetic_successfulland_lore"))),
    TH_MARKER(THIMBLE, Babel.translate("cosmetic_thimblemarker_title"), 22, new ItemBuilder(Material.WATER_BUCKET)
            .displayName(Babel.translate("cosmetic_thimblemarker"))
            .lore(Babel.translateMulti("cosmetic_thimblemarker_lore")),
            (id, document) -> {
        if(document.containsKey("messages")) {
            return new SignMarkerCosmetic(id, (List<String>) document.get("messages"));
        } else if(document.containsKey("flower")) {
            Document flowerDoc = (Document) document.get("flower");
            return new FlowerMarkerCosmetic(id, new MaterialData(Material.valueOf(
                    flowerDoc.getString("material")), flowerDoc.getInteger("data").byteValue()));
        } else if(document.containsKey("block")) {
            Document blockDoc = (Document) document.get("block");
            return new BasicMarkerCosmetic(id, new MaterialData(Material.valueOf(
                    blockDoc.getString("material")), blockDoc.getInteger("data").byteValue()));
        }
        return CosmeticInstance.SKULL_MARKER.getCosmetic();
    }),
    TH_JUMP_TRAIL(THIMBLE, Babel.translate("cosmetic_thjumptrail_title"), 23, new ItemBuilder(Material.NETHER_STALK)
            .displayName(Babel.translate("cosmetic_thjumptrail"))
            .lore(Babel.translateMulti("cosmetic_thjumptrail_lore"))),
    TH_WIN_EFFECT(THIMBLE, Babel.translate("cosmetic_thwineffect_title"), 24, new ItemBuilder(Material.GOLD_BLOCK)
            .displayName(Babel.translate("cosmetic_thwineffect"))
            .lore(Babel.translateMulti("cosmetic_thwineffect_lore"))),


    ;

    CosmeticMenu menu;
    int slot;
    ItemBuilder menuItem;
    BiFunction<String, Document, Cosmetic> loadConsumer;
    MultiPageGUI gui;

    CosmeticType(CosmeticMenu menu, BabelMessage title, int slot, ItemBuilder menuItem, BiFunction<String, Document, Cosmetic> loadConsumer) {
        this.menu = menu;
        this.slot = slot;
        this.menuItem = menuItem;
        this.loadConsumer = loadConsumer;
        this.gui = new MultiPageGUI(title, MultiPageGUI.PageFormat.RECTANGLE3, 54, 50, 48);

        gui.setItem(49, new ItemBuilder(Material.BOOK).displayName(Babel.translate("cosmetic_back")), (p, type) -> menu.getGui().open(p));
        gui.setItem(10, 0, new ItemBuilder(Material.BARRIER).displayName(Babel.translate("unequip_loot")), (p, type) ->
                CosmeticManager.getCosmeticManager().getSelectedCosmetic(p, this).ifPresent(cosmetic -> {
                    cosmetic.unequip(p, true);
                    gui.refresh(p);
                }));
        ScrollableItem item = new ScrollableItem(new ItemBuilder(Material.SIGN).displayName(Babel.translate("cosmetic_sort")))
                .setSelection(0, Babel.translate("alphabetical"), player -> gui.open(player, MultiPageGUI.SortType.ALPHABETICAL.getMethod()))
                .setSelection(1, Babel.translate("alphabetical_owned"), player -> gui.open(player, CosmeticSortType.ALPHABETICAL_OWNED.getMethod()))
                .setSelection(2, Babel.translate("rarity"), player -> {})
                .setSelection(3, Babel.translate("rarity_owned"), player -> {});
        gui.setItem(45, item::build, (player, type) -> {
            item.select(player, type);
            gui.refresh(player, 45);
        });
        menu.getGui().setItem(slot, menuItem::build, (p, type) -> gui.openOr(p, MultiPageGUI.SortType.ALPHABETICAL.getMethod()));
    }

    CosmeticType(CosmeticMenu menu, BabelMessage title, int slot, ItemBuilder menuItem) {
        this(menu, title, slot, menuItem, (id, document) -> {
            for (CosmeticInstance instance : CosmeticInstance.values()) {
                if(instance.cosmetic.getId().equals(id)) {
                    return instance.cosmetic;
                }
            }
            return null;
        });
    }

}
