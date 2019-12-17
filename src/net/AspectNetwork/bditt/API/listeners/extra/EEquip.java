package net.AspectNetwork.bditt.API.listeners.extra;


import java.util.Hashtable;
import java.util.Map;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import net.AspectNetwork.bditt.API.listeners.CEAPIListenerUtils;
import net.AspectNetwork.bditt.API.utils.ItemUtil;

public class EEquip extends BukkitRunnable {

  static Map<String, InventoryContents> equipment = new Hashtable<>();

  public static void loadPlayer(Player player) {
    equipment.put(player.getName(), new InventoryContents(player));
  }

  public static void clearPlayer(Player player) {
    equipment.remove(player.getName());
  }

  public static void clear() {
    equipment.clear();
  }


  Player player;

  public EEquip(Player player) {
    this.player = player;
  }

  public void run() {
    InventoryContents nowI = new InventoryContents(player);
    ItemStack[] nowArmorContents = nowI.armorContents;
    ItemStack nowMainHand = nowI.mainHand;
    ItemStack nowOffHand = nowI.offHand;
    try {
      InventoryContents prewI = equipment.get(player.getName());
      ItemStack[] prewArmorContents = prewI.armorContents;
      ItemStack prewMainHand = prewI.mainHand;
      ItemStack prewOffHand = prewI.offHand;

      for (int i = 0; i < nowArmorContents.length; i++) {
        if (!(nowArmorContents[i] + "").equalsIgnoreCase(prewArmorContents[i] + "")) {
          CEAPIListenerUtils.unenquipt(player, prewArmorContents[i]);
          CEAPIListenerUtils.enquipt(player, nowArmorContents[i]);
        }
      }
      if (!(nowMainHand + "").equalsIgnoreCase(prewMainHand + "")) {
        CEAPIListenerUtils.itemNotInMainHand(player, prewMainHand);
        CEAPIListenerUtils.itemInMainHand(player, nowMainHand);
      }
      if (!(nowOffHand + "").equalsIgnoreCase(prewOffHand + "")) {
        CEAPIListenerUtils.itemNotInOffHand(player, prewOffHand);
        CEAPIListenerUtils.itemInOffHand(player, nowOffHand);
      }
    } catch (Exception e) {
    }
    equipment.put(player.getName(), nowI);
  }

  private static class InventoryContents {

    ItemStack[] armorContents;
    ItemStack mainHand;
    ItemStack offHand;

    public InventoryContents(LivingEntity entity) {
      this(entity.getEquipment().getArmorContents(), ItemUtil.getMainHandItem(entity),
          ItemUtil.getOffHandItem(entity));
    }

    public InventoryContents(ItemStack[] armorContents, ItemStack mainHand, ItemStack offHand) {
      this.armorContents = armorContents;
      this.mainHand = mainHand;
      this.offHand = offHand;
    }
  }
}
