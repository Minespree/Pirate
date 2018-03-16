package net.minespree.pirate.cosmetics.games.skywars;

import com.google.common.collect.Lists;
import lombok.Getter;

public enum CageShapeType {

    DIAMOND((centre, row) -> Lists.newArrayList(centre.clone().add(1, row, 0),
            centre.clone().add(0, row, 1),
            centre.clone().add(-1, row, 0),
            centre.clone().add(0, row, -1))),
    RECTANGLE((centre, row) -> Lists.newArrayList(centre.clone().add(1, row, 0),
            centre.clone().add(0, row, 1),
            centre.clone().add(-1, row, 0),
            centre.clone().add(0, row, -1),
            centre.clone().add(1, row, -1),
            centre.clone().add(1, row, 1),
            centre.clone().add(-1, row, -1),
            centre.clone().add(-1, row, 1))),
    ;

    @Getter
    private CageShape shape;

    CageShapeType(CageShape shape) {
        this.shape = shape;
    }

}
