package net.AspectNetwork.bditt.API.events.inventory.hand;


import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import net.AspectNetwork.bditt.API.events.inventory.hand.enums.HandType;

public class EItemNotInHandEvent extends EItemHandEvent {

  public EItemNotInHandEvent(ItemStack item, LivingEntity owner, HandType handType) {
    super(item, owner, handType);
  }
}
