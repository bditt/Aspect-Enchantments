package net.AspectNetwork.bditt.Enchantments;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;

import net.AspectNetwork.bditt.API.enchantment.Enchantment;
import net.AspectNetwork.bditt.API.enchantment.event.EnchantmentEventHandler;
import net.AspectNetwork.bditt.API.enums.ItemType;
import net.AspectNetwork.bditt.API.events.world.EBlockBreakEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Explosive extends Enchantment {

	public Explosive() {
		super(ChatColor.GREEN + "Explosive", ItemType.ALL_OFF_THE_ABOVE, 1);
	}

	@EnchantmentEventHandler
	public void explode(EBlockBreakEvent event) {
		if(!(event.getOwner() instanceof Player)) return;

		Player owner = (Player) event.getOwner();
		// A 70% chance
		if(new Random().nextInt(100) < 70) {
			Location blockLocation = event.getBlock().getLocation();

			int radius = 1;
			List<Block> blocks = new ArrayList<>();
			for(int x = (blockLocation.getBlockX() - radius); x <= (blockLocation.getBlockX() + radius); x++) {
				for(int y = (blockLocation.getBlockY() - radius); y <= (blockLocation.getBlockY() + radius); y++) {
					for(int z = (blockLocation.getBlockZ() - radius); z <= (blockLocation.getBlockZ() + radius); z++) {
						Block block = blockLocation.getWorld().getBlockAt(x, y, z);
						blocks.add(block);
					}
				}
			}

			EntityExplodeEvent e = new EntityExplodeEvent(null, blockLocation, blocks, 0);
			Bukkit.getPluginManager().callEvent(e);
			if(!e.isCancelled()) {
				// Explosion effect does 0 damage.
				event.getOwner().getWorld().createExplosion(blockLocation, 0);

				for(Block block : e.blockList()) {
					for(ItemStack item : block.getDrops()) {
						owner.getInventory().addItem(item);
					}
					block.setType(Material.AIR);
				}

				owner.updateInventory();
			}
		}

	}
}