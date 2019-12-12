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
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            ItemStack handitem = player.getInventory().getItemInMainHand();
            ItemMeta meta = handitem.getItemMeta();
            List<String> lores = meta.hasLore() ? meta.getLore() : new ArrayList<>();
            lores.add(org.bukkit.ChatColor.GOLD + "Vampire");
            meta.setLore(lores);
            handitem.setItemMeta(meta);
        }

        // If the player (or console) uses our command correct, we can return true
        return true;
    }
}
