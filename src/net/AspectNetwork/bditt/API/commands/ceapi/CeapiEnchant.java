package net.AspectNetwork.bditt.API.commands.ceapi;


import net.AspectNetwork.bditt.API.CEAPIPermissions;
import net.AspectNetwork.bditt.API.CustomEnchantmentAPI;
import net.AspectNetwork.bditt.API.EnchantmentRegistry;
import net.AspectNetwork.bditt.API.commands.CEAPICommand;
import net.AspectNetwork.bditt.API.commands.ceapi.type.TypeCustomEnchantment;
import net.AspectNetwork.bditt.API.commands.ceapi.type.TypeInteger;
import net.AspectNetwork.bditt.API.commands.exceptions.CEAPICommandException;
import net.AspectNetwork.bditt.API.commands.requirement.RequirementHasItemInHand;
import net.AspectNetwork.bditt.API.commands.requirement.RequirementHasPerm;
import net.AspectNetwork.bditt.API.commands.requirement.RequirementIsPlayer;
import net.AspectNetwork.bditt.API.config.LanguageConfig;
import net.AspectNetwork.bditt.API.enchantment.Enchantment;
import net.AspectNetwork.bditt.API.listeners.CEAPIListenerUtils;
import net.AspectNetwork.bditt.API.utils.ItemUtil;

public class CeapiEnchant extends CEAPICommand {

  public CeapiEnchant() {
    this.addAlias("enchant");

    this.addRequirements(RequirementHasPerm.get(CEAPIPermissions.ENCHANT_USE));
    this.addRequirements(RequirementIsPlayer.get());
    this.addRequirements(RequirementHasItemInHand.get());

    this.addParameter(TypeCustomEnchantment.get());
    this.addParameter(TypeInteger.get()).setDefaultDesc("Default 1");
  }

  @Override
  public void perform() throws CEAPICommandException {
    if (sender == null) throw new CEAPICommandException("Sender is null");
    LanguageConfig lc = CustomEnchantmentAPI.getInstance().getLanguageConfig();

    Enchantment ench = this.readArg(null);
    if (ench == null)
      throw new CEAPICommandException(lc.UNKNOWN_ENCHANTMENT.format(this.readArgAt(0)));

    int lvl = this.readArg(1);
    if (lvl < 1) throw new CEAPICommandException(lc.LEVEL_LESS_THAN_ONE.format());

    if (EnchantmentRegistry.enchant(ItemUtil.getMainHandItem(me), ench, lvl, true, true)) {
      CEAPIListenerUtils.itemInMainHand(me, ItemUtil.getMainHandItem(me));
      throw new CEAPICommandException(lc.ENCHANT_SUCCESS.format(ench.getDisplay(lvl)));
    }
    throw new CEAPICommandException(lc.ENCHANT_ERROR.format(ench.getDisplay(lvl)));
  }
}
