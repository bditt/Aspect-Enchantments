package net.AspectNetwork.bditt;

import net.AspectNetwork.bditt.API.EnchantmentRegistry;
import net.AspectNetwork.bditt.API.plugin.CEPLPlugin;
import net.AspectNetwork.bditt.API.enchantment.Enchantment;
import net.AspectNetwork.bditt.Enchantments.Energized;
import net.AspectNetwork.bditt.Enchantments.Explosive;

public class Main extends CEPLPlugin {
	
	public static Enchantment ENERGIZED = new Energized();
	public static Enchantment EXPLOSIVE = new Explosive();
	public Main plugin;
	
    @Override
    public void Enable() {
    	System.out.print("Aspect Enchantments Loading!");
    	if (!EnchantmentRegistry.register(this, ENERGIZED))
    	{
    		getPluginLogger().severe("Failed to load " + ENERGIZED.getName() + "!");
    	}
    	if (!EnchantmentRegistry.register(this, EXPLOSIVE))
    	{
    		getPluginLogger().severe("Failed to load " + EXPLOSIVE.getName() + "!");
    	}
    	
    	PluginManager pm = Bukkit.getPluginManager();
    	System.out.print("Aspect Enchantments Loaded!");
    }
    
    @Override
    public void Disable() {
		EnchantmentRegistry.unregisterAll(this);
	}
}
