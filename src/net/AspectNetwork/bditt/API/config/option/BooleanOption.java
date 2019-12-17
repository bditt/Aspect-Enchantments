package net.AspectNetwork.bditt.API.config.option;


import net.AspectNetwork.bditt.API.config.Config;

public class BooleanOption {

  private final String path;
  public String getPath() {
    return path;
  }

  private boolean value;
  public boolean getValue() {
    return value;
  }
  public void setValue(boolean value) {
    this.value = value;
  }


  // Constructor
  public BooleanOption(String path, boolean value) {
    this.path = path;
    this.value = value;
  }


  public final void loadIfExist(Config config) {
    loadIfExist(config, this);
  }
  public static void loadIfExist(Config config, BooleanOption option) {
    if (config.getConfig().isSet(option.getPath())) {
      option.setValue(config.getConfig().getBoolean(option.getPath()));
    } else {
      save(config, option);
      config.save();
    }
  }

  public final void save(Config config) {
    save(config, this);
  }
  public static void save(Config config, BooleanOption option) {
    config.getConfig().set(option.getPath(), option.getValue());
  }


  @Override
  public final boolean equals(Object o) {
    if (o == this) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BooleanOption other = (BooleanOption) o;
    return getPath().equalsIgnoreCase(other.getPath());
  }
}
