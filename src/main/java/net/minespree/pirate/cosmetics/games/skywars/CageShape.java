package net.minespree.pirate.cosmetics.games.skywars;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public interface CageShape {

    int BOTTOM = 0;
    int TOP = 4;
    int MIDDLE_ROWS = 3;

    List<Location> getMiddle(Location centre, int row);

    default List<Location> getTop(Location centre) {
        return getBase(centre, TOP);
    }

    default List<Location> getBottom(Location centre) {
        return getBase(centre, 0);
    }

    default List<Location> getBase(Location centre, int row) {
        List<Location> middle = getMiddle(centre, row);
        middle.add(centre.clone().add(0, row, 0));
        return middle;
    }

    default List<Location> getMiddleRows(Location centre) {
        List<Location> locations = new ArrayList<>();
        for (int i = 1; i < MIDDLE_ROWS + 1; i++) {
            locations.addAll(getMiddle(centre, i));
        }
        return locations;
    }

    default List<Location> getBlocks(Location centre) {
        List<Location> locations = new ArrayList<>();
        locations.addAll(getTop(centre));
        locations.addAll(getMiddleRows(centre));
        locations.addAll(getBottom(centre));
        return locations;
    }

}
