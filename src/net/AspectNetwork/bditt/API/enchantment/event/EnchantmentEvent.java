package net.AspectNetwork.bditt.API.enchantment.event;


public interface EnchantmentEvent {

  /**
   * Gives a boolean if event has the option for Levels.
   *
   * @param event The instance of EnchantmentEvent.
   * @return True if it supports Levels.
   */
  static boolean hasLevels(EnchantmentEvent event) {
    return event.getClass().getAnnotation(EnchantmentEventWithLevel.class) != null;
  }
}
