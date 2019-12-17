package net.AspectNetwork.bditt.API.commands.ceapi;


import net.AspectNetwork.bditt.API.EnchantmentRegistry;
import net.AspectNetwork.bditt.API.RegisteredEnchantment;
import net.AspectNetwork.bditt.API.commands.CEAPICommand;
import net.AspectNetwork.bditt.API.commands.ceapi.type.TypeCeapiEnchantmentOptions;
import net.AspectNetwork.bditt.API.commands.ceapi.type.TypeCustomEnchantment;
import net.AspectNetwork.bditt.API.commands.exceptions.CEAPICommandException;
import net.AspectNetwork.bditt.API.commands.requirement.RequirementHasPerm;
import net.AspectNetwork.bditt.API.enchantment.Enchantment;
import net.AspectNetwork.bditt.API.utils.Text;

public class CeapiEnchantment extends CEAPICommand {

  public CeapiEnchantment() {
    this.addAlias("enchantment");

    this.addRequirements(RequirementHasPerm.get("adx.ceapi.enchantment"));

    this.addParameter(TypeCustomEnchantment.get());
    this.addParameter("info", TypeCeapiEnchantmentOptions.get(), "SubCommand", "not null");
  }

  @Override
  public void perform() throws CEAPICommandException {
    RegisteredEnchantment registeredEnchantment = EnchantmentRegistry
        .getFromID(this.getArgs().get(0));
    Enchantment enchantment = registeredEnchantment.getEnchantment();

    sender.sendMessage(Text.parse(
        "&r -------------&6[&r" + enchantment.getDisplay("").trim() + "&6]&r-------------&r"));
    sender.sendMessage(Text.parse("Name: " + enchantment.getName()));
    sender.sendMessage(Text.parse("Active: " + registeredEnchantment.isActive()));
    sender.sendMessage(Text.parse("Priority: " + enchantment.getPriority()));
    sender.sendMessage(Text.parse("Max Level: " + enchantment.getMaxLvl()));
    sender.sendMessage(Text.parse("Description: " + enchantment.getDescription()));
  }
}
