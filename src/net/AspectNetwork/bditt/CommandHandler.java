package net.AspectNetwork.bditt;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CommandHandler implements CommandExecutor {
	@SuppressWarnings("deprecation")
	@Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (args.length < 0)
		{
			Player player = (Player) sender;
			player.sendMessage("Usage: /aenchant <enchantment>");
			return true;
		}
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("aenchant.enchant"))
            {
	            player.sendMessage("0 = " + args[0]);
	            player.sendMessage("1 = " + args[1]);
	            ItemStack handitem = player.getInventory().getItemInMainHand();
	            ItemMeta meta = handitem.getItemMeta();
	            List<String> lores = meta.hasLore() ? meta.getLore() : new ArrayList<>();
	            player.sendMessage("2 = " + args[0].length());
	            player.sendMessage("3 = " + ("CrazyMiner").length());
	            player.sendMessage("4 = " + (args[0].equals("CrazyMiner")));
	            if (args[0].equals("CrazyMiner"))
	            {
	            	player.sendMessage("Adding Crazy Miner!");
	            	lores.add(org.bukkit.ChatColor.GOLD + "CrazyMiner " + args[1]);
	            	meta.setLore(lores);
	                handitem.setItemMeta(meta);
	            }
	            else if (args[0].equals("Vampire"))
	            {
	            	player.sendMessage("Adding Vampire!");
	            	lores.add(org.bukkit.ChatColor.GOLD + "Vampire " + args[1]);
	            	meta.setLore(lores);
	                handitem.setItemMeta(meta);	
	            }
            }
        }

        // If the player (or console) uses our command correct, we can return true
        return true;
    }
}
