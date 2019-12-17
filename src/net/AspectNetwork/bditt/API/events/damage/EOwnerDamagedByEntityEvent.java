package net.AspectNetwork.bditt.API.events.damage;


import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

import net.AspectNetwork.bditt.API.enchantment.event.EnchantmentEventWithLevel;

@EnchantmentEventWithLevel
public class EOwnerDamagedByEntityEvent extends EOwnerDamagedEvent {

  private Entity damager;
  public Entity getDamager() {
    return damager;
  }

  // Constructor
  public EOwnerDamagedByEntityEvent(ItemStack item, LivingEntity owner, double damage,
      DamageCause cause,
      Type type, Entity damager) {
    super(item, owner, damage, cause, type);
    this.damager = damager;
  }
}
