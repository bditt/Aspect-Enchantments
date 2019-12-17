package net.AspectNetwork.bditt.API.enums;


import org.bukkit.inventory.ItemStack;

public enum ArmorType {
  HELMET(5),
  CHESTPLATE(6),
  ELYTRA(6),
  LEGGINGS(7),
  BOOTS(8);

  private final int slot;
  public int getSlot() {
    return slot;
  }

  // Constructor
  ArmorType(int slot) {
    this.slot = slot;
  }

  /**
   * Attempts to match the ArmorType for the specified ItemStack.
   *
   * @param itemStack The ItemStack to parse the type of.
   * @return The parsed ArmorType. (null if none were found.)
   */
  public static ArmorType matchType(final ItemStack itemStack) {
    if (itemStack == null) {
      return null;
    }
    if (ItemType.HELMET.matchType(itemStack)) {
      return ArmorType.HELMET;
    } else if (ItemType.CHESTPLATE.matchType(itemStack)) {
      return ArmorType.CHESTPLATE;
    } else if (ItemType.ELYTRA.matchType(itemStack)) {
      return ArmorType.ELYTRA;
    } else if (ItemType.LEGGNIG.matchType(itemStack)) {
      return ArmorType.LEGGINGS;
    } else if (ItemType.BOOT.matchType(itemStack)) {
      return ArmorType.BOOTS;
    } else {
      return null;
    }
  }
}