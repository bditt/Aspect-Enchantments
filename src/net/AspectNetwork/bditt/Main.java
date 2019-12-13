package net.AspectNetwork.bditt;

import org.bukkit.*;
import org.bukkit.plugin.java.JavaPlugin;

import net.AspectNetwork.bditt.Enchantments.CrazedMiner;

public class Main extends JavaPlugin {
	  // Fired when plugin is first enabled
    @Override
    public void onEnable() {
    	System.out.print("Aspect Enchantments Loaded!");
    	this.getCommand("aenchant").setExecutor(new CommandHandler());
    	System.out.print("Aspect Enchantments Command Set!");
    	getServer().getPluginManager().registerEvents(new HitHandler(), this);
    	getServer().getPluginManager().registerEvents(new CrazedMiner(), this);
    }
    // Fired when plugin is disabled
    @Override
    public void onDisable() {

    }
}
