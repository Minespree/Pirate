package net.minespree.pirate.cosmetics.games.thimble.markers;

import net.minespree.pirate.cosmetics.games.thimble.MarkerCosmetic;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SignMarkerCosmetic extends MarkerCosmetic {

    private List<String> messages;

    public SignMarkerCosmetic(String id, List<String> messages) {
        super(id);

        this.messages = messages;
    }

    @Override
    public void build(Player player, Location location) {
        location.getBlock().setType(Material.SIGN_POST);
        Sign sign = (Sign) location.getBlock().getState();

        sign.setRawData((byte) ThreadLocalRandom.current().nextInt(0, 15));
        for (int i = 0; i < messages.size(); i++) {
            String message = messages.get(i);
            sign.setLine(i, message.replaceAll("%%p%%", player.getName()));
        }
        sign.update();
    }
}
