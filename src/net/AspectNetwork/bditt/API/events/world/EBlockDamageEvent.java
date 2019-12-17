package net.AspectNetwork.bditt.API.events.world;


import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.ItemStack;

import net.AspectNetwork.bditt.API.enchantment.event.extra.EnchantmentEventWithOwnerAndItem;

public class EBlockDamageEvent extends EnchantmentEventWithOwnerAndItem implements Cancellable {

  private Block block;
  public Block getBlock() {
    return block;
  }

  private boolean cancelled = false;
  public boolean isCancelled() {
    return cancelled;
  }
  public void setCancelled(boolean cancelled) {
    this.cancelled = cancelled;
  }

  public EBlockDamageEvent(LivingEntity owner, ItemStack item, Block block) {
    super(owner, item);
    this.block = block;
  }
}
