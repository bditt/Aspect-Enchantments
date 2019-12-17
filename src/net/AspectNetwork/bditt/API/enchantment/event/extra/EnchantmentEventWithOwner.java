package net.AspectNetwork.bditt.API.enchantment.event.extra;


import org.bukkit.entity.LivingEntity;

import net.AspectNetwork.bditt.API.enchantment.event.EnchantmentEvent;
import net.AspectNetwork.bditt.API.enchantment.event.forhelp.Owner;

public abstract class EnchantmentEventWithOwner implements EnchantmentEvent, Owner {

  protected final LivingEntity owner;

  // Constructor
  public EnchantmentEventWithOwner(LivingEntity owner) {
    this.owner = owner;
  }

  // Getters
  final public LivingEntity getOwner() {
    return owner;
  }
}
