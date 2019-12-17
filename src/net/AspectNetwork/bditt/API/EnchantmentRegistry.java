package net.AspectNetwork.bditt.API;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import net.AspectNetwork.bditt.API.config.EnchantmentsConfig;
import net.AspectNetwork.bditt.API.enchantment.Enchanted;
import net.AspectNetwork.bditt.API.enchantment.Enchantment;
import net.AspectNetwork.bditt.API.enchantment.event.EnchantmentEvent;
import net.AspectNetwork.bditt.API.events.enchant.enchant.EEnchantEntityEvent;
import net.AspectNetwork.bditt.API.events.enchant.enchant.EEnchantItemEvent;
import net.AspectNetwork.bditt.API.events.enchant.unenchant.EUnenchantEntityEvent;
import net.AspectNetwork.bditt.API.events.enchant.unenchant.EUnenchantItemEvent;
import net.AspectNetwork.bditt.API.utils.ItemUtil;
import net.AspectNetwork.bditt.API.utils.RomanNumeral;
import net.AspectNetwork.bditt.API.utils.Text;

public class EnchantmentRegistry {

  public static final EnchantingData DEFAULT_ENCHANT_DATA = new EnchantingData();

  private static final Map<Plugin, Map<String, RegisteredEnchantment>> enchantmentsMap = new HashMap<>();

  public static synchronized Map<Plugin, Map<String, RegisteredEnchantment>> getEnchantments() {
    return new HashMap<>(enchantmentsMap);
  }


  private static final Set<RegisteredEnchantment> enchantments = new HashSet<>();

  public static Set<RegisteredEnchantment> getRegisteredEnchantments() {
    return new HashSet<>(enchantments);
  }

  private static volatile Enchantment[] backedActiveEnchantments = null;

  /**
   * returns a array of Enchantment[].
   *
   * @return Returns a Enchantment[].
   */
  public static synchronized Enchantment[] getEnchantmentsArray() {
    if (backedActiveEnchantments != null) return backedActiveEnchantments;
    return bake();
  }

  /**
   * This method in a bake method for synchronization
   *
   * @return Returns a list of Enchantments that are thread safe.
   */
  private static Enchantment[] bake() {
    Enchantment[] baked = backedActiveEnchantments;
    if (baked == null) {
      // Set -> array
      synchronized (EnchantmentRegistry.class) {
        if ((baked = backedActiveEnchantments) == null) {
          Map<String, Enchantment> active = new HashMap<>();

          for (RegisteredEnchantment en : enchantments) {
            EnchantmentsConfig.EnchantmentData data = CustomEnchantmentAPI.getInstance()
                .getEnchantmentsConfig().getData(en);
            if (!data.getIsActive().getValue()) continue;

            boolean newActive = !active.containsKey(en.getEnchantment().getDisplay(""));
            en.setActive(newActive);
            if (data.getIsActive().getValue() != newActive) {
              data.getIsActive().setValue(newActive);
              CustomEnchantmentAPI.getInstance().getEnchantmentsConfig().update(en, data);
            }

            if (newActive)
              active.put(en.getEnchantment().getDisplay(""), en.getEnchantment());
          }
          CustomEnchantmentAPI.getInstance().getEnchantmentsConfig().save();
          baked = active.values().toArray(new Enchantment[active.values().size()]);
          Arrays.sort(baked);
          backedActiveEnchantments = baked;
        }
      }

    }
    return baked;
  }

  public synchronized static void rebuildEnchantmentsArray() {
    if (backedActiveEnchantments != null)
      backedActiveEnchantments = null;
  }

  // -------------------------------------------------- //
  //                    CONSTRUCTOR                     //
  // -------------------------------------------------- //

  private EnchantmentRegistry() {
  }

  // -------------------------------------------------- //
  //              ENCHANTMENT REGISTRATION              //
  // -------------------------------------------------- //

  public static synchronized boolean register(Plugin plugin, Enchantment enchantment) {
    if (plugin == null || enchantment == null) return false;
    return register(new RegisteredEnchantment(enchantment, plugin));
  }

