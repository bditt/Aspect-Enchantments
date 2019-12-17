package net.AspectNetwork.bditt.API.utils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import net.AspectNetwork.bditt.API.CustomEnchantmentAPI;
import net.AspectNetwork.bditt.API.enums.ItemType;
import net.AspectNetwork.bditt.API.events.inventory.hand.enums.HandType;

public class ItemUtil {

  public static final ItemStack NULL = new ItemStack(Material.AIR, 0);

  // Methods
  final public static List<Integer> getSlotsList(Player player, ItemStack item, SlotType slotType) {
    if (canEquip(item, player) && (slotType.equals(SlotType.CONTAINER) || slotType
        .equals(SlotType.QUICKBAR)))
      return new ArrayList<Integer>();
    int start = 0;
    if (slotType.equals(SlotType.ARMOR) || slotType.equals(SlotType.QUICKBAR) || slotType
        .equals(SlotType.CRAFTING)
        || slotType.equals(SlotType.RESULT)) {
      start = 9;
    }
    return getSlotsList(start, player.getInventory(), item);
  }

  public static boolean canEquip(ItemStack item, LivingEntity entity) {
    if (isEmpty(item)) return false;
    if (entity == null) return false;
    return (isEmpty(entity.getEquipment().getHelmet()) && ItemType.HELMET.matchType(item)) || (
        isEmpty(entity.getEquipment().getChestplate()) && ItemType.CHESTPLATE.matchType(item)) || (
        isEmpty(entity.getEquipment().getBoots()) && ItemType.BOOT.matchType(item)) || (
        isEmpty(entity.getEquipment().getLeggings()) && ItemType.LEGGNIG.matchType(item));
  }

  final public static List<Integer> getSlotsList(int index, final Inventory inventory,
      final ItemStack item) {
    List<Integer> result = new ArrayList<Integer>();
    if (inventory == null || isEmpty(item)) return result;
    if (index < 0) index = 0;
    int amount = item.getAmount();
    if (amount < 1) return result;
    final int iSize = inventory.getSize();
    {
      if (getMaxStackSize(item) > 1) {
        for (int SlotI = 0; SlotI < iSize; SlotI++) {
          ItemStack itemI = inventory.getItem(SlotI);
          if (isEmpty(itemI)) continue;
          if (item == itemI) continue;
          if (isSame(itemI, item) && itemI.getAmount() < getMaxStackSize(itemI)) {
            int dif = getDif(amount, itemI);
            amount -= dif;
            result.add(SlotI);
            if (amount <= 0) return result;
          }
        }
      }
      for (int i = 0; i < iSize; i++) {
        int SlotI = (i + index) % iSize;
        ItemStack itemI = inventory.getItem(SlotI);
        if (!isEmpty(itemI)) continue;
        result.add(SlotI);
        return result;
      }
    }
    return result;
  }

  public static boolean isEmpty(ItemStack itemStack) {
    if (itemStack == null) return true;
    if (itemStack.getAmount() < 0) return true;
    return itemStack.getType() == Material.AIR;
  }

  public static int getMaxStackSize(ItemStack item) {
    if (isEmpty(item)) return 0;
    return item.getMaxStackSize();
  }

  public static boolean isSame(ItemStack item1, ItemStack item2) {
    if (item1 == item2) return true;
    if (item1 == null || item2 == null) return false;
    if (item1.equals(item2)) return true;
    if (!item1.getType().equals(item2.getType())) return false;
    if (item1.hasItemMeta() != item2.hasItemMeta()) return false;

    if (item1.hasItemMeta() && item2.hasItemMeta()) {
      if (item1.getItemMeta().equals(item2.getItemMeta()))
        return true;
    }
    return false;
  }

  private static int getDif(int amount, ItemStack itemI) {
    int empty = itemI.getMaxStackSize() - itemI.getAmount();
    int dif = amount <= empty ? amount : empty;
    return dif;
  }

