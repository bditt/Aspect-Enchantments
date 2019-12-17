package net.AspectNetwork.bditt.API.events.bow;


import org.bukkit.entity.Arrow;

import net.AspectNetwork.bditt.API.enchantment.event.EnchantmentEvent;
import net.AspectNetwork.bditt.API.enchantment.event.EnchantmentEventWithLevel;

@EnchantmentEventWithLevel
public class EArrowLandEvent implements EnchantmentEvent {

  private final Arrow arrow;
  public Arrow getArrow() {
    return arrow;
  }

  // Constructor
  public EArrowLandEvent(Arrow arrow) {
    this.arrow = arrow;
  }
}
