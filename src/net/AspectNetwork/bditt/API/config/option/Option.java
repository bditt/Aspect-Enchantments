package net.AspectNetwork.bditt.API.config.option;


import net.AspectNetwork.bditt.API.config.Config;

public class Option {

  private final String path;
  public String getPath() {
    return path;
  }

  private Object value;
  public Object getValue() {
    return value;
  }
  public void setValue(Object value) {
    this.value = value;
  }


  // Constructor
  public Option(String path, Object value) {
    this.path = path;
    this.value = value;
  }


  public final void loadIfExist(Config config) {
    loadIfExist(config, this);
  }
  public static void loadIfExist(Config config, Option option) {
    if (config.getConfig().isSet(option.getPath())) {
      option.setValue(config.getConfig().get(option.getPath()));
    } else {
      save(config, option);
      config.save();
    }
  }

  public final void save(Config config) {
    save(config, this);
  }
  public static void save(Config config, Option option) {
    config.getConfig().set(option.getPath(), option.getValue());
  }


  @Override
  public final boolean equals(Object o) {
    if (o == this) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Option other = (Option) o;
    return getPath().equalsIgnoreCase(other.getPath());
  }
}
