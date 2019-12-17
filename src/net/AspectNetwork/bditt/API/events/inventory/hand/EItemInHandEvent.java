package net.AspectNetwork.bditt.API.events.inventory.hand;


import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import net.AspectNetwork.bditt.API.events.inventory.hand.enums.HandType;

public class EItemInHandEvent extends EItemHandEvent {

  public EItemInHandEvent(ItemStack item, LivingEntity owner, HandType handType) {
    super(item, owner, handType);
  }
}
