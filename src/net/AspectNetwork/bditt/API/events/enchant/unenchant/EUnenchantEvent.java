package net.AspectNetwork.bditt.API.events.enchant.unenchant;


import org.bukkit.event.Cancellable;

import net.AspectNetwork.bditt.API.enchantment.event.EnchantmentEvent;
import net.AspectNetwork.bditt.API.enchantment.event.EnchantmentEventWithLevel;

@EnchantmentEventWithLevel
public class EUnenchantEvent implements EnchantmentEvent, Cancellable {

  boolean cancelled = false;
  @Override
  public boolean isCancelled() {
    return cancelled;
  }
  @Override
  public void setCancelled(boolean cancelled) {
    this.cancelled = cancelled;
  }
}
