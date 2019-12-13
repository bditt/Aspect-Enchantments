package net.AspectNetwork.bditt.Enchantments;

import java.util.List;
import java.util.Random;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CrazedMiner implements Listener{
	@EventHandler
	public void onBreak(BlockBreakEvent e)
	{
		ItemStack hand = e.getPlayer().getInventory().getItemInMainHand();
		if (hand.hasItemMeta())
		{
			List<String> lores = hand.getItemMeta().getLore();
			if (lores.contains(org.bukkit.ChatColor.GOLD + "CrazyMiner I"))
			{
				if(new Random().nextInt(100) < 25) 
				{
					PotionEffect effect = new PotionEffect(PotionEffectType.FAST_DIGGING, 200, 0);
					e.getPlayer().addPotionEffect(effect);
				}
			}
			else if (lores.contains(org.bukkit.ChatColor.GOLD + "CrazyMiner II"))
			{
				if(new Random().nextInt(100) < 25) 
				{
					PotionEffect effect = new PotionEffect(PotionEffectType.FAST_DIGGING, 200, 1);
					e.getPlayer().addPotionEffect(effect);
				}
			}
		}
	}
}
