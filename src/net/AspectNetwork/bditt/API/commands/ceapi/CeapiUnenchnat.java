package net.AspectNetwork.bditt.API.commands.ceapi;


import org.bukkit.inventory.ItemStack;

import net.AspectNetwork.bditt.API.CEAPIPermissions;
import net.AspectNetwork.bditt.API.CustomEnchantmentAPI;
import net.AspectNetwork.bditt.API.EnchantmentRegistry;
import net.AspectNetwork.bditt.API.commands.CEAPICommand;
import net.AspectNetwork.bditt.API.commands.ceapi.type.TypeCustomEnchantment;
import net.AspectNetwork.bditt.API.commands.exceptions.CEAPICommandException;
import net.AspectNetwork.bditt.API.commands.requirement.RequirementHasItemInHand;
import net.AspectNetwork.bditt.API.commands.requirement.RequirementHasPerm;
import net.AspectNetwork.bditt.API.commands.requirement.RequirementIsPlayer;
import net.AspectNetwork.bditt.API.config.LanguageConfig;
import net.AspectNetwork.bditt.API.enchantment.Enchantment;
import net.AspectNetwork.bditt.API.events.inventory.hand.enums.HandType;
import net.AspectNetwork.bditt.API.listeners.CEAPIListenerUtils;
import net.AspectNetwork.bditt.API.utils.ItemUtil;

public class CeapiUnenchnat extends CEAPICommand {

  public CeapiUnenchnat() {
    this.addAlias("unenchant");

    this.addRequirements(RequirementHasPerm.get(CEAPIPermissions.UNENCHANT_USE));
    this.addRequirements(RequirementIsPlayer.get());
    this.addRequirements(RequirementHasItemInHand.get());

    this.addParameter(TypeCustomEnchantment.get());
  }

  @Override
  public void perform() throws CEAPICommandException {
    if (sender == null) throw new CEAPICommandException("Sender is null");
    LanguageConfig lc = CustomEnchantmentAPI.getInstance().getLanguageConfig();

    Enchantment ench = this.readArg();
    if (ench == null)
      throw new CEAPICommandException(lc.UNKNOWN_ENCHANTMENT.format(this.readArgAt(0)));

    ItemStack previous = new ItemStack(ItemUtil.getMainHandItem(me));
    if (EnchantmentRegistry.unenchant(ItemUtil.getMainHandItem(me), ench)) {
      CEAPIListenerUtils.itemNotInHand(me, previous, HandType.MAIN);
      throw new CEAPICommandException(lc.UNENCHANT_SUCCESS.format(ench.getDisplay("")));
    }
    throw new CEAPICommandException(lc.UNENCHANT_ERROR.format(ench.getDisplay("")));
  }
}
