package net.AspectNetwork.bditt.API.commands.ceapi.type;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.AspectNetwork.bditt.API.CustomEnchantmentAPI;
import net.AspectNetwork.bditt.API.commands.exceptions.TypeException;
import net.AspectNetwork.bditt.API.commands.type.TypeAbstract;
import net.AspectNetwork.bditt.API.utils.Text;

public class TypeOnlinePlayer extends TypeAbstract<Player> {

  private static final TypeOnlinePlayer i = new TypeOnlinePlayer();
  public static TypeOnlinePlayer get() {
    return i;
  }

  @Override
  public String getInvelidErrorMessage(String arg) {
    return CustomEnchantmentAPI.getInstance().getLanguageConfig().ONLINE_PLAYER_NOT_FOUND
        .format(arg);
  }

  @Override
  public Collection<String> getTabList(CommandSender sender, String arg) {
    List<String> onlinePlayers = new ArrayList<>();
    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
      if (!player.isOnline() || !player.isValid()) continue;
      onlinePlayers.add(player.getName());
    }
    return Text.getStartsWithIgnoreCase(onlinePlayers, arg);
  }

  @Override
  public boolean isValid(String arg, CommandSender sender) {
    return Bukkit.getServer().getPlayer(arg) != null;
  }

  @Override
  public Player read(String arg, CommandSender sender) throws TypeException {
    return Bukkit.getServer().getPlayer(arg);
  }
}
