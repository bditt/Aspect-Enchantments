package net.AspectNetwork.bditt.API.commands.ceapi;


import net.AspectNetwork.bditt.API.CEAPIPermissions;
import net.AspectNetwork.bditt.API.commands.CEAPICommand;
import net.AspectNetwork.bditt.API.commands.requirement.RequirementHasPerm;

public class Ceapi extends CEAPICommand {

  public Ceapi() {
    this.addAlias("ceapi");
    this.addAlias("CustomEnchantmentAPI");

    this.addRequirements(RequirementHasPerm.get(CEAPIPermissions.USE));

    this.addSubCommands(new CeapiList());
    this.addSubCommands(new CeapiEnchant());
    this.addSubCommands(new CeapiUnenchnat());
    this.addSubCommands(new CeapiReloadConfig());
    this.addSubCommands(new CeapiEnchantment());
  }
}
