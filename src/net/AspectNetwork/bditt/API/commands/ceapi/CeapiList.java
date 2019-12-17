package net.AspectNetwork.bditt.API.commands.ceapi;


import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

import net.AspectNetwork.bditt.API.CEAPIPermissions;
import net.AspectNetwork.bditt.API.EnchantmentRegistry;
import net.AspectNetwork.bditt.API.RegisteredEnchantment;
import net.AspectNetwork.bditt.API.commands.CEAPICommand;
import net.AspectNetwork.bditt.API.commands.exceptions.CEAPICommandException;
import net.AspectNetwork.bditt.API.commands.requirement.RequirementHasPerm;
import net.AspectNetwork.bditt.API.enchantment.Enchantment;

public class CeapiList extends CEAPICommand {

  public CeapiList() {
    this.addAlias("list");

    this.addRequirements(RequirementHasPerm.get(CEAPIPermissions.LIST));
  }

  @Override
  public void perform() throws CEAPICommandException {
    sender.sendMessage(ChatColor.GREEN + "============[Enchantments]============");
    for (Plugin plugin : EnchantmentRegistry.getEnchantments().keySet()) {
      sender.sendMessage(ChatColor.BOLD + "[" + plugin.getName() + "]");
      Map<String, RegisteredEnchantment> data = EnchantmentRegistry.getEnchantments().get(plugin);
      if (data == null) continue;

      List<Enchantment> active = Arrays.asList(EnchantmentRegistry.getEnchantmentsArray());

      for (String id : data.keySet()) {
        Enchantment ench = data.get(id).getEnchantment();
        if (ench == null) continue;

        sender.sendMessage(
            ChatColor.GOLD + " - " + ench.getDisplay("") + " : " + (active.contains(ench) ?
                ChatColor.GREEN + "Active" : ChatColor.DARK_RED + "Disabled"));
      }
    }
    sender.sendMessage(ChatColor.RED + "================[END]================");
  }
}
