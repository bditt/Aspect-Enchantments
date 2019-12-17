package net.AspectNetwork.bditt.API.events.bow;


import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.ItemStack;

import net.AspectNetwork.bditt.API.enchantment.event.EnchantmentEventWithLevel;
import net.AspectNetwork.bditt.API.enchantment.event.extra.EnchantmentEventWithOwnerAndItem;

@EnchantmentEventWithLevel
public class EBowShootEvent extends EnchantmentEventWithOwnerAndItem implements Cancellable {

  private final Entity projectile;
  public Entity getProjectile() {
    return projectile;
  }

  private boolean cancelled = false;
  public boolean isCancelled() {
    return cancelled;
  }
  public void setCancelled(boolean cancelled) {
    this.cancelled = cancelled;
  }

  // Constructor
  public EBowShootEvent(ItemStack item, LivingEntity owner, Entity projectile) {
    super(owner, item);
    this.projectile = projectile;
  }
}
