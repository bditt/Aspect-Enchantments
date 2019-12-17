package net.AspectNetwork.bditt.API.config.option;


import java.util.List;

import net.AspectNetwork.bditt.API.config.Config;

public class ListStringOption {

  private final String path;
  public String getPath() {
    return path;
  }

  private List<String> value;
  public List<String> getValue() {
    return value;
  }
  public void setValue(List<String> value) {
    this.value = value;
  }


  // Constructor
  public ListStringOption(String path, List<String> value) {
    this.path = path;
    this.value = value;
  }


  public final void loadIfExist(Config config) {
    loadIfExist(config, this);
  }
  public static void loadIfExist(Config config, ListStringOption option) {
    if (config.getConfig().isSet(option.getPath())) {
      option.setValue(config.getConfig().getStringList(option.getPath()));
    } else {
      save(config, option);
      config.save();
    }
  }

  public final void save(Config config) {
    save(config, this);
  }
  public static void save(Config config, ListStringOption option) {
    config.getConfig().set(option.getPath(), option.getValue());
  }


  @Override
  public final boolean equals(Object o) {
    if (o == this) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ListStringOption other = (ListStringOption) o;
    return getPath().equalsIgnoreCase(other.getPath());
  }
}
