package net.AspectNetwork.bditt.API;


public enum CEAPIPermissions {
  NULL(""),
  ALL("*"),
  USE("use"),
  LIST("list"),
  ENCHANT_USE("enchant"),
  UNENCHANT_USE("unenchant"),
  ENCHANTMENT_PREFIX("enchantment."),
  ENCHANTMENT_ALL("enchantment.*"),;

  private final String value;
  public String getValue() {
    return "AspectNetwork.enchant." + value;
  }

  CEAPIPermissions(String value) {
    this.value = value;
  }
}
