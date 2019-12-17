package net.AspectNetwork.bditt.API.commands.ceapi.type;


import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;

import net.AspectNetwork.bditt.API.CustomEnchantmentAPI;
import net.AspectNetwork.bditt.API.commands.exceptions.TypeException;
import net.AspectNetwork.bditt.API.commands.type.TypeAbstract;

public class TypeInteger extends TypeAbstract<Integer> {

  private static final TypeInteger i = new TypeInteger();
  public static TypeInteger get() {
    return i;
  }

  TypeInteger() {
  }

  @Override
  public String getInvelidErrorMessage(String arg) {
    return CustomEnchantmentAPI.getInstance().getLanguageConfig().INVALID.format(arg);
  }

  @Override
  public Integer read(String arg, CommandSender sender) throws TypeException {
    return Integer.parseInt(arg);
  }

  @Override
  public boolean isValid(String arg, CommandSender sender) {
    return StringUtils.isNumeric(arg);
  }

  @Override
  public Collection<String> getTabList(CommandSender sender, String arg) {
    return arg.length() == 0 ? Arrays.asList("" + 1) : null;
  }
}