  /**
   * This method registers the Enchantment.
   *
   * @param registeredEnchantment A RegisteredEnchantment.
   * @return If the Enchantment has been registered.(Like if some other plugin
   * has a Enchantment with the same display name)
   */
  public static synchronized boolean register(RegisteredEnchantment registeredEnchantment) {
    if (registeredEnchantment == null) return false;

    Plugin plugin = registeredEnchantment.getPlugin();
    Enchantment enchantment = registeredEnchantment.getEnchantment();
    String enchMapID = getEnchantmentsMapID(enchantment);

    Map<String, RegisteredEnchantment> enchs = enchantmentsMap.containsKey(plugin) ?
        enchantmentsMap.get(plugin) :
        new HashMap<>();

    if (!enchs.containsKey(enchMapID)) {
      if (enchantments.add(registeredEnchantment)) {
        enchs.put(enchMapID, registeredEnchantment);
        enchantmentsMap.put(plugin, enchs);
        backedActiveEnchantments = null;

        CustomEnchantmentAPI.getInstance().getEnchantmentsConfig().getData(registeredEnchantment);
        return true;
      }
    }
    return false;
  }

  /**
   * Returns the name without Color in UpperCase with spaces replaced with '_' (In other words the ID).
   *
   * @param enchantment The enchantment That you want to get the ID from.
   * @return The ID.
   */
  public static String getEnchantmentsMapID(Enchantment enchantment) {
    return ChatColor.stripColor(enchantment.getName().toUpperCase()).replace(" ", "_");
  }

  /**
   * Unregisters a Enchantment.
   *
   * @param plugin The plugin that registered the Enchantment.
   * @param enchantment The Enchantment.
   * @return If the Enchantment has ben unregistered.
   */
  public static synchronized boolean unregister(Plugin plugin, Enchantment enchantment) {
    if (plugin == null || enchantment == null) return false;
    return unregister(new RegisteredEnchantment(enchantment, plugin));
  }

  /**
   * Unregisters a Enchantment.
   *
   * @param registeredEnchantment The Registered Enchantment.
   * @return If the Enchantment has ben unregistered.
   */
  public static synchronized boolean unregister(RegisteredEnchantment registeredEnchantment) {
    if (registeredEnchantment == null) return false;

    Plugin plugin = registeredEnchantment.getPlugin();
    Enchantment enchantment = registeredEnchantment.getEnchantment();
    String enchMapID = getEnchantmentsMapID(enchantment);

    if (enchantments.remove(registeredEnchantment)) {
      if (enchantmentsMap.containsKey(plugin)) {
        Map<String, RegisteredEnchantment> enchs = enchantmentsMap.get(plugin);

        if (enchs.containsKey(enchMapID))
          enchs.remove(enchMapID);

        if (enchs.isEmpty()) enchantmentsMap.remove(plugin);
      }
      backedActiveEnchantments = null;
      return true;
    }

    return false;
  }

  /**
   * Unregisters all Enchantments of a Plugin.
   *
   * @param plugin If it unregistered.
   */
  public static synchronized void unregisterAll(Plugin plugin) {
    if (plugin == null) return;

    if (enchantmentsMap.containsKey(plugin)) {
      for (RegisteredEnchantment registeredEnchantment : enchantmentsMap.get(plugin).values()) {
        if (enchantments.remove(registeredEnchantment))
          backedActiveEnchantments = null;
      }
      enchantmentsMap.remove(plugin);
    }
  }

  /**
   * Resets all Enchantments that have been registered.
   */
  public static synchronized void reset() {
    enchantments.clear();
    enchantmentsMap.clear();
    backedActiveEnchantments = null;
  }

  // -------------------------------------------------- //
  //                 ENCHANTMENT IDs                     //
  // -------------------------------------------------- //

  /**
   * Gets the RegisteredEnchantment from the ID.
   *
   * @param id The id(case sensitive) given from getID().
   * @return Returns the Enchantment or null.
   */
  public static synchronized RegisteredEnchantment getFromID(String id) {
    if (id == null) return null;
    if (id.trim().length() == 0) return null;

    String[] data = id.split(":");
    if (data.length < 2) return null;
    String plugin = data[0];
    String ench = data[1];
    Plugin plu = Bukkit.getPluginManager().getPlugin(plugin);

    if (!enchantmentsMap.containsKey(plu)) return null;

    Map<String, RegisteredEnchantment> data2 = enchantmentsMap.get(plu);
    if (data2 == null) return null;
    if (data2.isEmpty()) return null;
    if (data2.containsKey(ench.toUpperCase())) {
      return data2.get(ench.toUpperCase());
    }
    return null;
  }

