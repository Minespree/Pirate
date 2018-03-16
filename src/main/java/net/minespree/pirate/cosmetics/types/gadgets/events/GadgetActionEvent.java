package net.minespree.pirate.cosmetics.types.gadgets.events;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import lombok.Getter;
import net.minespree.pirate.cosmetics.types.GadgetCosmetic;

import java.util.List;

/**
 * Called by Gadgets before they perform
 * a specific checkable action such
 * as a block update.
 */
@Getter
public final class GadgetActionEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();

	private final GadgetCosmetic gadget;
	private final Location actionLocation;
	private final ActionType actionType;
	private final List<Block> blocks;

	private boolean cancelled;

	public GadgetActionEvent(GadgetCosmetic gadget, Location actionLocation) {
		this(gadget, actionLocation, ActionType.COSMETIC, null);
	}

	public GadgetActionEvent(GadgetCosmetic gadget, Location actionLocation, ActionType actionType) {
		this(gadget, actionLocation, actionType, null);
	}

	public GadgetActionEvent(GadgetCosmetic gadget, Location actionLocation, ActionType actionType, List<Block> blocks) {
		this.gadget = gadget;
		this.actionLocation = actionLocation;
		this.actionType = actionType;
		this.blocks = blocks;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	/**
	 * Types of action changes that can be performed
	 * by a gadget.
	 */
	public enum ActionType {
		/**
		 * Changes that only appear to the player (e.g. messages/sounds)
		 */
		COSMETIC,
		/**
		 * Changes to blocks in the world
		 */
		BLOCK_CHANGE,
		/**
		 * Changes to the player's, who used the gadget, location
		 */
		PLAYER_TRANSPORT;
	}
}
