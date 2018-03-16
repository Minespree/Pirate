package net.minespree.pirate.crates;

import lombok.Getter;
import org.bukkit.ChatColor;

public enum Rarity {

    LEGENDARY(ChatColor.YELLOW, 255, 246, 0, 5),
    EPIC(ChatColor.LIGHT_PURPLE, 247, 74, 232, 20),
    RARE(ChatColor.BLUE, 74, 215, 247, 50),
    COMMON(ChatColor.GRAY, 167, 184, 188, 100);

    @Getter
    private float r;
    @Getter
    private float g;
    @Getter
    private float b;

    @Getter
    private double weight;

    @Getter
    private ChatColor textColor;

    Rarity(ChatColor textColor, float r, float g, float b, double weight) {

        this.r = r;
        this.g = g;
        this.b = b;

        this.weight = weight;

        this.textColor = textColor;
    }
}
