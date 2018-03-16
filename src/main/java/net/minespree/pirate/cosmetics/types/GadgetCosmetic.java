package net.minespree.pirate.cosmetics.types;

import lombok.Getter;
import net.minespree.babel.Babel;
import net.minespree.pirate.cosmetics.Cosmetic;
import net.minespree.pirate.cosmetics.CosmeticManager;
import net.minespree.pirate.cosmetics.CosmeticPlayer;
import net.minespree.pirate.cosmetics.CosmeticType;
import net.minespree.wizard.util.ItemBuilder;
import net.minespree.wizard.util.MessageUtil;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class GadgetCosmetic extends Cosmetic {

    protected final int slot = 4;

    private Map<UUID, Long> cooldowns = new HashMap<>();

    @Getter
    protected GadgetAmmo ammo;

    private long cooldown;

    public GadgetCosmetic(String id, long cooldown) {
        super(id, CosmeticType.GADGET);

        this.cooldown = cooldown;
    }

    @Override
    public void equip(Player player, boolean save) {
        super.equip(player, save);

        setItem(player);
    }

    @Override
    public void unequip(Player player, boolean save) {
        super.unequip(player, save);

        removeItem(player);
    }

    @Override
    public void itemData(Player player, ItemBuilder builder) {
        if(ammo != null) {
            builder.lore(Babel.translate("gadget_ammo"), ammo.getAmmo(player.getUniqueId()));
            builder.lore(" ");
        }
    }

    public boolean useGadget(Player player) {

        if (isEquipped(player) && !CosmeticManager.getCosmeticManager().isDisabled()) {

            if (player.getItemInHand() != null) {

                if (System.currentTimeMillis() >= cooldowns.getOrDefault(player.getUniqueId(), 0L)) {

                    use(player);
                    ammo.use(player.getUniqueId());
                    cooldowns.put(player.getUniqueId(), System.currentTimeMillis() + cooldown);

                    CosmeticPlayer np = CosmeticManager.getCosmeticManager().getPlayer(player);

                    if (np != null) {
                        np.updateCosmetic(this);
                    }

                    return true;

                } else {

                    MessageUtil.sendActionBar(player, Babel.translate("ccooldown")
                            .toString(player, String.format("%.1f", ((double) (cooldowns.get(player.getUniqueId()) - System.currentTimeMillis()) / 1000d))));
                }
            }
        }

        return false;
    }

    public abstract void use(Player player);

    public void setItem(Player player) {
        player.getInventory().setItem(slot, itemBuilder.build(player));
    }

    public void removeItem(Player player) {
        player.getInventory().setItem(slot, null);
    }

    //TODO Once crates are in, add check for ammo.
    public static class GadgetAmmo {

        private Map<UUID, Integer> ammo = new HashMap<>();

        public void use(UUID uuid) {
            if(ammo.containsKey(uuid)) {
                setAmmo(uuid, getAmmo(uuid) - 1);
            }
        }

        public void setAmmo(UUID uuid, int a) {
            ammo.put(uuid, a <= 0 ? 0 : a);
        }

        public int getAmmo(UUID uuid) {
            return ammo.getOrDefault(uuid, 0);
        }

    }
}
