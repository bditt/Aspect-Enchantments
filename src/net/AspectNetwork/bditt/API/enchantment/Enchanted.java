package net.AspectNetwork.bditt.API.enchantment;


import net.AspectNetwork.bditt.API.enchantment.event.EnchantmentEvent;
import net.AspectNetwork.bditt.API.enchantment.event.forhelp.Lvl;

public class Enchanted implements Lvl {

  private final int lvl;
  public int getLvl() {
    return lvl;
  }

  private final Enchantment enchantment;
  public Enchantment getEnchantment() {
    return enchantment;
  }

  // Constructor
  public Enchanted(Enchantment enchantment) {
    this(enchantment, 1);
  }
  public Enchanted(Enchantment enchantment, int lvl) {
    this.lvl = lvl;
    this.enchantment = enchantment;
  }

  /**
   * Fires the event.
   *
   * @param event The instance of the EnchantmentEvent you want to fire.
   * @param sync If it's going to be ran synchronized.
   */
  public void fireEvent(EnchantmentEvent event, boolean sync) {
    enchantment.fireEvent(event, lvl, sync);
  }
  /**
   * Fires the event.
   *
   * @param event The instance of the EnchantmentEvent you want to fire.
   */
  public void fireEvent(EnchantmentEvent event) {
    enchantment.fireEvent(event, lvl);
  }
}
