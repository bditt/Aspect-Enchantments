package net.AspectNetwork.bditt.API.enchantment.event.extra;


import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import net.AspectNetwork.bditt.API.enchantment.event.EnchantmentEvent;
import net.AspectNetwork.bditt.API.enchantment.event.forhelp.Item;
import net.AspectNetwork.bditt.API.enchantment.event.forhelp.Owner;

public abstract class EnchantmentEventWithOwnerAndItem implements EnchantmentEvent, Owner, Item {

  protected final LivingEntity owner;
  private final ItemStack item;

  // Constructor
  public EnchantmentEventWithOwnerAndItem(LivingEntity owner, ItemStack item) {
    this.owner = owner;
    this.item = item;
  }

  // Getters
  final public LivingEntity getOwner() {
    return owner;
  }

  final public ItemStack getItem() {
    return item;
  }
}
