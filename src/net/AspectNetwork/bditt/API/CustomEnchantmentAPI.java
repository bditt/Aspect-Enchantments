package net.AspectNetwork.bditt.API;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import net.AspectNetwork.bditt.API.abst.api.NSM;
import net.AspectNetwork.bditt.API.commands.CEAPICommand;
import net.AspectNetwork.bditt.API.commands.ceapi.Ceapi;
import net.AspectNetwork.bditt.API.config.DefaultConfig;
import net.AspectNetwork.bditt.API.config.EnchantmentsConfig;
import net.AspectNetwork.bditt.API.config.LanguageConfig;
import net.AspectNetwork.bditt.API.listeners.CEAPIListenerUtils;
import net.AspectNetwork.bditt.API.listeners.DefaultEventsListener;
import net.AspectNetwork.bditt.API.listeners.extra.EEquip;
import net.AspectNetwork.bditt.API.plugin.TLogger;
import net.AspectNetwork.bditt.API.utils.GameLogger;
import net.AspectNetwork.bditt.API.utils.UpdateChecker;

public class CustomEnchantmentAPI extends JavaPlugin {

  // Global fields
  private static final GameLogger ceapiLogger = new GameLogger();
  /**
   * Returns the logger for all Custom Enchantment API Plugins
   *
   * @return the Custom Enchantment API Logger
   */
  public static GameLogger getCEAPILogger() {
    return ceapiLogger;
  }

  private static CustomEnchantmentAPI instance;
  /**
   * This method returns the currently running instance of Plugin
   *
   * @return The plugin
   */
  public static CustomEnchantmentAPI getInstance() {
    return instance;
  }

  // End of Global Fields
  private final TLogger logger;
  /**
   * Returns the logger For the CustomEnchantmentAPI Plugin.
   *
   * @return The logger
   */
  public TLogger getTLogger() {
    return logger;
  }

  private final DefaultConfig dc;
  /**
   * Returns the config.yml file and its options.
   *
   * @return The config.yml file.
   */
  public DefaultConfig getDefaultConfig() {
    return dc;
  }

  private final EnchantmentsConfig ec;
  /**
   * Returns the Enchantments.yml file.
   *
   * @return The Enchantments.yml file.
   */
  public EnchantmentsConfig getEnchantmentsConfig() {
    return ec;
  }

  private LanguageConfig lc;
  /**
   * Returns the currently defined LanguageConfig in the DefaultConfig.
   *
   * @return The current LanguageConfig.
   */
  public LanguageConfig getLanguageConfig() {
    return lc;
  }


  private final String serverVersion;
  /**
   * Gets the server NSMVersion like v1_8.. (IDK) i newer checked.
   *
   * @return The full server NSMVersion.
   */
  public final String getServerVersion() {
    return serverVersion;
  }

  private final String[] supportedVersions;
  /**
   * Gets the supported versions of bukkit/spigot.
   *
   * @return Return s a array with the versions like v1_8, v1_9 ,...
   */
  public final String[] getSupportedVersions() {
    return supportedVersions;
  }

  public final String NSMVersion;
  /**
   * Gets the closest string resembling the {@code getServerVersion()} String.
   *
   * @return The NSMVersion used for the {@code NSM}
   */
  public final String getNSMVersion() {
    return NSMVersion;
  }

  private NSM nsm;
  /**
   * Returns the NSU used dynamic-ly assigned by the current Bukkit NSMVersion.
   *
   * @return The dynamic NSU NSMVersion
   */
  public NSM getNSM() {
    return nsm;
  }

