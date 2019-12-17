package net.AspectNetwork.bditt.API.events.inventory.hand;


import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import net.AspectNetwork.bditt.API.events.inventory.hand.enums.HandType;

public class EItemInOffHandEvent extends EItemInHandEvent {

  public EItemInOffHandEvent(ItemStack item, LivingEntity owner) {
    super(item, owner, HandType.OFF);
  }
}
