package net.AspectNetwork.bditt.API.events.world;


import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import net.AspectNetwork.bditt.API.enchantment.event.EnchantmentEventWithLevel;
import net.AspectNetwork.bditt.API.enchantment.event.extra.EnchantmentEventWithOwnerAndItem;
import net.AspectNetwork.bditt.API.events.inventory.hand.enums.HandType;

@EnchantmentEventWithLevel
public class EInteractEvent extends EnchantmentEventWithOwnerAndItem implements Cancellable {

  private final HandType handType;
  private boolean cancelled = false;
  private Action action;
  private BlockFace blockFace;
  private Block clickedBlock;

  // Constructor
  public EInteractEvent(ItemStack item, LivingEntity owner, Action action, BlockFace blockFace,
      Block clickedBlock, HandType handType) {
    super(owner, item);
    this.action = action;
    this.blockFace = blockFace;
    this.clickedBlock = clickedBlock;
    this.handType = handType;
  }

  // Getters
  public boolean isCancelled() {
    return cancelled;
  }

  public void setCancelled(boolean cancelled) {
    this.cancelled = cancelled;
  }

  public Action getAction() {
    return action;
  }

  public BlockFace getBlockFace() {
    return blockFace;
  }

  public Block getClickedBlock() {
    return clickedBlock;
  }

  public HandType getHandType() {
    return handType;
  }
}