  /**
   * Returns a ID. That can be used from getFromID().
   *
   * @param plugin The Plugin that registered the Enchantment.
   * @param enchantment The Enchantment.
   * @return The ID
   */
  public static String getID(Plugin plugin, Enchantment enchantment) {
    if (plugin == null) return null;
    if (enchantment == null) return null;
    return plugin.getName() + ":" + getEnchantmentsMapID(enchantment);
  }

  // -------------------------------------------------- //
  //                ITEM ENCHANTMENT                    //
  // -------------------------------------------------- //

  /**
   * Gets a array of Enchanted Enchantments on the item.
   *
   * @param item The item you want to list a array of Enchanted.
   * @return A array of Enchanted Enchantments.
   */
  public static synchronized Enchanted[] getEnchantments(ItemStack item) {
    List<Enchanted> res = new ArrayList<>();
    if (ItemUtil.isEmpty(item)) return res.toArray(new Enchanted[res.size()]);
    if (!item.hasItemMeta()) return res.toArray(new Enchanted[res.size()]);
    ItemMeta data = item.getItemMeta();
    if (!data.hasLore()) return res.toArray(new Enchanted[res.size()]);

    for (String line : data.getLore()) {
      if (line == null) continue;
      if (line.equalsIgnoreCase("")) continue;

      for (Enchantment ench : bake()) {
        if (ench.hasCustomEnchantment(line)) {
          int lvl = RomanNumeral.getIntFromRoman(line.substring(line.lastIndexOf(" ") + 1));
          res.add(new Enchanted(ench, lvl));
        }
      }
    }

    return res.toArray(new Enchanted[res.size()]);
  }

  /**
   * Unenchants a Enchantment from a Item
   *
   * @param item The item that you want to unenchant a Enchantment from.
   * @param enchantment The Enchantment that you want to unenchant.
   * @return If it unenchanted(Like if the Enchantment didn't exist on the
   * item).
   */
  public static boolean unenchant(ItemStack item, Enchantment enchantment) {
    if (ItemUtil.isEmpty(item)) return false;
    if (enchantment == null) return false;

    if (!item.hasItemMeta()) return false;
    ItemMeta data = item.getItemMeta();
    if (!data.hasLore()) return false;
    List<String> lore = data.getLore();
    if (lore.isEmpty()) return false;

    Iterator<String> iterator = lore.iterator();
    boolean flag = false;
    while (iterator.hasNext()) {
      if (enchantment.hasCustomEnchantment(iterator.next())) {
        iterator.remove();
        flag = true;
      }
    }
    if (!flag) return false;

    EUnenchantItemEvent e = new EUnenchantItemEvent(item);
    enchantment.fireEvent(e);
    if (e.isCancelled()) return false;

    data.setLore(lore);
    item.setItemMeta(data);
    return true;
  }

  /**
   * Enchants a ItemStack with a Enchantment.
   *
   * @param item The item you want to enchant.
   * @param enchantment The Enchantment you want to enchant on a item.
   * @param level The level of the enchantment.
   * @return If the enchant method was successful.
   */
  public static boolean enchant(ItemStack item, Enchantment enchantment, int level) {
    return enchant(item, enchantment, level, DEFAULT_ENCHANT_DATA);
  }

  /**
   * Enchants a ItemStack with a Enchantment.
   *
   * @param item The item you want to enchant.
   * @param enchantment The Enchantment you want to enchant on a item.
   * @param level The level of the enchantment.
   * @param data The data for enchanting.
   * @return If the enchant method was successful.
   */
  public static boolean enchant(ItemStack item, Enchantment enchantment, int level,
      EnchantingData data) {
    if (ItemUtil.isEmpty(item) || enchantment == null) return false;  // Checking if null
    if (level < 1) return false; // Invalid

    // Quick applying the data
    if (!data.isUnsafeItemType() && !enchantment.getType().matchType(item)) return false;
    if (!data.isUnsafeLevel() && level > enchantment.getMaxLvl()) level = enchantment.getMaxLvl();

    final ItemMeta itemMeta = item.getItemMeta();
    List<String> lore =
        itemMeta.hasLore() ? new ArrayList<>(itemMeta.getLore()) : new ArrayList<>();
    if (lore.contains(enchantment.getDisplay(level))) return false;

    boolean success = true; // If true then I'm happy.

    // Checking if the Enchantment is already applied.
    Iterator<String> loreIterator = lore.iterator();
    while (loreIterator.hasNext()) {
      String line = loreIterator.next();
      if (Text.isEmpty(line)) continue;
      if (!enchantment.hasCustomEnchantment(line)) continue;

      int lvl = RomanNumeral.getIntFromRoman(line.substring(line.lastIndexOf(" ") + 1));
      if (data.isOverrideCurrent() || (data.isOverrideIfLargerThatCurrent() && level > lvl) || (
          data.isOverrideIfSmallerThanCurrent() && level < lvl)) {
        loreIterator.remove();
        continue;
      } else success = false;
    }
    if (!success) return false;

    EEnchantItemEvent e = new EEnchantItemEvent(item);
    enchantment.fireEvent(e, level);
    if (e.isCancelled()) return false;

    lore.add(0, enchantment.getDisplay(level));

    // Applying the changes to the item
    itemMeta.setLore(lore);
    item.setItemMeta(itemMeta);

    return true; // Returning if successful.
  }

