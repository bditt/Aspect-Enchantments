package net.AspectNetwork.bditt.API.commands.ceapi;


import net.AspectNetwork.bditt.API.CustomEnchantmentAPI;
import net.AspectNetwork.bditt.API.commands.CEAPICommand;
import net.AspectNetwork.bditt.API.commands.exceptions.CEAPICommandException;
import net.AspectNetwork.bditt.API.commands.requirement.RequirementIsOP;

public class CeapiReloadConfig extends CEAPICommand {

  public CeapiReloadConfig() {
    this.addAlias("reloadConfigs");

    // this.addRequirements(RequirementHasPerm.get("adx.ceapi.use"));
    this.addRequirements(RequirementIsOP.get());
  }

  @Override
  public void perform() throws CEAPICommandException {
    CustomEnchantmentAPI.getInstance().reloadConfigs();
    sender
        .sendMessage(CustomEnchantmentAPI.getInstance().getLanguageConfig().RELOAD_CONFIG.format());
  }
}
