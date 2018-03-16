package net.minespree.pirate.cosmetics.types.superhats;

import net.minespree.pirate.PiratePlugin;
import net.minespree.pirate.cosmetics.types.SuperHatCosmetic;
import net.minespree.pirate.crates.Rarity;
import net.minespree.wizard.util.SkullUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BetaSuperHat extends SuperHatCosmetic {

    private int i;
    private static ItemStack b = SkullUtil.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ1MDMxZTg3MTM5YTgxNzY4ZTE0ZmQzZjBhNDdhNjcyMTNmMDYyNTQ2ZmExN2E4YTg2MjIyYmUxOTc4YTNmIn19fQ==\\");
    private static ItemStack e = SkullUtil.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmYzY2Y4ZGQ4MmYzODE5ODMxYmRhNjcxNjU0MGVkZGMyZDcxNTNjODViY2M3OGU0MzI1OTU2NTA0ZjY3NWYifX19\\");
    private static ItemStack t = SkullUtil.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2E2ZTUzYmZiN2MxM2ZlZGJkZmU4OTY3NmY4MWZjMmNhNzk3NDYzNGE2ODQxNDFhZDFmNTE2NGYwZWRmNGEyIn19fQ==\\");
    private static ItemStack a = SkullUtil.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDJjZDVhMWI1Mjg4Y2FhYTIxYTZhY2Q0Yzk4Y2VhZmQ0YzE1ODhjOGIyMDI2Yzg4YjcwZDNjMTU0ZDM5YmFiIn19fQ==\\");
    private static ItemStack empty = SkullUtil.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzcyMzcwNGE5ZDU5MTBiOWNkNTA1ZGM5OWM3NzliZjUwMzc5Y2I4NDc0NWNjNzE5ZTlmNzg0ZGQ4YyJ9fX0=\\");

    public BetaSuperHat() {
        super("shbeta", b);

        Bukkit.getScheduler().runTaskTimer(PiratePlugin.getPlugin(), () -> {
            i++;
            if(i >= 30)
                i = 0;
        }, 1L, 1L);
    }

    public void tick(Player player) {
        switch (i) {
            case 5:
                player.getInventory().setHelmet(b);
                break;
            case 10:
                player.getInventory().setHelmet(e);
                break;
            case 15:
                player.getInventory().setHelmet(t);
                break;
            case 20:
                player.getInventory().setHelmet(a);
                break;
            case 25:
                player.getInventory().setHelmet(empty);
                break;
            default:
                break;
        }
    }

}
