package net.minespree.pirate.util;

import lombok.Getter;
import net.minespree.pirate.PiratePlugin;
import net.minespree.pirate.crates.LootItem;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class FloatingItem {

    @Getter
    private Location location;
    @Getter
    private ItemStack item;
    @Getter
    private LootItem lootItem;
    @Getter
    private ArmorStand stand;
    @Getter
    private Item itemEntity;

    public FloatingItem(Location location, ItemStack item) {

        this.location = location;
        this.item = item;

        this.stand = location.getWorld().spawn(location.clone().subtract(0, 2, 0), ArmorStand.class);

        itemEntity = location.getWorld().dropItem(location, item);
        itemEntity.setMetadata("floatingItem", new FixedMetadataValue(PiratePlugin.getPlugin(), stand.getEntityId()));

        stand.setVisible(false);
        stand.setPassenger(itemEntity);
        stand.setGravity(false);
    }

    public FloatingItem(Location location, LootItem lootItem) {

        this.location = location;
        this.item = lootItem.getItemStack();
        this.lootItem = lootItem;

        this.stand = location.getWorld().spawn(location.clone().subtract(0, 2, 0), ArmorStand.class);

        itemEntity = location.getWorld().dropItem(location, item);
        itemEntity.setMetadata("floatingItem", new FixedMetadataValue(PiratePlugin.getPlugin(), stand.getEntityId()));

        stand.setVisible(false);
        stand.setPassenger(itemEntity);
        stand.setGravity(false);
    }

    public void teleport(Location location) {

        this.stand.eject();

        this.stand.teleport(location.clone().subtract(0, 2, 0));

        this.stand.setPassenger(this.itemEntity);
    }

    public void delete() {

        this.stand.remove();
        this.itemEntity.remove();
    }

    public void setItem(ItemStack item) {

        this.item = item;
        itemEntity.setItemStack(item);
    }

    public static class ItemSafety implements Listener {

        @EventHandler
        public void pickUp(PlayerPickupItemEvent event) {

            if (!event.getItem().hasMetadata("floatingItem")) {
                return;
            }

            event.setCancelled(true);
        }

        @EventHandler
        public void stack(ItemMergeEvent event) {

            if (event.getEntity().hasMetadata("floatingItem")) {
                event.setCancelled(true);
            }
        }
    }
}
