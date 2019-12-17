package net.AspectNetwork.bditt.API.events.enchant.enchant;


import org.bukkit.inventory.ItemStack;

import net.AspectNetwork.bditt.API.enchantment.event.forhelp.Item;


public class EEnchantItemEvent extends EEnchantEvent implements Item {

  private final ItemStack item;
  @Override
  public ItemStack getItem() {
    return item;
  }

  public EEnchantItemEvent(ItemStack item) {
    this.item = item;
  }
}
