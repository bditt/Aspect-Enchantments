package net.AspectNetwork.bditt.API.events.bow;


import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;

import net.AspectNetwork.bditt.API.enchantment.event.EnchantmentEvent;
import net.AspectNetwork.bditt.API.enchantment.event.EnchantmentEventWithLevel;
import net.AspectNetwork.bditt.API.enchantment.event.forhelp.Damage;

@EnchantmentEventWithLevel
public class EArrowHitEvent implements EnchantmentEvent, Cancellable, Damage {

  private final LivingEntity target;
  public LivingEntity getTarget() {
    return target;
  }

  private final Arrow arrow;
  public Arrow getArrow() {
    return arrow;
  }

  private double damage;
  public double getDamage() {
    return damage;
  }
  public void setDamage(double damage) {
    this.damage = damage;
  }

  private boolean cancelled = false;
  public boolean isCancelled() {
    return cancelled;
  }
  public void setCancelled(boolean cancelled) {
    this.cancelled = cancelled;
  }

  // Constructor
  public EArrowHitEvent(LivingEntity target, Arrow arrow, double damage) {
    this.target = target;
    this.arrow = arrow;
    this.damage = damage;
  }
}