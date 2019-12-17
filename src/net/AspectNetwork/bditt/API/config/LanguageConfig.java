package net.AspectNetwork.bditt.API.config;


import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import net.AspectNetwork.bditt.API.config.option.StringOption;

public class LanguageConfig extends Config {

  public final StringOption RELOAD_CONFIG = new StringOption(
      "messages.reloadConfig",
      "&2Reloaded config files."
  );

  // ---------------------------------------------- //
  //                     ERRORS                     //
  // ---------------------------------------------- //
  public final StringOption INVALID = new StringOption(
      "error.invalid",
      "&c'%1$s' is not valid."
  );
  public final StringOption NO_PERMISSION = new StringOption(
      "error.errorYouDoNotHavePermission",
      "&cYou don't have access to use that command."
  );

  public final StringOption TO_MANY_ENOUGH_ARGUMENTS = new StringOption(
      "error.errorToManyArguments",
      "&cTo many arguments."
  );
  public final StringOption NOT_ENOUGH_ARGUMENTS = new StringOption(
      "error.errorNotEnoughArguments",
      "&cNot enough arguments."
  );
  public final StringOption UNKNOWN_COMMAND = new StringOption(
      "error.unknownCommand",
      "&cUnknown command!"
  );
  public final StringOption UNKNOWN_ENCHANTMENT = new StringOption(
      "error.unknownEnchantment",
      "&cUnknown Enchantment!"
  );
  public final StringOption NO_ITEM_IN_HAND = new StringOption(
      "error.noItemInHand",
      "&cYou must have an item in your main hand."
  );
  public final StringOption NOT_PLAYER = new StringOption(
      "error.notPlayer",
      "&cThis command must be executed by a player!"
  );
  public final StringOption ONLINE_PLAYER_NOT_FOUND = new StringOption(
      "error.onlinePlayerNotFound",
      "&5Player not found."
  );
  public final StringOption OFFLINE_PLAYER_NOT_FOUND = new StringOption(
      "error.offlinePlayerNotFound",
      "&5Player not found."
  );

  // ---------------------------------------------- //
  //                 ENCHANTING                     //
  // ---------------------------------------------- //
  public final StringOption LEVEL_LESS_THAN_ONE = new StringOption(
      "enchant.errorLessThanOne",
      "&5The level must be more or equal to 1."
  );
  public final StringOption ENCHANT_SUCCESS = new StringOption(
      "enchant.success",
      "&2Enchanted item in hand with %s"
  );
  public final StringOption ENCHANT_ERROR = new StringOption(
      "enchant.error",
      "&cDidn't enchant item with %s"
  );

  // ---------------------------------------------- //
  //                UN-ENCHANTING                   //
  // ---------------------------------------------- //

  public final StringOption UNENCHANT_SUCCESS = new StringOption(
      "unenchant.success",
      "&2Un-enchanted item in hand with %s"
  );
  public final StringOption UNENCHANT_ERROR = new StringOption(
      "unenchant.error",
      "&cDidn't un-enchant item with %s"
  );

  // ---------------------------------------------- //
  //             VERSION CHECKING                   //
  // ---------------------------------------------- //

  public final StringOption NEW_VERSION_AVAILABLE = new StringOption(
      "info.newVersionAvailable",
      "A new version is available version: %1$s, download link: %2$s"
  );
  public final StringOption NO_VERSION_AVAILABLE = new StringOption(
      "info.noVersionAvailable",
      "No new versions are available."
  );

  // ---------------------------------------------- //
  //                 CONSTRUCTOR                    //
  // ---------------------------------------------- //

  public LanguageConfig(Plugin plugin, String file) {
    super(plugin, file);
    load();
  }

  @Override
  public void onLoad(YamlConfiguration config) {
    RELOAD_CONFIG.loadIfExist(this);
    INVALID.loadIfExist(this);
    NO_PERMISSION.loadIfExist(this);
    TO_MANY_ENOUGH_ARGUMENTS.loadIfExist(this);
    NOT_ENOUGH_ARGUMENTS.loadIfExist(this);
    UNKNOWN_COMMAND.loadIfExist(this);
    UNKNOWN_ENCHANTMENT.loadIfExist(this);
    NO_ITEM_IN_HAND.loadIfExist(this);
    NOT_PLAYER.loadIfExist(this);
    LEVEL_LESS_THAN_ONE.loadIfExist(this);

    ONLINE_PLAYER_NOT_FOUND.loadIfExist(this);
    OFFLINE_PLAYER_NOT_FOUND.loadIfExist(this);

    ENCHANT_SUCCESS.loadIfExist(this);
    ENCHANT_ERROR.loadIfExist(this);

    UNENCHANT_SUCCESS.loadIfExist(this);
    UNENCHANT_ERROR.loadIfExist(this);

    NEW_VERSION_AVAILABLE.loadIfExist(this);
    NO_VERSION_AVAILABLE.loadIfExist(this);
  }

  public void onSave(YamlConfiguration config) {
  }

}
