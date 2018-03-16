package net.minespree.pirate.cosmetics.types.gadgets;

import com.google.common.collect.ImmutableList;
import net.minespree.babel.Babel;
import net.minespree.babel.BabelStringMessageType;
import net.minespree.pirate.PiratePlugin;
import net.minespree.pirate.cosmetics.types.GadgetCosmetic;
import net.minespree.pirate.cosmetics.types.gadgets.events.GadgetActionEvent;
import net.minespree.pirate.crates.Rarity;
import net.minespree.wizard.floatingtext.types.PublicFloatingText;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PraisePineappleGadget extends GadgetCosmetic {

    public PraisePineappleGadget() {
        super("gpraisepineapple", 10000);

        ammo = new GadgetAmmo();
    }

    private static ChatColor gold = ChatColor.GOLD;
    private static ChatColor bold = ChatColor.BOLD;
    private static ChatColor green = ChatColor.GREEN;
    private static ChatColor darkGreen = ChatColor.DARK_GREEN;
    private static ChatColor yellow = ChatColor.YELLOW;
    private static ChatColor red = ChatColor.RED;

    private static List<Location> locations = new ArrayList<>();
    private static List<PublicFloatingText> text = new ArrayList<>();

    private static ImmutableList<BabelStringMessageType> messages = ImmutableList.of(Babel.translate("gpraise_pineapple_1"),
            Babel.translate("gpraise_pineapple_2"),
            Babel.translate("gpraise_pineapple_3"),
            Babel.translate("gpraise_pineapple_4"));


    private static Random random = new Random();

    @Override
    public void initialize() {
        super.initialize();
        super.registerLootItem(Rarity.LEGENDARY);
    }

    @EventHandler
    public void cause(PlayerInteractEvent event) {
        if(useGadget(event.getPlayer())) {
            if (event.getAction() != Action.RIGHT_CLICK_AIR) {
                return;
            }

            if (event.getPlayer().getItemInHand().getType() != Material.SPECKLED_MELON) {
                return;
            }

            GadgetActionEvent gadgetActionEvent = new GadgetActionEvent(this, event.getPlayer().getLocation(), GadgetActionEvent.ActionType.COSMETIC);
            Bukkit.getServer().getPluginManager().callEvent(gadgetActionEvent);

            if (gadgetActionEvent.isCancelled()) {
                return;
            }

            populateLocation(event.getPlayer());

            new BukkitRunnable() {

                int i = locations.size();

                @Override
                public void run() {

                    PublicFloatingText floatingText = new PublicFloatingText(locations.get(i - 1));
                    floatingText.setText(randomMessage());
                    text.add(floatingText);

                    event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.MAGMACUBE_WALK, 1, 1);

                    if (i == 1) {

                        this.cancel();
                        locations.clear();

                        new BukkitRunnable() {

                            @Override
                            public void run() {

                                event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.MAGMACUBE_WALK, 1, 1);

                                text.forEach(PublicFloatingText::remove);
                            }

                        }.runTaskLaterAsynchronously(PiratePlugin.getPlugin(), 100L);
                    }

                    i--;
                }

            }.runTaskTimer(PiratePlugin.getPlugin(), 5L, 10L);
        }
    }

    private static BabelStringMessageType randomMessage() {
        return messages.get(random.nextInt(messages.size()));
    }

    private static void populateLocation(Player player) {

        for (int i = 0; i < 7; i++) {

            locations.add(player.getLocation().clone().add(random(3, true), random(2, false) + 1, random(3, true)));
        }
    }

    private static int random(int bound, boolean negative) {

        if (negative) {
            return random.nextInt(bound) * (random.nextBoolean() ? -1 : 1);
        } else {
            return random.nextInt(bound);
        }
    }

    @Override
    public void use(Player player) {

    }
}
