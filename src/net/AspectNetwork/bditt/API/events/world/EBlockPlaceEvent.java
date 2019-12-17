package net.AspectNetwork.bditt.API.events.world;


import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.ItemStack;

import net.AspectNetwork.bditt.API.enchantment.event.EnchantmentEventWithLevel;
import net.AspectNetwork.bditt.API.enchantment.event.extra.EnchantmentEventWithOwnerAndItem;

@EnchantmentEventWithLevel
public class EBlockPlaceEvent extends EnchantmentEventWithOwnerAndItem implements Cancellable {

  private boolean canBuild = true;
  private Block block, blockAgainst, blockPlaced;
  private BlockState blockReplacedState;
  private boolean cancelled = false;

  // Constructor
  public EBlockPlaceEvent(ItemStack item, LivingEntity owner, Block block, Block blockAgainst,
      Block blockPlaced, BlockState blockReplacedState) {
    super(owner, item);
    this.block = block;
    this.blockAgainst = blockAgainst;
    this.blockPlaced = blockPlaced;
    this.blockReplacedState = blockReplacedState;
  }

  public boolean canBuild() {
    return canBuild;
  }

  // Getters
  public Block getBlock() {
    return block;
  }

  public Block getBlockAgainst() {
    return blockAgainst;
  }

  public Block getBlockPlaced() {
    return blockPlaced;
  }

  public BlockState getBlockReplacedState() {
    return blockReplacedState;
  }

  public boolean isCancelled() {
    return cancelled;
  }

  public void setCancelled(boolean cancelled) {
    this.cancelled = cancelled;
  }

  // Setters
  public void setBuild(boolean build) {
    this.canBuild = build;
  }
}
