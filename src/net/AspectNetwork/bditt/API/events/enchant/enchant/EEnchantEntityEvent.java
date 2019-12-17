package net.AspectNetwork.bditt.API.events.enchant.enchant;

import org.bukkit.entity.Entity;

import net.AspectNetwork.bditt.API.enchantment.event.forhelp.GetEntity;


public class EEnchantEntityEvent extends EEnchantEvent implements GetEntity {

  private final Entity entity;
  @Override
  public Entity getEntity() {
    return entity;
  }

  public EEnchantEntityEvent(Entity entity) {
    this.entity = entity;
  }
}
