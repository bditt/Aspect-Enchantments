package net.AspectNetwork.bditt.Enchantments;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.AspectNetwork.bditt.API.enchantment.Enchantment;
import net.AspectNetwork.bditt.API.enchantment.event.EnchantmentEventHandler;
import net.AspectNetwork.bditt.API.enums.ItemType;
import net.AspectNetwork.bditt.API.events.inventory.EEquipEvent;
import net.AspectNetwork.bditt.API.events.inventory.EUnequipEvent;

public class Energized extends Enchantment {

	public Energized() {
		super(ChatColor.GOLD + "Energized" + ChatColor.GOLD, ItemType.ALL_OFF_THE_ABOVE, 40);
	}

	@EnchantmentEventHandler
	public void onEquip(EEquipEvent event,int lvl) {
		if (event.getOwner() instanceof Player) {
			Player player = (Player) event.getOwner();
			player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 720000, lvl-1));
		}
	}

	@EnchantmentEventHandler
	public void onUnequip(EUnequipEvent event) {
		if (event.getOwner() instanceof Player) {
			Player player = (Player) event.getOwner();
			player.removePotionEffect(PotionEffectType.SPEED);
		}
	}
}
