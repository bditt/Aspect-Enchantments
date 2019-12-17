package net.AspectNetwork.bditt.API.enchantment;


import org.bukkit.ChatColor;

import net.AspectNetwork.bditt.API.CustomEnchantmentAPI;
import net.AspectNetwork.bditt.API.enchantment.event.EnchantmentEvent;
import net.AspectNetwork.bditt.API.enums.EnchantmentPriority;
import net.AspectNetwork.bditt.API.enums.ItemType;
import net.AspectNetwork.bditt.API.utils.RomanNumeral;

public abstract class Enchantment implements Comparable<Enchantment> {

  private final String name;
  /**
   * Gets the name of the enchantment, depending on config.yml with/out color codes.
   *
   * @return The name
   */
  public final String getName() {
    return name;
  }
  /**
   * Gets the display name that the enchantment will have when enchanted.
   *
   * @param lvl The level that the display name will have.(If 0 or less it
   * throws a Exception)
   * @return The display name
   */
  public final String getDisplay(int lvl) {
    return getDisplay(RomanNumeral.getRomanFromInt(lvl));
  }
  /**
   * Gets the display name that the enchantment will have when enchanted.
   *
   * @param romanNumeral The roman numeral.
   * @return The display name.
   */
  public String getDisplay(String romanNumeral) {
    return name + " " + romanNumeral;
  }


  private String description = null;
  /**
   * Gets the description of the Enchantment. Note creates a new instance.
   *
   * @return The description.
   */
  public final String getDescription() {
    return description;
  }
  /**
   * Sets the description.
   *
   * @param description The new Description.
   */
  protected final <T extends Enchantment> T setDescription(String description) {
    this.description = description;

    return (T) this;
  }


  protected final ItemType type;
  /**
   * Gets the items on which the Enchantment can be enchanted on.
   *
   * @return A TemType object.
   */
  public final ItemType getType() {
    return type;
  }


  protected final int maxLvl;
  /**
   * Gets the max level of the Enchantment.
   *
   * @return The max level.
   */
  public final int getMaxLvl() {
    return maxLvl;
  }


  protected final EnchantmentPriority priority;
  /**
   * Gets the priority of the Enchantment.
   *
   * @return The Enchantments priority.
   */
  public EnchantmentPriority getPriority() {
    return priority;
  }
  @Override
  public final int compareTo(Enchantment other) {
    return other.priority.compareTo(priority);
  }


  private final EventBus eb;

  // --------------------------------------- //
  //              Constructor                //
  // --------------------------------------- //

  /**
   * @param name The name of the enchantment
   * @param type The ItemType that the Enchantment can be enchanted on.
   * @param maxLvl The max level that the Enchantment can be.
   */
  public Enchantment(String name, ItemType type, int maxLvl) {
    this(name, type, maxLvl, EnchantmentPriority.NORMAL);
  }

  /**
   * @param name The name of the enchantment
   * @param type The ItemType that the Enchantment can be enchanted on.
   * @param maxLvl The max level that the Enchantment can be.
   * @param priority The priority that the Enchantment has.
   */
  public Enchantment(String name, ItemType type, int maxLvl, EnchantmentPriority priority) {
    this.name = name;

    this.type = type;
    if (maxLvl < 1) throw new IllegalArgumentException("The Max Level must be at least 1.");
    this.maxLvl = maxLvl;
    this.priority = priority;
    eb = new EventBus(this);
  }

  // --------------------------------------- //
  //               Checking                  //
  // --------------------------------------- //

  /**
   * Returns a boolean if the String is equal to the Enchantment display name.
   *
   * @param line The input String
   * @return If the String is equal to the Enchantment display name.
   */
  public final boolean hasCustomEnchantment(String line) {
    if (line == null) return false;
    if (line.trim().equals("")) return false;

    String display = this.getDisplay("");
    if (!CustomEnchantmentAPI.getInstance().getDefaultConfig().COLOR_CODE_SPECIFIC.getValue()) {
      line = ChatColor.stripColor(line);
      display = ChatColor.stripColor(display);
    }
    int displayLength = display.length();

    if (displayLength >= line.length()) return false;
    if (!line.startsWith(display)) return false;

    String[] lastWorld_s = line.replaceFirst(line.substring(0, displayLength), "").split(" ");
    if (lastWorld_s.length > 1) return false;
    return RomanNumeral.isRoman(lastWorld_s[0].trim());
  }

  // --------------------------------------- //
  //              Event Firing               //
  // --------------------------------------- //

  /**
   * Fires the event.
   *
   * @param event The instance of the EnchantmentEvent you want to fire.
   * @param sync If it's going to be ran synchronized.
   */
  public void fireEvent(EnchantmentEvent event, boolean sync) {
    fireEvent(event, 1, sync);
  }
  /**
   * Fires the event.
   *
   * @param event The instance of the EnchantmentEvent you want to fire.
   * @param lvl The level.
   * @param sync If it's going to be ran synchronized.
   */
  public void fireEvent(EnchantmentEvent event, int lvl, boolean sync) {
    eb.fireEvent(event, lvl, sync);
  }
  /**
   * Fires the event.
   *
   * @param event The instance of the EnchantmentEvent you want to fire.
   */
  public void fireEvent(EnchantmentEvent event) {
    fireEvent(event, 1);
  }
  /**
   * Fires the event.
   *
   * @param event The instance of the EnchantmentEvent you want to fire.
   * @param lvl The level.
   */
  public void fireEvent(EnchantmentEvent event, int lvl) {
    fireEvent(event, lvl, true);
  }

  // --------------------------------------- //
  //              Methods               //
  // --------------------------------------- //

  @Override
  public final boolean equals(Object o) {
    if (o == this) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Enchantment other = (Enchantment) o;
    return this.hasCustomEnchantment(other.getDisplay(1));
  }
  @Override
  public final String toString() {
    return getDisplay("");
  }
  @Override
  public final int hashCode() {
    return getName().hashCode();
  }
}
