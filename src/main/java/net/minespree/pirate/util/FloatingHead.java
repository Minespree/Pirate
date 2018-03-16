package net.minespree.pirate.util;

import lombok.Getter;
import lombok.Setter;
import net.minespree.wizard.util.SkullUtil;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;

public class FloatingHead {

    @Getter
    private Location location;
    @Getter
    @Setter
    private ItemStack itemStack;
    @Getter
    private ArmorStand armorStand;

    public FloatingHead(Location location, String textureValue) {

        this.location = location;

        this.armorStand = location.getWorld().spawn(location.subtract(0, 2, 0), ArmorStand.class);

        armorStand.setVisible(false);
        armorStand.setGravity(false);

        ItemStack itemStack = SkullUtil.createSkull(textureValue);

        armorStand.setHelmet(itemStack);
    }

    public void teleport(Location location) {

        this.armorStand.teleport(location);
    }

    public void delete() {

        this.armorStand.remove();
    }

    public double getX() {
        return this.getLocation().getX();
    }

    public double getY() {
        return this.getLocation().getY();
    }

    public double getZ() {
        return this.getLocation().getZ();
    }
}