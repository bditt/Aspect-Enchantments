package net.AspectNetwork.bditt.API.events.inventory.hand;


import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.ItemStack;

import net.AspectNetwork.bditt.API.enchantment.event.EnchantmentEventWithLevel;
import net.AspectNetwork.bditt.API.enchantment.event.extra.EnchantmentEventWithOwnerAndItem;
import net.AspectNetwork.bditt.API.events.inventory.hand.enums.HandType;

@EnchantmentEventWithLevel
public class EItemHandEvent extends EnchantmentEventWithOwnerAndItem implements Cancellable {

  private final HandType handType;
  public HandType getHandType() {
    return this.handType;
  }

  private boolean cancelled = false;
  public boolean isCancelled() {
    return cancelled;
  }
  public void setCancelled(boolean cancelled) {
    this.cancelled = cancelled;
  }

  public EItemHandEvent(ItemStack item, LivingEntity owner, HandType handType) {
    super(owner, item);
    this.handType = handType;
  }
}
