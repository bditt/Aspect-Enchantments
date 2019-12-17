package net.AspectNetwork.bditt.API.commands.ceapi.type;


import org.bukkit.command.CommandSender;

import net.AspectNetwork.bditt.API.commands.exceptions.TypeException;
import net.AspectNetwork.bditt.API.commands.type.TypeAbstract;

public class TypeCeapiEnchantmentOptions extends TypeAbstract<String> {

  private static final TypeCeapiEnchantmentOptions i = new TypeCeapiEnchantmentOptions();
  public static TypeCeapiEnchantmentOptions get() {
    return i;
  }


  public static enum Options {

  }

  @Override
  public String getInvelidErrorMessage(String arg) {
    return null;
  }

  @Override
  public String read(String arg, CommandSender sender) throws TypeException {
    return arg;
  }
}