  // Constructor
  public CustomEnchantmentAPI() {
    this.supportedVersions = loadSupportedVersions();

    String packageName = this.getServer().getClass().getPackage().getName();
    serverVersion = packageName.substring(packageName.lastIndexOf('.') + 1);

    this.NSMVersion = loadVersion();

    instance = this;
    logger = new TLogger(this);
    ceapiLogger.getLogger().setParent(this.getLogger());
    ceapiLogger.getLogger().setUseParentHandlers(false);
    ceapiLogger.createDefaultLogFiles(this.getDataFolder());
    {
      dc = new DefaultConfig(this);
      ec = new EnchantmentsConfig(this);
      reloadConfigs();
    }
  }
  private String[] loadSupportedVersions() {
    Set<String> supportedVersions = new HashSet<>();
    try {
      ZipInputStream zip = new ZipInputStream(new FileInputStream(this.getFile().getPath()));
      String prefix = this.getClass().getPackage().getName() + ".abst.";

      for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
        if (!entry.isDirectory()) continue;

        String path = entry.getName()
            .replace(File.separatorChar, '.')
            .replace('/', '.');
        if (!path.startsWith(prefix)) continue;

        String version = path
            .replaceFirst(path.substring(0, prefix.length()), "")
            .split("\\\\.")[0]
            .replace('.', ' ')
            .trim();
        if (version.length() == 0 || version.startsWith("api")) continue;

        supportedVersions.add(version);
      }
      zip.close();
    } catch (Exception e) {
    }
    return supportedVersions.toArray(new String[supportedVersions.size()]);
  }
  private String loadVersion() {
    String current = "";
    for (String v : this.supportedVersions) {
      if (!serverVersion.toLowerCase().startsWith(v.toLowerCase())) continue;
      if (v.length() < current.length()) continue;
      current = v;

    }
    return current;
  }

  private static final String[] languageConfigs = new String[] {
      "en-US",
      "Template-en-US"
  };
  /**
   * This method creates(If they don't exist) and reloads the config Files.
   */
  public void reloadConfigs() {
    dc.createFileIfDoesNotExist();
    ec.createFileIfDoesNotExist();

    for (String languageConfig : languageConfigs) {
      File newFile = new File(this.getDataFolder(), "/locale/" + languageConfig + ".yml");
      if (newFile.exists()) continue;

      logger.info("File: 'locale/" + newFile.getName() + "' doesn't exist, creating...");
      InputStream in = null;
      FileOutputStream out = null;
      try {
        newFile.getParentFile().mkdir();
        newFile.createNewFile();

        in = this.getResource(languageConfig + ".yml");
        out = new FileOutputStream(newFile);
        int read;
        byte[] bytes = new byte[1024];
        while ((read = in.read(bytes)) != -1) {
          out.write(bytes, 0, read);
        }
        logger.info("Created File: 'locale/" + newFile.getName() + "'");
      } catch (Exception e) {
        logger.warning("Couldn't create File: 'locale/" + newFile.getName() + "'");
      } finally {
        try {
          if (in != null)
            in.close();
          if (out != null)
            out.close();
        } catch (IOException ignored) {
        }
      }
    }
    dc.load();
    ec.load();
    lc = new LanguageConfig(this, "locale/" + dc.MESSAGE_LOCALIZATION_FILE.getValue());

    EnchantmentRegistry.rebuildEnchantmentsArray();
  }

  @Override
  public void onEnable() {
    instance = this;
    ceapiLogger.createDefaultLogFiles(this.getDataFolder());
    logger.preEnabled(true);
    {
      try {
        final Class<?> clazz = Class
            .forName("adx.audioxd.customenchantmentapi.abst." + NSMVersion + ".NSMHandler");
        if (NSM.class.isAssignableFrom(clazz)) { // Make sure it actually implements NMS
          this.nsm = (NSM) clazz.getConstructor().newInstance(); // Set our handler
        }
      } catch (final Exception e) {
        logger.info(e.getMessage());
        logger.severe("Could not find support for Spigot " + serverVersion + ".");
        this.setEnabled(false);
        onDisable();
        return;
      }

      reloadConfigs();

      EEquip.clear();
      this.getServer().getOnlinePlayers().forEach(EEquip::loadPlayer);
      Bukkit.getPluginManager().registerEvents(new DefaultEventsListener(this), this);

      try {
        Method notMain = CEAPIListenerUtils.class
            .getMethod("itemNotInMainHand", LivingEntity.class, ItemStack.class);
        Method notOff = CEAPIListenerUtils.class
            .getMethod("itemNotInOffHand", LivingEntity.class, ItemStack.class);
        Method main = CEAPIListenerUtils.class
            .getMethod("itemInMainHand", LivingEntity.class, ItemStack.class);
        Method off = CEAPIListenerUtils.class
            .getMethod("itemInOffHand", LivingEntity.class, ItemStack.class);
        if (notMain == null || notOff == null || main == null || off == null)
          return;
        Bukkit.getPluginManager()
            .registerEvents(nsm.getVersionListener(notMain, notOff, main, off), this);
      } catch (NoSuchMethodException e) {
        e.printStackTrace();
      }

      CEAPICommand.registerCommand(this, new Ceapi());

      if (dc.CHECK_FOR_UPDATES.getValue()) {
        UpdateChecker uc = new UpdateChecker(
            "http://dev.bukkit.org/bukkit-plugins/customeenchantmentapi/files.rss");
        if (uc.isUpdateNeeded(this)) {
          logger.info(lc.NEW_VERSION_AVAILABLE.format(uc.getLatestVersion(), uc.getDownloadLink()));
        } else {
          logger.info(lc.NO_VERSION_AVAILABLE.format());
        }
      }
    }
    logger.enabled(true);
  }
  @Override
  public void onDisable() {
    logger.preEnabled(false);
    {
      EnchantmentRegistry.reset();
    }
    logger.enabled(false);
    ceapiLogger.closeActiveLogFiles();
    instance = null;
  }
}