  /**
   * Enchants a ItemStack with a Enchantment.
   *
   * @param item The item you want to enchant.
   * @param enchantment The Enchantment you want to enchant on a item.
   * @param level The level of the enchantment.
   * @param override If it overrides the current enchantment.
   * @param override_if_larger_level If it overrides if there's a larger level.
   * @return If the enchant method was successful.
   */
  public static boolean enchant(ItemStack item, Enchantment enchantment, int level,
      boolean override, boolean override_if_larger_level) {
    return enchant(item, enchantment, level, new EnchantingData().setOverrideCurrent(override)
        .setOverrideIfLargerThatCurrent(override_if_larger_level));
  }

  // -------------------------------------------------- //
  //                ENTITY ENCHANTMENT                  //
  // -------------------------------------------------- //

  /**
   * The Tag-Prefix used by {@code getTagID}.
   */
  public static final String TAG_PREFIX = ("adx_" + UUID.randomUUID().toString() + "_").trim()
      .replace(' ', '_');

  /**
   * @param enchantment The Enchantment.
   * @return Returns a String withe the {@code TAG_PREFIX} at the beginning and the {@code Enchantment.getDisplay}(with ' ' replaced to '_').
   */
  public static String getTagID(Enchantment enchantment) {
    if (enchantment == null) return null;
    return TAG_PREFIX + enchantment.getDisplay("").trim().replace(' ', '_');

  }

  /**
   * Gets the Enchanted Enchantments on the Entity.
   *
   * @param entity The Entity that you want to scan.
   * @return A Enchanted[] array.
   */
  public synchronized static Enchanted[] getEnchantments(Entity entity) {
    List<Enchanted> enchanted = new ArrayList<>();
    {
      for (Enchantment enchantment : bake()) {
        String tagID = getTagID(enchantment);
        if (entity.hasMetadata(tagID)) {
          int lvl = entity.getMetadata(tagID).get(0).asInt();
          enchanted.add(new Enchanted(enchantment, lvl));
        }
      }
    }
    return enchanted.toArray(new Enchanted[enchanted.size()]);
  }

  /**
   * Removes the Enchantment enchanted on the Entity.
   *
   * @param entity The Entity you want to unenchant.
   * @param enchantment The Enchantment that you want to unenchant.
   * @return If the Enchantment has been unenchanted.
   */
  public synchronized static boolean unenchant(Entity entity, Enchantment enchantment) {
    if (entity == null || enchantment == null) return false;

    String tagID = getTagID(enchantment);
    List<MetadataValue> mValues = entity.getMetadata(tagID);
    if (mValues == null || mValues.isEmpty()) return false;

    Iterator<MetadataValue> iterator = mValues.iterator();
    while (iterator.hasNext()) {
      MetadataValue mV = iterator.next();
      if (mV.getOwningPlugin().equals(CustomEnchantmentAPI.getInstance())) {
        EUnenchantEntityEvent e = new EUnenchantEntityEvent(entity);
        enchantment.fireEvent(e);
        if (e.isCancelled()) return false;

        iterator.remove();
        entity.removeMetadata(tagID, CustomEnchantmentAPI.getInstance());
        return true;
      }
    }

    return false;
  }

  /**
   * Enchants a Entity with a Enchantment.
   *
   * @param entity The Entity that you want to enchant.
   * @param enchantment Enchantment you want to enchant on a item.
   * @param level The level of the enchantment.
   * @return If the enchant method was successful.
   */
  public synchronized static boolean enchant(Entity entity, Enchantment enchantment, int level) {
    return enchant(entity, enchantment, level, DEFAULT_ENCHANT_DATA);
  }

