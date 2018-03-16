package net.minespree.pirate.cosmetics.types.superhats;

import net.minespree.pirate.PiratePlugin;
import net.minespree.pirate.cosmetics.types.SuperHatCosmetic;
import net.minespree.pirate.crates.Rarity;
import net.minespree.wizard.util.SkullUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AlphaSuperHat extends SuperHatCosmetic {

    private int i;
    private static ItemStack a = SkullUtil.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODA3M2FlNTQ3ZTZkYWE5ZDJkYzhjYjkwZTc4ZGQxYzcxY2RmYWRiNzQwMWRjMTY3ZDE2ODE5YjE3MzI4M2M1MSJ9fX0=\\");
    private static ItemStack l = SkullUtil.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTk3NDYzZDcxODFlODNhMTQzYTZjZWQxYTFmNzdmNjZkMWYyOGU2ZjI3MmZlOGNkOTVlN2ZiODllYTBkYzY5In19fQ==\\");
    private static ItemStack p = SkullUtil.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTc1Y2M1ZDQ3ZDVhYTBhNzhkMjRmZTQ5NDI4ZTNhNGI1MWM5MjE3NTQ0ZmEwN2JjNGNkNzdjMmU1Y2I5NmEifX19\\");
    private static ItemStack h = SkullUtil.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmMyODk5Zjk1NmQ5Yzg3ZGExMThiYjgxM2FhODIyNmY1ZjMzOTU5MWVlZTVmYmY5YmFmY2VlNTljZDllMWRmIn19fQ==\\");
    private static ItemStack empty = SkullUtil.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzUxMTM3ZTExNDQzYThmYmIwNWZjZDNjY2MxYWY5YmQyMzAzOTE4ZjM1NDQ4MTg1ZTNlZDk2ZWYxODRkYSJ9fX0=\\");

    public AlphaSuperHat() {
        super("shalpha", a);

        i = 0;

        Bukkit.getScheduler().runTaskTimer(PiratePlugin.getPlugin(), this::tick, 5L, 5L);
    }

    public void tick() {
        if(i >= 6)
            i = 0;

        super.tick();
        i++;
    }

    public void tick(Player player) {
        switch (i) {
            case 0:
                player.getInventory().setHelmet(a);
                break;
            case 1:
                player.getInventory().setHelmet(l);
                break;
            case 2:
                player.getInventory().setHelmet(p);
                break;
            case 3:
                player.getInventory().setHelmet(h);
                break;
            case 4:
                player.getInventory().setHelmet(a);
                break;
            case 5:
                player.getInventory().setHelmet(empty);
                break;
            default:
                break;
        }
    }
}
