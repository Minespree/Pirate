package net.minespree.pirate.cosmetics.types.gadgets;

import com.google.common.collect.ImmutableList;
import net.minespree.pirate.PiratePlugin;
import net.minespree.pirate.cosmetics.types.GadgetCosmetic;
import net.minespree.pirate.cosmetics.types.gadgets.events.GadgetActionEvent;
import net.minespree.pirate.crates.Rarity;
import org.bukkit.*;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.metadata.FixedMetadataValue;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class FireworkBowGadget extends GadgetCosmetic {

    public FireworkBowGadget() {
        super("gfireworkbow", 5000);

        ammo = new GadgetAmmo();
    }

    List<Color> colorList = ImmutableList.of(Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW, Color.PURPLE);
    Random random = new Random();

    @Override
    public void initialize() {
        super.initialize();
        super.registerLootItem(Rarity.RARE);
    }

    @EventHandler
    public void click(PlayerInteractEvent event) {

        if (event.getAction() != Action.RIGHT_CLICK_AIR) {
            return;
        }

        if (event.getPlayer().getItemInHand().getType() != Material.BOW) {
            return;
        }

        if (useGadget(event.getPlayer())) {

            event.getPlayer().launchProjectile(Arrow.class);

            useGadget(event.getPlayer());
        }
    }

    @EventHandler
    public void onFire(ProjectileLaunchEvent event) {
        if(event.getEntity().getShooter() != null && event.getEntity().getShooter() instanceof Player && useGadget((Player) event.getEntity().getShooter())) {
            event.getEntity().setMetadata("firework", new FixedMetadataValue(PiratePlugin.getPlugin(), ((Player) event.getEntity().getShooter()).getName()));
        }
    }

    @EventHandler
    public void onShoot(ProjectileHitEvent event) {

        if(event.getEntity().getShooter() != null && event.getEntity().getShooter() instanceof Player && event.getEntity().hasMetadata("firework")) {

            Color randomColor = colorList.get(random.nextInt(colorList.size()));

            Projectile projectile = event.getEntity();

            if (!(projectile instanceof Arrow)) {
                return;
            }

            Arrow arrow = (Arrow) projectile;

            if (!(arrow.getShooter() instanceof Player)) {
                return;
            }

            Player player = (Player) arrow.getShooter();
            Location location = arrow.getLocation();

            GadgetActionEvent gadgetActionEvent = new GadgetActionEvent(this, location, GadgetActionEvent.ActionType.COSMETIC);
            Bukkit.getServer().getPluginManager().callEvent(gadgetActionEvent);

            if (gadgetActionEvent.isCancelled()) {
                return;
            }

            Firework firework = player.getWorld().spawn(location, Firework.class);
            FireworkEffect fireworkEffect = FireworkEffect.builder().trail(false).flicker(false).withColor(randomColor).withFade(randomColor).with(FireworkEffect.Type.BALL).build();
            FireworkMeta fireworkMeta = firework.getFireworkMeta();

            fireworkMeta.clearEffects();
            fireworkMeta.addEffect(fireworkEffect);

            Field field;

            try {
                field = fireworkMeta.getClass().getDeclaredField("power");
                field.setAccessible(true);
                field.set(fireworkMeta, -1);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }

            firework.setFireworkMeta(fireworkMeta);
        }
    }

    @Override
    public void use(Player player) {}
}