  public static Map<Integer, ItemStack> getSlots(Player player, ItemStack item, SlotType slotType) {
    if (canEquip(item, player) && (slotType.equals(SlotType.CONTAINER) || slotType
        .equals(SlotType.QUICKBAR)))
      return new HashMap<Integer, ItemStack>();
    int start = 0;
    if (slotType.equals(SlotType.ARMOR) || slotType.equals(SlotType.QUICKBAR) || slotType
        .equals(SlotType.CRAFTING)
        || slotType.equals(SlotType.RESULT)) {
      start = 9;
    }
    return getSlotsItem(start, player.getInventory(), item);
  }

  final public static Map<Integer, ItemStack> getSlotsItem(int index, final Inventory inventory,
      final ItemStack item) {
    Map<Integer, ItemStack> result = new HashMap<Integer, ItemStack>();
    if (inventory == null || isEmpty(item)) return result;
    if (index < 0) index = 0;
    int amount = item.getAmount();
    if (amount < 1) return result;
    final int iSize = inventory.getSize();

    if (getMaxStackSize(item) > 1) {
      for (int SlotI = 0; SlotI < iSize; SlotI++) {
        ItemStack itemI = inventory.getItem(SlotI);
        if (isEmpty(itemI)) continue;
        if (item == itemI) continue;
        if (isSame(itemI, item) && itemI.getAmount() < getMaxStackSize(itemI)) {
          int dif = getDif(amount, itemI);
          amount -= dif;
          ItemStack j = new ItemStack(item);
          j.setAmount(dif);
          result.put(SlotI, j);
          if (amount <= 0) return result;
        }

      }
    }
    for (int i = 0; i < iSize; i++) {
      int SlotI = (i + index) % iSize;
      ItemStack itemI = inventory.getItem(SlotI);
      if (isEmpty(itemI)) {
        ItemStack j = new ItemStack(item);
        j.setAmount(amount);
        result.put(SlotI, j);
        return result;
      }
    }
    return result;
  }

  public static ItemStack addDisplay(ItemStack item, String name, List<String> lore) {
    if (isEmpty(item)) return NULL;
    if (name == null) name = "";
    if (lore == null) lore = new ArrayList<String>();

    ItemMeta meta = item.getItemMeta();

    meta.setDisplayName(name);
    meta.setLore(lore);

    item.setItemMeta(meta);

    return item;
  }

  public static ItemStack setName(ItemStack item, String name) {
    if (isEmpty(item)) return NULL;
    if (name == null) name = "";

    ItemMeta meta = item.getItemMeta();

    meta.setDisplayName(name);

    item.setItemMeta(meta);

    return item;
  }

  public static ItemStack setDurability(ItemStack item, short damage, boolean breakItem) {
    if (isEmpty(item)) return NULL;
    item.setDurability((short) (item.getType().getMaxDurability() - damage));
    if (item.getDurability() > item.getType().getMaxDurability() && breakItem) item = NULL;

    return item;
  }

  public static ItemStack setLore(ItemStack item, List<String> lore) {
    if (isEmpty(item)) return NULL;
    if (lore == null) lore = new ArrayList<String>();

    ItemMeta meta = item.getItemMeta();

    meta.setLore(lore);

    item.setItemMeta(meta);

    return item;
  }

  public static ItemStack setColorOfLeatherArmor(ItemStack item, Color color) {
    if (isEmpty(item)) return NULL;

    if (item.getItemMeta() instanceof LeatherArmorMeta) {
      LeatherArmorMeta lam = (LeatherArmorMeta) item.getItemMeta();
      lam.setColor(color);
      item.setItemMeta(lam);
      return item;
    }
    return item;
  }

  public static HandType getEquippingHandType(Player player) {
    if (!isEmpty(getMainHandItem(player))) {
      return HandType.MAIN;
    } else {
      if (!isEmpty(getOffHandItem(player))) {
        return HandType.OFF;
      } else {
        return HandType.UNKNOWN;
      }
    }
  }

  public static ItemStack getMainHandItem(LivingEntity player) {
    return CustomEnchantmentAPI.getInstance().getNSM().getItemInMainHand(player);
  }

  public static ItemStack getOffHandItem(LivingEntity player) {
    return CustomEnchantmentAPI.getInstance().getNSM().getItemInOffHand(player);
  }

}
