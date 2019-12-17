package net.AspectNetwork.bditt.API.abst.api;


import java.lang.reflect.Method;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public interface NSM {

  ItemStack getItemInMainHand(LivingEntity player);
  ItemStack getItemInOffHand(LivingEntity player);

  boolean isHandMainHAnd(PlayerInteractEvent event);

  VersionListener getVersionListener(Method notMain, Method notOff, Method main, Method off);
}
