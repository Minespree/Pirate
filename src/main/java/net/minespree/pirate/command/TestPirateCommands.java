package net.minespree.pirate.command;

import net.minespree.feather.command.system.annotation.Command;
import net.minespree.feather.command.system.annotation.Param;
import net.minespree.feather.player.rank.Rank;
import net.minespree.pirate.cosmetics.CosmeticManager;
import net.minespree.pirate.cosmetics.CosmeticPlayer;
import net.minespree.pirate.cosmetics.games.skywars.CageCosmetic;
import net.minespree.pirate.cosmetics.types.WinEffectCosmetic;
import org.bukkit.ChatColor;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

/**
 * @since 22/09/2017
 */
public class TestPirateCommands {

    private static CageCosmetic cageCosmetic;

    @Command(names = "winfx", requiredRank = Rank.ADMIN, hideFromHelp = true)
    public static void winFx(Player player, @Param(name = "Effect") String effect) {
        WinEffectCosmetic cosmetic = (WinEffectCosmetic) CosmeticManager.getCosmeticManager().getCosmetics().getOrDefault(effect, null);
        if (cosmetic == null) {
            player.sendMessage(ChatColor.RED + "Win effect " + effect + " not found.");
        } else {
            cosmetic.use(player);
            player.sendMessage(ChatColor.GREEN + "Activated " + effect);
        }
    }

    @Command(names = "cage", requiredRank = Rank.ADMIN, hideFromHelp = true)
    public static void cage(Player player, @Param(name = "Cage") String cage, @Param(name = "Face") String face) {
        CageCosmetic cosmetic = (CageCosmetic) CosmeticManager.getCosmeticManager().getCosmetics().getOrDefault(cage, null);
        if (cosmetic == null) {
            player.sendMessage(ChatColor.RED + "Win effect " + cage + " not found.");
        } else {
            cosmetic.build(player.getLocation(), BlockFace.valueOf(face.toUpperCase()));
            player.sendMessage(ChatColor.GREEN + "Activated " + cage);
        }
    }

    @Command(names = "givecosmetics", requiredRank = Rank.ADMIN, hideFromHelp = true)
    public static void giveCosmetics(Player player) {
        CosmeticPlayer cosmeticPlayer = CosmeticManager.getCosmeticManager().getPlayer(player);
        for (String s : CosmeticManager.getCosmeticManager().getCosmetics().keySet()) {
            if(!cosmeticPlayer.hasCosmetic(s)) {
                cosmeticPlayer.addUnlockedCosmetic(s, null);
            }
        }
    }

}
