package net.AspectNetwork.bditt.API.commands.requirement;


import org.bukkit.command.CommandSender;

import net.AspectNetwork.bditt.API.commands.CEAPICommand;

public interface Requirement {

  boolean apply(CommandSender sender, CEAPICommand command);

  String createErrorMessage(CommandSender sender);
  String createErrorMessage(CommandSender sender, CEAPICommand command);
}
