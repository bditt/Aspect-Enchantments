package net.AspectNetwork.bditt.API.enums;


import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import net.AspectNetwork.bditt.API.utils.ItemUtil;

public class ItemType {

  public final static ItemType SPADE = new ItemType(new Material[] {
      Material.LEGACY_WOOD_SPADE,
      Material.LEGACY_STONE_SPADE,
      Material.LEGACY_IRON_SPADE,
      Material.LEGACY_GOLD_SPADE,
      Material.LEGACY_DIAMOND_SPADE
  });
  public final static ItemType PICKAXE = new ItemType(new Material[] {
      Material.LEGACY_WOOD_PICKAXE,
      Material.STONE_PICKAXE,
      Material.IRON_PICKAXE,
      Material.LEGACY_GOLD_PICKAXE,
      Material.DIAMOND_PICKAXE
  });
  public final static ItemType HOE = new ItemType(new Material[] {
      Material.LEGACY_WOOD_HOE,
      Material.STONE_HOE,
      Material.IRON_HOE,
      Material.LEGACY_GOLD_HOE,
      Material.DIAMOND_HOE
  });
  public final static ItemType AXE = new ItemType(new Material[] {
      Material.LEGACY_WOOD_AXE,
      Material.STONE_AXE,
      Material.IRON_AXE,
      Material.LEGACY_GOLD_AXE,
      Material.DIAMOND_AXE
  });
  public final static ItemType SWORD = new ItemType(new Material[] {
      Material.LEGACY_WOOD_SWORD,
      Material.STONE_SWORD,
      Material.IRON_SWORD,
      Material.LEGACY_GOLD_SWORD,
      Material.DIAMOND_SWORD
  });
  public final static ItemType BOW = new ItemType(new Material[] {
      Material.BOW
  });

  public final static ItemType SHIELD = new ItemType(new Material[] {
      Material.matchMaterial("SHIELD")
  });
  public final static ItemType ELYTRA = new ItemType(new Material[] {
      Material.matchMaterial("ELYTRA")
  });

  public final static ItemType HELMET = new ItemType(new Material[] {
      Material.LEATHER_HELMET,
      Material.IRON_HELMET,
      Material.LEGACY_GOLD_HELMET,
      Material.DIAMOND_HELMET
  });
  public final static ItemType CHESTPLATE = new ItemType(new Material[] {
      Material.LEATHER_CHESTPLATE,
      Material.IRON_CHESTPLATE,
      Material.LEGACY_GOLD_CHESTPLATE,
      Material.DIAMOND_CHESTPLATE
  });
  public final static ItemType LEGGNIG = new ItemType(new Material[] {
      Material.LEATHER_LEGGINGS,
      Material.IRON_LEGGINGS,
      Material.LEGACY_GOLD_LEGGINGS,
      Material.DIAMOND_LEGGINGS
  });
  public final static ItemType BOOT = new ItemType(new Material[] {
      Material.LEATHER_BOOTS,
      Material.IRON_BOOTS,
      Material.LEGACY_GOLD_BOOTS,
      Material.DIAMOND_BOOTS
  });

  public final static ItemType SADDLE = new ItemType(new Material[] {
      Material.SADDLE
  });
  public final static ItemType BARDING = new ItemType(new Material[] {
      Material.LEGACY_DIAMOND_BARDING,
      Material.LEGACY_GOLD_BARDING,
      Material.LEGACY_IRON_BARDING
  });

  public final static ItemType TOOLS = new ItemType(new ItemType[] {
      SPADE,
      PICKAXE,
      AXE,
      HOE
  });
  public final static ItemType WEAPONS = new ItemType(new ItemType[] {
      SWORD,
      AXE,
      BOW
  });
  public final static ItemType ARMOR = new ItemType(new ItemType[] {
      HELMET,
      CHESTPLATE,
      LEGGNIG,
      BOOT
  });
  public final static ItemType HORSE = new ItemType(new ItemType[] {
      SADDLE,
      BARDING
  });

  public final static ItemType ALL_OFF_THE_ABOVE = new ItemType(new ItemType[] {
      TOOLS,
      WEAPONS,
      SHIELD,
      ARMOR,
      ELYTRA,
      HORSE
  });

  private final Material[] types;
  public Material[] getTypes() {
    return types;
  }

  private final List<Material> typesList;
  public List<Material> getTypesList() {
    return typesList;
  }

  // Constructor
  public ItemType(ItemType[] subIT) {
    this(subIT, null);
  }
  public ItemType(Material[] subM) {
    this(null, subM);
  }
  public ItemType(List<Material> subMList) {
    this(null, null, subMList);
  }
  public ItemType(ItemType[] subIT, Material[] subM) {
    this(subIT, subM, null);
  }
  public ItemType(ItemType[] subIT, Material[] subM, List<Material> subMList) {
    List<Material> materials = new ArrayList<Material>();
    if (subIT != null) {
      for (ItemType type : subIT) {
        materials.addAll(type.typesList);
      }
    }
    if (subM != null) materials.addAll(asList(subM));
    if (subMList != null) materials.addAll(subMList);

    materials = removeNullIndex(materials);
    Collections.sort(materials);

    this.typesList = materials;
    this.types = materials.toArray(new Material[materials.size()]);
  }

  private List<Material> removeNullIndex(List<Material> in) {
    List<Material> out = new ArrayList<Material>();
    for (Material mat : in) {
      if (mat == null) continue;
      out.add(mat);
    }
    return out;
  }

  public boolean matchType(ItemStack item) {
    if (ItemUtil.isEmpty(item)) return false;
    return typesList.contains(item.getType());
  }
  public boolean matchType(Material material) {
    if (material == null) return false;
    return typesList.contains(material);
  }
}
