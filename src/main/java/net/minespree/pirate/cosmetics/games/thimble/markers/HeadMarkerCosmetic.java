package net.minespree.pirate.cosmetics.games.thimble.markers;

import net.minespree.pirate.cosmetics.games.thimble.MarkerCosmetic;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;

import java.util.concurrent.ThreadLocalRandom;

public class HeadMarkerCosmetic extends MarkerCosmetic {

    private final BlockFace[] faces = new BlockFace[] {BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST, BlockFace.EAST,
    BlockFace.NORTH_EAST, BlockFace.NORTH_WEST, BlockFace.SOUTH_EAST, BlockFace.SOUTH_WEST};

    public HeadMarkerCosmetic() {
        super("mk_head");
    }

    @Override
    public void build(Player player, Location location) {
        location.getBlock().setType(Material.SKULL);
        Skull skull = (Skull) location.getBlock().getState();
        skull.setRawData((byte) 1);
        skull.setRotation(faces[ThreadLocalRandom.current().nextInt(faces.length)]);
        skull.setSkullType(SkullType.PLAYER);
        skull.setOwner(player.getName());
        skull.update();
    }
}
