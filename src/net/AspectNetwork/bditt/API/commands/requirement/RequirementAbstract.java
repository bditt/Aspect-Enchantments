package net.AspectNetwork.bditt.API.commands.requirement;


import java.io.Serializable;

import org.bukkit.command.CommandSender;

import net.AspectNetwork.bditt.API.commands.CEAPICommand;

public abstract class RequirementAbstract implements Requirement, Serializable {

  private static final long serialVersionUID = 1L;

  // -------------------------------------------- //
  // OVERRIDE
  // -------------------------------------------- //

  public boolean apply(CommandSender sender) {
    return this.apply(sender, null);
  }

  @Override
  public String createErrorMessage(CommandSender sender) {
    return this.createErrorMessage(sender, null);
  }

	/*public static String getDesc(CEAPICommand command) {
		if(command == null) return "do that";
		return command.getDesc();
	}*/

  // -------------------------------------------- //
  // BULK
  // -------------------------------------------- //

  public static boolean isRequirementsMet(Iterable<Requirement> requirements, CommandSender sender,
      CEAPICommand command, boolean verboose) {
    String error = getRequirementsError(requirements, sender, command, verboose);
    if (error != null && verboose) sender.sendMessage(error);
    return error == null;
  }

  public static String getRequirementsError(Iterable<Requirement> requirements,
      CommandSender sender, CEAPICommand command, boolean verboose) {
    for (Requirement requirement : requirements) {
      if (requirement.apply(sender, command)) continue;
      if (!verboose) return "";
      return requirement.createErrorMessage(sender, command);
    }
    return null;
  }

}
