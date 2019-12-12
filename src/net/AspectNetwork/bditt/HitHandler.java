package net.AspectNetwork.bditt;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class HitHandler implements Listener{
	public static int generateRandomIntIntRange(int min, int max) {
	    Random r = new Random();
	    return r.nextInt((max - min) + 1) + min;
	}
	
	@EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            Player Damaged = (Player) e.getEntity();
            Player Damager = (Player) e.getDamager();
            PlayerInventory DamagedItems = Damaged.getInventory();
            ItemStack DamagedHelmet = DamagedItems.getHelmet();
            ItemStack DamagedChestplate = DamagedItems.getChestplate();
            ItemStack DamagedLeggings = DamagedItems.getLeggings();
            ItemStack DamagedBoots = DamagedItems.getBoots();
            PlayerInventory DamagerItems = Damager.getInventory();
            ItemStack DamagerWeapon = DamagerItems.getItemInMainHand();
            ItemMeta DamagerWeaponMeta = DamagerWeapon.getItemMeta();
            List<String> DamagerWeaponLores = DamagerWeaponMeta.hasLore() ? DamagerWeaponMeta.getLore() : new ArrayList<>();
            if (DamagerWeaponLores.size() > 0)
            {
	            for (String lore : DamagerWeaponLores)
	            {
	            	Damager.chat(lore);
	            }
            }
            if (DamagerWeaponLores.contains("Vampire"))
            {
            	Damager.chat(ChatColor.GOLD + "Weapon has Vampire!");
            	Damaged.damage(4.0);
        		Damager.setHealth(Damager.getHealth() + 4.0);
        		Damager.chat(ChatColor.GOLD + "I has stolen your health!");
            }
            Damager.chat(ChatColor.GOLD + "CE Done!");
        }
    }
}
