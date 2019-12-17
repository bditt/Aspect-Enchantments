package net.AspectNetwork.bditt.API.enchantment.event.extra;


import org.bukkit.inventory.ItemStack;

import net.AspectNetwork.bditt.API.enchantment.event.EnchantmentEvent;
import net.AspectNetwork.bditt.API.enchantment.event.forhelp.Item;

public abstract class EnchantmentEventWithItem implements EnchantmentEvent, Item {

  private final ItemStack item;

  // Constructor
  public EnchantmentEventWithItem(ItemStack item) {
    this.item = item;
  }

  // Getters
  final public ItemStack getItem() {
    return item;
  }
}
