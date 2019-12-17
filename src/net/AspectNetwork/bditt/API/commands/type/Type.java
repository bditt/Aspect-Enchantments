package net.AspectNetwork.bditt.API.commands.type;


import java.util.Collection;
import java.util.List;

import org.bukkit.command.CommandSender;

import net.AspectNetwork.bditt.API.commands.entities.Named;
import net.AspectNetwork.bditt.API.commands.exceptions.TypeException;

public interface Type<T> extends Named {

  String getInvelidErrorMessage(String arg);

  String getName();

  T read(String arg, CommandSender sender) throws TypeException;
  T read(CommandSender sender) throws TypeException;
  T read(String arg) throws TypeException;
  T read() throws TypeException;

  boolean isValid(String arg, CommandSender sender);

  Collection<String> getTabList(CommandSender sender, String arg);
  List<String> getTabListFiltered(CommandSender sender, String arg);

  boolean allowSpaceAfterTab = false;
  boolean allowSpaceAfterTab();
  <T extends Type> T setAllowSpaceAfterTab(boolean allowSpaceAfterTab);

  boolean equals(T type1, T type2);
  boolean equalsInner(T type1, T type2);
}
