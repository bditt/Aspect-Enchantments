package net.AspectNetwork.bditt.API.commands.requirement;


import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.AspectNetwork.bditt.API.CustomEnchantmentAPI;
import net.AspectNetwork.bditt.API.commands.CEAPICommand;
import net.AspectNetwork.bditt.API.utils.ItemUtil;

public class RequirementHasItemInHand implements Requirement {

  private static RequirementHasItemInHand i = new RequirementHasItemInHand();
  public static RequirementHasItemInHand get() {
    return i;
  }
  RequirementHasItemInHand() {
  }

  @Override
  public boolean apply(CommandSender sender, CEAPICommand command) {
    boolean flag = sender instanceof Player;
    if (!flag) return false;
    flag = !ItemUtil
        .isEmpty(CustomEnchantmentAPI.getInstance().getNSM().getItemInMainHand((Player) sender));
    return flag;
  }

  @Override
  public String createErrorMessage(CommandSender sender) {
    return createErrorMessage(sender, null);
  }

  @Override
  public String createErrorMessage(CommandSender sender, CEAPICommand command) {
    return CustomEnchantmentAPI.getInstance().getLanguageConfig().NO_ITEM_IN_HAND.format();
  }
}