  /**
   * Enchants a Entity with a Enchantment.
   *
   * @param entity The Entity that you want to enchant.
   * @param enchantment Enchantment you want to enchant on a item.
   * @param level The level of the enchantment.
   * @param data The data for enchanting.
   * @return If the enchant method was successful.
   */
  public synchronized static boolean enchant(Entity entity, Enchantment enchantment, int level,
      EnchantingData data) {
    if (entity == null || enchantment == null) return false;
    if (level < 1) return false;

    if (!data.isUnsafeLevel() && level > enchantment.getMaxLvl()) level = enchantment.getMaxLvl();

    String tagID = getTagID(enchantment);
    List<MetadataValue> mValues = entity.getMetadata(tagID);

    boolean flag = true;
    if (mValues.isEmpty() || data.isOverrideCurrent()) ;
    else if (data.isOverrideIfLargerThatCurrent() || data.isOverrideIfSmallerThanCurrent()) {
      for (MetadataValue mV : mValues) {
        int lvl = mV.asInt();
        if (data.isOverrideCurrent() || (data.isOverrideIfLargerThatCurrent() && level > lvl) || (
            data.isOverrideIfSmallerThanCurrent() && level < lvl))
          ;
        else flag = false;
      }
    }
    if (!flag) return false;

    EEnchantEntityEvent e = new EEnchantEntityEvent(entity);
    enchantment.fireEvent(e, level);
    if (e.isCancelled()) return false;

    entity.setMetadata(tagID, new FixedMetadataValue(CustomEnchantmentAPI.getInstance(), level));
    return true;
  }

  /**
   * Enchants a Entity with a Enchantment.
   *
   * @param entity The Entity that you want to enchant.
   * @param enchantment Enchantment you want to enchant on a item.
   * @param level The level of the enchantment.
   * @param override If it overrides the current enchantment.
   * @param override_if_larger_level If it overrides if there's a larger level.
   * @return If the enchant method was successful.
   */
  public synchronized static boolean enchant(Entity entity, Enchantment enchantment, int level,
      boolean override, boolean override_if_larger_level) {
    return enchant(entity, enchantment, level,
        new EnchantingData(override, override_if_larger_level));
  }

  // -------------------------------------------------- //
  //                  EVENT FIRING                      //
  // -------------------------------------------------- //

  /**
   * Fires the Event/s for every Enchanted Enchantment.
   *
   * @param enchantedEnchantments The array of Enchanted Enchantments.
   * @param events The instance of the EnchantmentEvent/s.
   */
  public static void fireEvents(Enchanted[] enchantedEnchantments, EnchantmentEvent... events) {
    if (enchantedEnchantments == null || events == null || events.length < 1) return;

    int count = 0;
    for (int i = 0; i < events.length; i++) {
      if (events[i] == null) count++;
    }
    if (count >= events.length) return;

    for (Enchanted ench : enchantedEnchantments) {
      for (EnchantmentEvent event : events) {
        if (event == null) continue;
        ench.fireEvent(event);
      }
    }
  }

  /**
   * Fires the Event/s for every Enchanted Enchantment.
   *
   * @param enchantments The array of Enchantments.
   * @param events The instance of the EnchantmentEvent/s.
   */
  public static void fireEvents(Enchantment[] enchantments, EnchantmentEvent... events) {
    if (enchantments == null || events == null || events.length < 1) return;

    int count = 0;
    for (int i = 0; i < events.length; i++) {
      if (events[i] == null) count++;
    }
    if (count >= events.length) return;

    for (Enchantment ench : enchantments) {
      for (EnchantmentEvent event : events) {
        if (event == null) continue;
        ench.fireEvent(event);
      }
    }
  }

  // -------------------------------------------------- //
  //         EXTRA CLASSES/ENUMS/INTERFACES             //
  // -------------------------------------------------- //

  public static final class EnchantingData {

    private boolean overrideCurrent = false;

    public boolean isOverrideCurrent() {
      return overrideCurrent;
    }

    public EnchantingData setOverrideCurrent(boolean overrideCurrent) {
      this.overrideCurrent = overrideCurrent;
      return this;
    }

    private boolean overrideIfLargerThatCurrent = true;

    public boolean isOverrideIfLargerThatCurrent() {
      return overrideIfLargerThatCurrent;
    }

    public EnchantingData setOverrideIfLargerThatCurrent(boolean overrideIfLargerThatCurrent) {
      this.overrideIfLargerThatCurrent = overrideIfLargerThatCurrent;
      return this;
    }

