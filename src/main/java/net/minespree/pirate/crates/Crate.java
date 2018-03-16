package net.minespree.pirate.crates;

import lombok.Getter;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockAction;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import net.minespree.pirate.PiratePlugin;
import net.minespree.pirate.util.ArcEffect;
import net.minespree.pirate.util.FloatingHead;
import net.minespree.pirate.util.FloatingItem;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
import org.bukkit.entity.Player;
import org.bukkit.material.Chest;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class Crate {

    public static List<Crate> cratesInUse = new ArrayList<>();

    @Getter
    private Location location;
    private Block block;
    private FloatingHead floatingHead;
    private String chestTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmY2OGQ1MDliNWQxNjY5Yjk3MWRkMWQ0ZGYyZTQ3ZTE5YmNiMWIzM2JmMWE3ZmYxZGRhMjliZmM2ZjllYmYifX19";

    private List<Location> pedestals = new ArrayList<>();
    private Player player;

    public Crate(Block block, Player player) {

        this.location = block.getLocation().add(0.5, 0.5, 0.5);
        this.block = block;
        this.player = player;

        this.pedestals = getPedestals(this.block);

        cratesInUse.add(this);
    }

    public void open() {

        this.playChestOpen(this.block);

        this.playRisingChest();

        this.animation();
    }

    private void playRisingChest() {

        floatingHead = new FloatingHead(this.location, chestTexture);

        new BukkitRunnable() {

            double i = 0;

            float yaw = 0;

            Location location;

            @Override
            public void run() {

                if (yaw == 550) {

                    new BukkitRunnable() {

                        int i  = 0;

                        @Override
                        public void run() {

                            if (i > 10) {

                                floatingHead.delete();

                                PacketPlayOutWorldParticles worldParticles2 = new PacketPlayOutWorldParticles(EnumParticle.EXPLOSION_HUGE, true, (float) floatingHead.getX(), (float) (floatingHead.getY() + 4), (float) floatingHead.getZ(), 0, 0, 0, 0, 1);
                                floatingHead.getLocation().getWorld().getPlayers().forEach(player1 -> ((CraftPlayer) player1).getHandle().playerConnection.sendPacket(worldParticles2));

                                this.cancel();
                                return;
                            }

                            PacketPlayOutWorldParticles worldParticles3 = new PacketPlayOutWorldParticles(EnumParticle.ENCHANTMENT_TABLE, true, (float) floatingHead.getX(), (float) (floatingHead.getY() + 4), (float) floatingHead.getZ(), 0, 0, 0, 0, 1);
                            floatingHead.getLocation().getWorld().getPlayers().forEach(player1 -> ((CraftPlayer) player1).getHandle().playerConnection.sendPacket(worldParticles3));

                            i++;
                        }

                    }.runTaskTimerAsynchronously(PiratePlugin.getPlugin(), 0L, 2L);

                    this.cancel();
                    return;
                }

                location = new Location(floatingHead.getLocation().getWorld(), floatingHead.getX(), floatingHead.getY() + i, floatingHead.getZ(), yaw, 0);

                floatingHead.teleport(location);

                i += 0.05;
                yaw += 10;
            }

        }.runTaskTimerAsynchronously(PiratePlugin.getPlugin(), 0L, 1L);
    }

    private void animation() {

        Location spawnLocation = floatingHead.getLocation().clone().add(0, 4.5, 0);

        FloatingItem item1 = new FloatingItem(location, LootItem.getRandomItem());
        FloatingItem item2 = new FloatingItem(location, LootItem.getRandomItem());
        FloatingItem item3 = new FloatingItem(location, LootItem.getRandomItem());

        List<ArcEffect> arcEffects = new ArrayList<>();

        arcEffects.add(new ArcEffect(EnumParticle.REDSTONE, item1, item1.getLootItem().getRarity().getR(), item1.getLootItem().getRarity().getG(), item1.getLootItem().getRarity().getB(), 1, 2, 30, location.getWorld().getPlayers(), pedestals.get(0), spawnLocation));
        arcEffects.add(new ArcEffect(EnumParticle.REDSTONE, item2, item2.getLootItem().getRarity().getR(), item2.getLootItem().getRarity().getG(), item2.getLootItem().getRarity().getB(), 1, 2, 30, location.getWorld().getPlayers(), pedestals.get(1), spawnLocation));
        arcEffects.add(new ArcEffect(EnumParticle.REDSTONE, item3, item3.getLootItem().getRarity().getR(), item3.getLootItem().getRarity().getG(), item3.getLootItem().getRarity().getB(), 1, 2, 30, location.getWorld().getPlayers(), pedestals.get(2), spawnLocation));

        new BukkitRunnable() {

            int i = 0;

            @Override
            public void run() {

                if (i == arcEffects.size()) {

                    pedestals.clear();
                    arcEffects.clear();

                    this.cancel();
                    return;
                }

                arcEffects.get(i).run();

                i++;
            }

        }.runTaskTimerAsynchronously(PiratePlugin.getPlugin(), 95L, 1L);

        new BukkitRunnable() {

            @Override
            public void run() {

                item1.delete();
                item2.delete();
                item3.delete();
            }

        }.runTaskLaterAsynchronously(PiratePlugin.getPlugin(), 240L);
    }

    private void playChestOpen(Block block) {

        new BukkitRunnable() {

            int i = 0;

            @Override
            public void run() {

                if (i == 10) {

                    PacketPlayOutBlockAction blockAction = new PacketPlayOutBlockAction(new BlockPosition(block.getX(), block.getY(), block.getZ()), CraftMagicNumbers.getBlock(block), 1, 0);
                    block.getLocation().getWorld().getPlayers().forEach(player1 -> ((CraftPlayer) player1).getHandle().playerConnection.sendPacket(blockAction));

                    this.cancel();
                    return;
                }

                PacketPlayOutBlockAction blockAction = new PacketPlayOutBlockAction(new BlockPosition(block.getX(), block.getY(), block.getZ()), CraftMagicNumbers.getBlock(block), 1, 1);
                block.getLocation().getWorld().getPlayers().forEach(player1 -> ((CraftPlayer) player1).getHandle().playerConnection.sendPacket(blockAction));

                i++;
            }

        }.runTaskTimerAsynchronously(PiratePlugin.getPlugin(), 0L, 3L);
    }

    private static List<Location> getPedestals(Block block) {

        Chest chest = new Chest(0, block.getData());

        BlockFace blockFace = chest.getFacing();

        Location blockLoc = block.getLocation().clone().add(0, 0.2, 0);

        List<Location> pedestals = new ArrayList<>();

        switch (blockFace) {
            case NORTH:

                //Z + 1
                //X +- 2
                pedestals.add(blockLoc.clone().add(0.5, 1, 1.5));
                pedestals.add(blockLoc.clone().add(2.5, 1, 0.5));
                pedestals.add(blockLoc.clone().add(-1.5, 1, 0.5));

                break;
            case SOUTH:

                //Z - 1
                //X +- 2
                pedestals.add(blockLoc.clone().add(0.5, 1, -0.5));
                pedestals.add(blockLoc.clone().add(2.5, 1, 0.5));
                pedestals.add(blockLoc.clone().add(-1.5, 1, 0.5));

                break;
            case EAST:

                //X - 1
                //Z +- 2
                pedestals.add(blockLoc.clone().add(-0.5, 1, 0.5));
                pedestals.add(blockLoc.clone().add(0.5, 1, 2.5));
                pedestals.add(blockLoc.clone().add(0.5, 1, -1.5));

                break;
            case WEST:

                //X + 1
                //Z +- 2
                pedestals.add(blockLoc.clone().add(1.5, 1, 0.5));
                pedestals.add(blockLoc.clone().add(0.5, 1, 2.5));
                pedestals.add(blockLoc.clone().add(0.5, 1, -1.5));

                break;
            default:

                //TODO : Send message saying something's wrong.
                break;
        }

        return pedestals;
    }
}