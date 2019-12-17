package net.AspectNetwork.bditt.API.events.enchant.unenchant;


import org.bukkit.entity.Entity;

import net.AspectNetwork.bditt.API.enchantment.event.forhelp.GetEntity;
import net.AspectNetwork.bditt.API.events.enchant.enchant.EEnchantEvent;

public class EUnenchantEntityEvent extends EEnchantEvent implements GetEntity {

  private final Entity entity;
  @Override
  public Entity getEntity() {
    return entity;
  }

  public EUnenchantEntityEvent(Entity entity) {
    this.entity = entity;
  }
}
