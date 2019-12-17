package net.AspectNetwork.bditt.API.events.enchant.unenchant;


import org.bukkit.inventory.ItemStack;

import net.AspectNetwork.bditt.API.enchantment.event.forhelp.Item;


public class EUnenchantItemEvent extends EUnenchantEvent implements Item {

  private final ItemStack item;
  @Override
  public ItemStack getItem() {
    return item;
  }

  public EUnenchantItemEvent(ItemStack item) {
    this.item = item;
  }
}
