package net.AspectNetwork.bditt.API.commands.requirement;


import org.bukkit.command.CommandSender;

import net.AspectNetwork.bditt.API.CEAPIPermissions;
import net.AspectNetwork.bditt.API.CustomEnchantmentAPI;
import net.AspectNetwork.bditt.API.commands.CEAPICommand;

public class RequirementHasPerm extends RequirementAbstract {

  private final String permissionId;
  public String getPermissionId() {
    return this.permissionId;
  }

  public RequirementHasPerm(String permissionId) {
    this.permissionId = permissionId;
  }

  @Override
  public boolean apply(CommandSender sender, CEAPICommand command) {
    return sender.hasPermission(this.permissionId);
  }

  @Override
  public String createErrorMessage(CommandSender sender, CEAPICommand command) {
    if (command == null)
      CustomEnchantmentAPI.getInstance().getLanguageConfig().NOT_PLAYER.format(permissionId, "");
    return CustomEnchantmentAPI.getInstance().getLanguageConfig().NO_PERMISSION
        .format(permissionId, command.getFullCommand());
  }

  public static RequirementHasPerm get(String permissionId) {
    return new RequirementHasPerm(permissionId);
  }
  public static RequirementHasPerm get(CEAPIPermissions permission) {
    return get(permission.getValue());
  }
}
