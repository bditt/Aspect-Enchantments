package net.AspectNetwork.bditt.API.config;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public abstract class Config {

  protected final File configFile;
  protected final File localFile;
  public final File getFile() {
    return configFile;
  }

  protected final Plugin plugin;
  protected YamlConfiguration config;
  public YamlConfiguration getConfig() {
    return config;
  }


  // Constructor
  public Config(Plugin plugin, String file) {
    this.localFile = new File(file + ".yml");
    this.configFile = new File(plugin.getDataFolder(), localFile.getPath());
    this.plugin = plugin;
  }

  public final void load() {
    createFileIfDoesNotExist();
    config = YamlConfiguration.loadConfiguration(configFile);
    onLoad(config);
  }
  public abstract void onLoad(YamlConfiguration config);

  public void createFileIfDoesNotExist() {
    if (!configFile.exists()) {
      if (configFile.getParentFile().mkdirs())
        copy(plugin.getResource(localFile.getName()), configFile);
    }
  }

  private void copy(InputStream in, File file) {
    if (in == null || file == null) return;
    try {
      OutputStream out = new FileOutputStream(file);
      byte[] buf = new byte[1024];
      int len;
      while ((len = in.read(buf)) > 0) {
        out.write(buf, 0, len);
      }
      out.close();
      in.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public final void save() {
    onSave(config);
    try {
      config.save(configFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  public abstract void onSave(YamlConfiguration config);
}
