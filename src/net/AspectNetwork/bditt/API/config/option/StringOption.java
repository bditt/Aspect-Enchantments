package net.AspectNetwork.bditt.API.config.option;


import net.AspectNetwork.bditt.API.config.Config;
import net.AspectNetwork.bditt.API.utils.Text;

public class StringOption {

  private final String path;

  public String getPath() {
    return path;
  }

  private String value;

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  // Constructor
  public StringOption(String path, String value) {
    this.path = path;
    this.value = value;
  }

  public final void loadIfExist(Config config) {
    loadIfExist(config, this);
  }

  public static void loadIfExist(Config config, StringOption option) {
    if (config.getConfig().isSet(option.getPath())) {
      option.setValue(config.getConfig().getString(option.getPath()));
    } else {
      save(config, option);
      config.save();
    }
  }

  public final void save(Config config) {
    save(config, this);
  }

  public static void save(Config config, StringOption option) {
    config.getConfig().set(option.getPath(), option.getValue());
  }

  public final String format(String... o) {
    return Text.parse(value, (Object[]) o);
  }


  @Override
  public final boolean equals(Object o) {
    if (o == this) return true;
    if (o == null || getClass() != o.getClass()) return false;
    StringOption other = (StringOption) o;
    return getPath().equalsIgnoreCase(other.getPath());
  }
}
