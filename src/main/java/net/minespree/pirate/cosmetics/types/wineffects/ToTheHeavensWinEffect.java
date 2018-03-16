package net.minespree.pirate.cosmetics.types.wineffects;

import com.google.common.collect.Lists;
import net.minespree.pirate.PiratePlugin;
import net.minespree.pirate.cosmetics.CosmeticType;
import net.minespree.pirate.cosmetics.types.WinEffectCosmetic;
import net.minespree.pirate.util.RealEulerAngle;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

/**
 * @since 22/09/2017
 */
public class ToTheHeavensWinEffect extends WinEffectCosmetic {

    private List<PossiblePose> possiblePoses = Arrays.asList(
            new PossiblePose(new RealEulerAngle(325f, 0f, 0f), new RealEulerAngle(160f,0f,49f), new RealEulerAngle(159f,0f,324f), new RealEulerAngle(329f,0f,330f), new RealEulerAngle(330f,0f,26f)),
            new PossiblePose(new RealEulerAngle(328f,0f,0f), new RealEulerAngle(203f,356f,0f), new RealEulerAngle(203f,7f,0f), new RealEulerAngle(27f,27f,0f), new RealEulerAngle(27f,321f,0f)),
            new PossiblePose(new RealEulerAngle(328f,0f,0f), new RealEulerAngle(203f,356f,0f), new RealEulerAngle(19f,7f,0f), new RealEulerAngle(27f,27f,0f), new RealEulerAngle(27f,321f,0f)),
            new PossiblePose(new RealEulerAngle(328f,0f,0f), new RealEulerAngle(25f,36f,0f), new RealEulerAngle(25f,317f,0f), new RealEulerAngle(27f,27f,0f), new RealEulerAngle(27f,321f,0f))
    );

    public ToTheHeavensWinEffect() {
        super("to_the_heavens", CosmeticType.SW_WIN_EFFECT);
    }

    @Override
    public void use(Player... players) {
        Arrays.stream(players).forEach(player -> {
            int amount = ThreadLocalRandom.current().nextInt(45, 60);
            IntStream.range(0, amount).forEach(i -> new Animator(player));
        });
    }

    public class Animator {

        Animator(Player player) {
            int randX = ThreadLocalRandom.current().nextInt(-20, 20);
            int randZ = ThreadLocalRandom.current().nextInt(-25, 25);
            int randY = ThreadLocalRandom.current().nextInt(-5, 10);

            ArmorStand stand = player.getWorld().spawn(player.getLocation().add(randX, randY, randZ), ArmorStand.class);
            int initialY = stand.getLocation().getBlockY();

            stand.setGravity(false);
            stand.setBasePlate(false);
            stand.setArms(true);
            stand.setSmall(ThreadLocalRandom.current().nextBoolean());

            PossiblePose possiblePose = possiblePoses.get(ThreadLocalRandom.current().nextInt(possiblePoses.size()));
            possiblePose.apply(stand);

            ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (byte) SkullType.PLAYER.ordinal());
            SkullMeta meta = (SkullMeta) head.getItemMeta();
            meta.setOwner(player.getName());
            head.setItemMeta(meta);

            stand.setHelmet(head);

            ItemStack[] stacks = new RandomLeatherArmorGen().generate();

            stand.setChestplate(stacks[0]);
            stand.setLeggings(stacks[1]);
            stand.setBoots(stacks[2]);

            new BukkitRunnable() {
                int ticks = 0;
                float currentYaw = 0f;

                boolean reverse = ThreadLocalRandom.current().nextBoolean();
                int maxDiff = ThreadLocalRandom.current().nextInt(25, 45);

                @Override
                public void run() {
                    ticks++;

                    Location location = stand.getLocation();
                    location.setYaw((reverse ? (currentYaw += 10) : (currentYaw -= 10)));
                    location.setY(location.getY() + 0.25);
                    if (location.getY() - initialY > maxDiff) {
                        stand.getEquipment().clear();
                        stand.remove();

                        location.getWorld().createExplosion(location, 1f, false);
                        cancel();
                        return;
                    }
                    stand.teleport(location);
                }
            }.runTaskTimer(PiratePlugin.getPlugin(), 0L, 1L);
        }
    }

    public class RandomLeatherArmorGen {

        private Color[] colors = new Color[]{Color.BLACK, Color.WHITE, Color.MAROON, Color.BLUE, Color.RED, Color.GREEN, Color.AQUA};

        ItemStack[] generate() {
            Color color = colors[ThreadLocalRandom.current().nextInt(colors.length)];

            List<ItemStack> items = Lists.newArrayList();
            ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
            LeatherArmorMeta meta = (LeatherArmorMeta) chest.getItemMeta();
            meta.setColor(color);
            chest.setItemMeta(meta);

            ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
            LeatherArmorMeta meta1 = (LeatherArmorMeta) leggings.getItemMeta();
            meta1.setColor(color);
            leggings.setItemMeta(meta1);

            ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
            LeatherArmorMeta meta2 = (LeatherArmorMeta) boots.getItemMeta();
            meta2.setColor(color);
            boots.setItemMeta(meta2);

            items.addAll(Arrays.asList(chest, leggings, boots));

            return items.toArray(new ItemStack[items.size()]);
        }
    }

    public class PossiblePose {

        private RealEulerAngle headPose, leftArmPose, rightArmPose, leftLegPose, rightLegPose;

        PossiblePose(RealEulerAngle headPose, RealEulerAngle leftArmPose, RealEulerAngle rightArmPose, RealEulerAngle leftLegPose, RealEulerAngle rightLegPose) {
            this.headPose = headPose;
            this.leftArmPose = leftArmPose;
            this.rightArmPose = rightArmPose;
            this.leftLegPose = leftLegPose;
            this.rightLegPose = rightLegPose;
        }

        void apply(ArmorStand stand) {
            stand.setHeadPose(headPose);
            stand.setLeftArmPose(leftArmPose);
            stand.setRightArmPose(rightArmPose);
            stand.setLeftLegPose(leftLegPose);
            stand.setRightLegPose(rightLegPose);
        }
    }
}
