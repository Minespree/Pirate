package net.minespree.pirate.cosmetics.games.thimble.successful;

import net.minespree.babel.Babel;
import net.minespree.babel.ComplexBabelMessage;
import net.minespree.pirate.PiratePlugin;
import net.minespree.pirate.cosmetics.games.thimble.LandCosmetic;
import net.minespree.wizard.floatingtext.types.PublicFloatingText;
import net.minespree.wizard.util.Chat;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.atomic.AtomicInteger;

public class SadLandCosmetic extends LandCosmetic {

    private static final ComplexBabelMessage PLEASE_CLAP_1 = new ComplexBabelMessage().append(Chat.GRAY).append(Babel.translate("please_clap"));
    private static final ComplexBabelMessage PLEASE_CLAP_2 = new ComplexBabelMessage().append(Chat.RED).append(Babel.translate("please_clap"));

    public SadLandCosmetic() {
        super("sl_sad");
    }

    @Override
    public void land(Player player, Location location) {
        AtomicInteger tick = new AtomicInteger(0);
        PublicFloatingText text = new PublicFloatingText(location.clone().add(0.5, 0, 0.5));
        text.show();
        new BukkitRunnable() {
            @Override
            public void run() {
                if(tick.get() < 5) {
                    if(tick.get() < 4) {
                        Bukkit.getOnlinePlayers().forEach(p -> p.playSound(p.getLocation(), Sound.NOTE_BASS, 2F, getPitch(tick.get())));
                    }
                } else if(tick.get() >= 10) {
                    text.remove();
                    cancel();
                    return;
                }
                if(tick.get() % 2 == 0) {
                    text.setText(PLEASE_CLAP_1);
                } else {
                    text.setText(PLEASE_CLAP_2);
                }
                tick.incrementAndGet();
            }
        }.runTaskTimer(PiratePlugin.getPlugin(), 0L, 7L);
    }

    public float getPitch(int tick) {
        if(tick == 0) {
            return 1.8F;
        } else if(tick == 1) {
            return 1.4F;
        } else if(tick == 2) {
            return 1.2F;
        } else {
            return 0.5F;
        }
    }

}
