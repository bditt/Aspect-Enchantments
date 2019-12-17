package net.AspectNetwork.bditt.API.events.damage;


import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

import net.AspectNetwork.bditt.API.enchantment.event.EnchantmentEventWithLevel;
import net.AspectNetwork.bditt.API.enchantment.event.extra.EnchantmentEventWithOwnerAndItem;
import net.AspectNetwork.bditt.API.enchantment.event.forhelp.Damage;

@EnchantmentEventWithLevel
public class EOwnerDamagedEvent extends EnchantmentEventWithOwnerAndItem implements Cancellable,
    Damage {

  private final DamageCause cause;
  public DamageCause getCause() {
    return cause;
  }

  private final Type type;
  public Type getType() {
    return type;
  }

  private boolean cancelled = false;
  public boolean isCancelled() {
    return cancelled;
  }
  public void setCancelled(boolean cancelled) {
    this.cancelled = cancelled;
  }

  private double damage;
  public double getDamage() {
    return damage;
  }
  public void setDamage(double damage) {
    this.damage = damage;
  }

  // Constructor
  public EOwnerDamagedEvent(ItemStack item, LivingEntity owner, double damage, DamageCause cause,
      Type type) {
    super(owner, item);
    this.damage = damage;
    this.cause = cause;
    this.type = type;
  }

  public enum Type {
    IN_HAND,
    ARMOR,
    SHIELD,
    HORSE_ARMOR
  }
}
