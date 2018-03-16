package net.minespree.pirate.cosmetics.types;

import lombok.Getter;
import net.minespree.pirate.cosmetics.Cosmetic;
import net.minespree.pirate.cosmetics.CosmeticType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TitleCosmetic extends Cosmetic {

    @Getter
    private String title;

    private Map<UUID, Slime> names = new HashMap<>(), titles = new HashMap<>();

    public TitleCosmetic(String id, String title) {
        super(id, CosmeticType.TITLE);

        this.title = title;
    }

    @Override
    public void equip(Player player, boolean save) {
        super.equip(player, save);

        /*Slime t = (Slime) player.getWorld().spawnEntity(player.getEyeLocation(), EntityType.SLIME);
        t.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0));
        t.setSize(1);
        t.setCustomName(ChatColor.translateAlternateColorCodes('&', title));
        t.setCustomNameVisible(true);
        Slime name = (Slime) player.getWorld().spawnEntity(player.getEyeLocation(), EntityType.SLIME);
        name.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0));
        name.setSize(1);
        name.setCustomName(player.getName());
        name.setCustomNameVisible(true);
        player.addPassenger(t);
        t.addPassenger(name);
        names.put(player.getUniqueId(), name);
        titles.put(player.getUniqueId(), t);
        EntityPlayer ep = ((CraftPlayer) player).getHandle();
        ep.playerConnection.sendPacket(new PacketPlayOutEntityDestroy(t.getEntityId(), name.getEntityId()));*/
    }

    @Override
    public void unequip(Player player, boolean save) {
        super.unequip(player, save);

        if(names.containsKey(player.getUniqueId())) {
            names.get(player.getUniqueId()).remove();
            names.remove(player.getUniqueId());
        }
        if(titles.containsKey(player.getUniqueId())) {
            titles.get(player.getUniqueId()).remove();
            titles.remove(player.getUniqueId());
        }
    }

}
