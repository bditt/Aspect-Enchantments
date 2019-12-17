package net.AspectNetwork.bditt.API.config;


import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import net.AspectNetwork.bditt.API.config.option.BooleanOption;
import net.AspectNetwork.bditt.API.config.option.StringOption;

public class DefaultConfig extends Config {

  public final BooleanOption COLOR_CODE_SPECIFIC;
  public final StringOption MESSAGE_LOCALIZATION_FILE;
  public final BooleanOption CHECK_FOR_UPDATES;


  // Constructor
  public DefaultConfig(Plugin plugin) {
    super(plugin, "config");

    COLOR_CODE_SPECIFIC = new BooleanOption("colorCodeSpecific", true);
    MESSAGE_LOCALIZATION_FILE = new StringOption("messageLocalizationFile", "en-US");
    CHECK_FOR_UPDATES = new BooleanOption("checkForUpdates", false);
  }

  @Override
  public void onLoad(YamlConfiguration config) {
    COLOR_CODE_SPECIFIC.loadIfExist(this);
    MESSAGE_LOCALIZATION_FILE.loadIfExist(this);
    CHECK_FOR_UPDATES.loadIfExist(this);
  }

  @Override
  public void onSave(YamlConfiguration config) {
  }
}
