package net.AspectNetwork.bditt.API.commands.ceapi.type;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import net.AspectNetwork.bditt.API.CEAPIPermissions;
import net.AspectNetwork.bditt.API.CustomEnchantmentAPI;
import net.AspectNetwork.bditt.API.EnchantmentRegistry;
import net.AspectNetwork.bditt.API.commands.exceptions.TypeException;
import net.AspectNetwork.bditt.API.commands.requirement.RequirementHasPerm;
import net.AspectNetwork.bditt.API.commands.type.TypeAbstract;
import net.AspectNetwork.bditt.API.enchantment.Enchantment;

public class TypeCustomEnchantment extends TypeAbstract<Enchantment> {

  private static final TypeCustomEnchantment i = new TypeCustomEnchantment();
  public static TypeCustomEnchantment get() {
    return i;
  }
  TypeCustomEnchantment() {
  }

  @Override
  public Enchantment read(String arg, CommandSender sender) throws TypeException {
    if (!hasPermission(sender, arg)) throw new TypeException(
        RequirementHasPerm.get(CEAPIPermissions.ENCHANTMENT_PREFIX + arg)
            .createErrorMessage(sender));
    return EnchantmentRegistry.getFromID(arg).getEnchantment();
  }

  @Override
  public String getInvelidErrorMessage(String arg) {
    return CustomEnchantmentAPI.getInstance().getLanguageConfig().UNKNOWN_ENCHANTMENT.format(arg);
  }

  @Override
  public Collection<String> getTabList(CommandSender sender, String arg) {
    List<String> out = new ArrayList<>();

    for (Plugin plugin : EnchantmentRegistry.getEnchantments().keySet()) {
      if (EnchantmentRegistry.getEnchantments().get(plugin) == null) continue;
      for (String enchName : EnchantmentRegistry.getEnchantments().get(plugin).keySet()) {
        Enchantment ench = EnchantmentRegistry.getEnchantments().get(plugin).get(enchName)
            .getEnchantment();
        String o = EnchantmentRegistry.getID(plugin, ench);
        if (o == null) continue;
        if (!hasPermission(sender, o)) continue;
        if (o.toLowerCase().startsWith(arg.toLowerCase())) out.add(o);
      }
    }
    return out;
  }

  private static boolean hasPermission(CommandSender sender, String o) {
    return RequirementHasPerm.get(CEAPIPermissions.ENCHANTMENT_PREFIX + o).apply(sender)
        || RequirementHasPerm.get(CEAPIPermissions.ENCHANTMENT_ALL).apply(sender);
  }
}
