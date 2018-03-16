package net.minespree.pirate.cosmetics.types.gadgets.fishslapper;

import net.minespree.babel.Babel;
import net.minespree.feather.data.gamedata.GameRegistry;
import net.minespree.feather.player.NetworkPlayer;
import net.minespree.feather.player.stats.persitent.PersistentStatistics;
import net.minespree.pirate.PiratePlugin;
import net.minespree.pirate.cosmetics.types.GadgetCosmetic;
import net.minespree.pirate.cosmetics.types.gadgets.events.GadgetActionEvent;
import net.minespree.pirate.crates.Rarity;
import net.minespree.wizard.util.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class FishSlapperGadget extends GadgetCosmetic {

    private static HashMap<UUID, FishSlapperData> fishSlapperData = new HashMap<>();

    public FishSlapperGadget() {
        super("gfishslapper", 10000);

        ammo = null;
    }

    @EventHandler
    public void onSlap(PlayerInteractAtEntityEvent event) {

        if (!(event.getRightClicked() instanceof Player)) {
            return;
        }

        if(useGadget( event.getPlayer())) {

            Player damager = event.getPlayer();
            Player player = (Player) event.getRightClicked();

            if (damager.getItemInHand().getType() != Material.RAW_FISH) {
                return;
            }

            GadgetActionEvent gadgetActionEvent = new GadgetActionEvent(this, damager.getLocation(), GadgetActionEvent.ActionType.COSMETIC);
            Bukkit.getServer().getPluginManager().callEvent(gadgetActionEvent);

            if (gadgetActionEvent.isCancelled()) {
                return;
            }

            if (fishSlapperData.containsKey(damager.getUniqueId())) {

                if (fishSlapperData.get(damager.getUniqueId()).getPlayers().contains(player)) {

                    return;
                }

                update(damager, player, true);
                damager.playSound(damager.getLocation(), Sound.SLIME_ATTACK, 1, 1);
                damager.hidePlayer(player);

            } else {

                this.useGadget(damager);

                NetworkPlayer networkPlayer = NetworkPlayer.of(player);
                PersistentStatistics persistentStatistics = networkPlayer.getPersistentStats();

                persistentStatistics.getValue(PersistentStatistics.constructKey(GameRegistry.Type.GLOBAL, PersistentStatistics.PersistableData.INTEGERS, "fish_slap_highscore"), o -> {

                    if (o instanceof Integer) {
                        damager.sendMessage(Babel.translate("gfish_slapper_current_highscore").toString(damager, String.valueOf(o)));
                    }
                });

                fishSlapperData.put(damager.getUniqueId(), new FishSlapperData(new ArrayList<>(), 30, new BukkitRunnable() {

                    @Override
                    public void run() {

                        //Increment
                        fishSlapperData.get(damager.getUniqueId()).setCooldown(fishSlapperData.get(damager.getUniqueId()).getCooldown() - 1);
                        update(damager, player, false);

                        if (fishSlapperData.get(damager.getUniqueId()).getCooldown() == 0) {

                            damager.playSound(damager.getLocation(), Sound.LEVEL_UP, 1, 1);

                            for (Player hitPlayer : fishSlapperData.get(damager.getUniqueId()).getPlayers()) {
                                damager.showPlayer(hitPlayer);
                            }

                            MessageUtil.sendActionBar(damager, Babel.translate("gfish_slapper_people_slapped")
                                    .toString(damager, String.valueOf(fishSlapperData.get(damager.getUniqueId()).getPlayers().size() + 1)));

                            NetworkPlayer networkPlayer = NetworkPlayer.of(player);
                            PersistentStatistics persistentStatistics = networkPlayer.getPersistentStats();

                            persistentStatistics.getValue(PersistentStatistics.constructKey(GameRegistry.Type.GLOBAL, PersistentStatistics.PersistableData.INTEGERS, "fish_slap_highscore"), o -> {

                                if (o instanceof Integer) {

                                    Integer score = (Integer) o;

                                    if (score <= fishSlapperData.get(damager.getUniqueId()).getPlayers().size() + 1) {

                                        setHighScore(damager, fishSlapperData.get(damager.getUniqueId()).getPlayers().size());

                                        MessageUtil.sendActionBar(damager, Babel.translate("gfish_slapper_new_hishscore")
                                                .toString(damager, String.valueOf(fishSlapperData.get(damager.getUniqueId()).getPlayers().size() + 1)));

                                    }
                                }
                            });

                            fishSlapperData.remove(damager.getUniqueId());

                            this.cancel();
                        }
                    }
                }));

                fishSlapperData.get(damager.getUniqueId()).getRunnable().runTaskTimer(PiratePlugin.getPlugin(), 0L, 20L);
            }
        }
    }

    private static void update(Player player, Player hit, boolean addEntity) {

        FishSlapperData fishSlapper = fishSlapperData.get(player.getUniqueId());

        if (addEntity) fishSlapper.getPlayers().add(hit);

        MessageUtil.sendActionBar(player, Babel.translate("gfish_slapper_actionbar")
                .toString(player, String.valueOf(fishSlapper.getPlayers().size() + 1), String.valueOf(fishSlapper.getCooldown())));
    }

    public static void setHighScore(Player player, int highscore) {

        NetworkPlayer networkPlayer = NetworkPlayer.of(player);
        PersistentStatistics persistentStatistics = networkPlayer.getPersistentStats();

        persistentStatistics.getIntegerStatistics(GameRegistry.Type.GLOBAL).set("fish_slap_highscore", highscore);

        persistentStatistics.persist();
    }

    @Override
    public void use(Player player) {

    }
}