    private boolean overrideIfSmallerThanCurrent = true;

    public boolean isOverrideIfSmallerThanCurrent() {
      return overrideIfSmallerThanCurrent;
    }

    public EnchantingData setOverrideIfLowerThatCurrent(boolean overrideIfLowerThanCurrent) {
      this.overrideIfSmallerThanCurrent = overrideIfLowerThanCurrent;
      return this;
    }

    private boolean unsafe = false;

    public boolean isUnsafe() {
      return unsafe;
    }

    public EnchantingData setUnsafe(boolean unsafe) {
      this.unsafe = unsafe;
      this.setUnsafeItemType(unsafe);
      this.setUnsafeLevel(true);
      return this;
    }

    private boolean unsafeLevel = false;

    public boolean isUnsafeLevel() {
      return unsafeLevel;
    }

    public EnchantingData setUnsafeLevel(boolean unsafeLevel) {
      this.unsafeLevel = unsafeLevel;
      this.unsafe = true;

      return this;
    }

    private boolean unsafeItemType = false;

    public boolean isUnsafeItemType() {
      return unsafeItemType;
    }

    public EnchantingData setUnsafeItemType(boolean unsafeItemType) {
      this.unsafeItemType = unsafeItemType;
      this.unsafe = true;

      return this;
    }

    public EnchantingData() {
    }

    /**
     * Constructor for EnchantingData.
     *
     * @param overrideCurrent If it overrides the current Enchantment.
     * @param overrideIfLargerThatCurrent Overrides the current Enchantment if the Level is larger.
     * @param overrideIfSmallerThanCurrent Overrides the current Enchantment if the Level is lower.
     * @param unsafeLevel If the Level is unsafe(If it larger that the Max-Level)
     * @param unsafeItemType If the ItemType is not what it supposed to be.
     */
    public EnchantingData(boolean overrideCurrent, boolean overrideIfLargerThatCurrent,
        boolean overrideIfSmallerThanCurrent, boolean unsafeLevel, boolean unsafeItemType) {
      this.setOverrideCurrent(overrideCurrent);
      this.setOverrideIfLargerThatCurrent(overrideIfLargerThatCurrent);
      this.setOverrideIfLowerThatCurrent(overrideIfSmallerThanCurrent);
      this.setUnsafeLevel(unsafeLevel);
      this.setUnsafeItemType(unsafeItemType);
    }

    /**
     * Constructor for EnchantingData.
     *
     * @param overrideCurrent If it overrides the current Enchantment.
     * @param overrideIfLargerThatCurrent Overrides the current Enchantment if the Level is larger.
     * @param overrideISmallerThanCurrent Overrides the current Enchantment if the Level is lower.
     * @param unsafe If the Level is unsafe(If it larger that the Max-Level) and if the ItemType is not what it supposed to be.
     */
    public EnchantingData(boolean overrideCurrent, boolean overrideIfLargerThatCurrent,
        boolean overrideISmallerThanCurrent, boolean unsafe) {
      this.setOverrideCurrent(overrideCurrent);
      this.setOverrideIfLargerThatCurrent(overrideIfLargerThatCurrent);
      this.setOverrideIfLowerThatCurrent(overrideISmallerThanCurrent);
      this.setUnsafe(unsafe);
    }

    /**
     * Constructor for EnchantingData.
     *
     * @param overrideCurrent If it overrides the current Enchantment.
     * @param overrideIfLargerThatCurrent Overrides the current Enchantment if the Level is larger.
     * @param overrideIfSmallerThanCurrent Overrides the current Enchantment if the Level is lower.
     */
    public EnchantingData(boolean overrideCurrent, boolean overrideIfLargerThatCurrent,
        boolean overrideIfSmallerThanCurrent) {
      this.setOverrideCurrent(overrideCurrent);
      this.setOverrideIfLargerThatCurrent(overrideIfLargerThatCurrent);
      this.setOverrideIfLowerThatCurrent(overrideIfSmallerThanCurrent);
    }

    /**
     * Constructor for EnchantingData.
     *
     * @param overrideCurrent If it overrides the current Enchantment.
     * @param overrideIfLargerThatCurrent Overrides the current Enchantment if the Level is larger.
     */
    public EnchantingData(boolean overrideCurrent, boolean overrideIfLargerThatCurrent) {
      this.setOverrideCurrent(overrideCurrent);
      this.setOverrideIfLargerThatCurrent(overrideIfLargerThatCurrent);
    }
  }
}
