package net.AspectNetwork.bditt.API.events.inventory;


import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.ItemStack;

import net.AspectNetwork.bditt.API.enchantment.event.EnchantmentEventWithLevel;
import net.AspectNetwork.bditt.API.enchantment.event.extra.EnchantmentEventWithOwnerAndItem;
import net.AspectNetwork.bditt.API.enums.ArmorType;

@EnchantmentEventWithLevel
public class EUnequipEvent extends EnchantmentEventWithOwnerAndItem implements Cancellable {

  private final ArmorType armorType;
  public ArmorType getArmorType() {
    return armorType;
  }

  private boolean cancelled = false;
  public boolean isCancelled() {
    return cancelled;
  }
  public void setCancelled(boolean cancelled) {
    this.cancelled = cancelled;
  }

  public EUnequipEvent(ItemStack item, LivingEntity owner) {
    super(owner, item);
    armorType = ArmorType.matchType(item);
  }
}
