package net.minespree.pirate.cosmetics.games.skywars.cages;

import com.google.common.collect.Lists;
import net.minespree.pirate.cosmetics.games.skywars.CageCosmetic;
import net.minespree.pirate.cosmetics.games.skywars.CageShape;
import net.minespree.pirate.cosmetics.games.skywars.CageShapeType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.material.Button;
import org.bukkit.material.TrapDoor;

import java.util.Arrays;
import java.util.List;

public class DuckCageCosmetic extends CageCosmetic {

    public DuckCageCosmetic() {
        super("cg_rubberduck");
    }

    @Override
    public List<Location> build(Location centre, BlockFace direction) {
        List<Location> locations = Lists.newArrayList();

        CageShape shape = CageShapeType.DIAMOND.getShape();

        List<Location> middle = shape.getMiddleRows(centre);
        Location top = centre.clone().add(0, 3, 0);
        middle.add(top);
        Location bottom = centre.clone().subtract(0, 1, 0);
        Location base = centre.clone().add(0, 1, 0);
        Location head, button1, button2, trapdoor, base1, base2, base3, base4;
        switch (direction) {
            case NORTH:
                head = base.clone().subtract(0, 0, 1);
                trapdoor = head.clone().subtract(0, 0, 1);
                break;
            case EAST:
                head = base.clone().add(1, 0, 0);
                trapdoor = head.clone().add(1, 0, 0);
                break;
            case WEST:
                head = base.clone().subtract(1, 0, 0);
                trapdoor = head.clone().subtract(1, 0, 0);
                break;
            default:
                head = base.clone().add(0, 0, 1);
                trapdoor = head.clone().add(0, 0, 1);
                break;
        }
        if(direction == BlockFace.NORTH || direction == BlockFace.SOUTH) {
            button1 = head.clone().subtract(1, 0, 0);
            button2 = head.clone().add(1, 0, 0);
            base1 = centre.clone().add(0, 0, 1);
            base2 = centre.clone().subtract(0, 0, 1);
            base3 = centre.clone().add(1, 0, 0);
            base4 = centre.clone().subtract(1, 0, 0);
        } else {
            button1 = head.clone().subtract(0, 0, 1);
            button2 = head.clone().add(0, 0, 1);
            base1 = centre.clone().add(1, 0, 0);
            base2 = centre.clone().subtract(1, 0, 0);
            base3 = centre.clone().add(0, 0, 1);
            base4 = centre.clone().subtract(0, 0, 1);
        }

        bottom.getBlock().setType(Material.BARRIER);
        centre.getBlock().setType(Material.WATER);
        setYellow(base1, false);
        setYellow(base2, false);
        setYellow(base3, true);
        base4.getBlock().setType(Material.GOLD_BLOCK);
        middle.forEach(location -> location.getBlock().setType(Material.BARRIER));
        setYellow(head, true);
        trapdoor.getBlock().setType(Material.TRAP_DOOR);
        button1.getBlock().setType(Material.WOOD_BUTTON);
        button2.getBlock().setType(Material.WOOD_BUTTON);
        if(direction == BlockFace.WEST) {
            setButtonDirection(button1, BlockFace.NORTH);
            setButtonDirection(button2, BlockFace.SOUTH);
            setTrapdoor(trapdoor, BlockFace.WEST);
        } else if(direction == BlockFace.EAST) {
            setButtonDirection(button1, BlockFace.NORTH);
            setButtonDirection(button2, BlockFace.SOUTH);
            setTrapdoor(trapdoor, BlockFace.EAST);
        } else if(direction == BlockFace.NORTH) {
            setButtonDirection(button1, BlockFace.WEST);
            setButtonDirection(button2, BlockFace.EAST);
            setTrapdoor(trapdoor, BlockFace.NORTH);
        } else {
            setButtonDirection(button1, BlockFace.WEST);
            setButtonDirection(button2, BlockFace.EAST);
            setTrapdoor(trapdoor, BlockFace.SOUTH);
        }

        locations.addAll(Arrays.asList(centre, bottom, button1, button2, trapdoor, base1, base2, base3, base4));
        locations.addAll(middle);
        return locations;
    }

    private void setYellow(Location location, boolean wool) {
        if(wool) {
            location.getBlock().setType(Material.WOOL);
        } else location.getBlock().setType(Material.STAINED_CLAY);
        location.getBlock().setData((byte) 4);
    }

    private void setButtonDirection(Location button, BlockFace face) {
        BlockState state = button.getBlock().getState();
        Button b2 = (Button) state.getData();
        b2.setFacingDirection(face);
        state.update();
    }

    private void setTrapdoor(Location trapdoor, BlockFace face) {
        BlockState state = trapdoor.getBlock().getState();
        TrapDoor door = (TrapDoor) state.getData();
        door.setFacingDirection(face);
        state.update();
    }

}
