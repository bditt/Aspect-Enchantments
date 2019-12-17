package net.AspectNetwork.bditt.API.commands.type;


import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import org.bukkit.command.CommandSender;

import net.AspectNetwork.bditt.API.commands.exceptions.TypeException;
import net.AspectNetwork.bditt.API.utils.Text;

public abstract class TypeAbstract<T> implements Type<T> {

  @Override
  public String getName() {
    int prefixLength = "type".length();

    String name = this.getClass().getSimpleName();
    name = name.substring(prefixLength);

    final List<String> words = Text.camelsplit(name);

    return Text.implode(words, " ").toLowerCase();
  }

  @Override
  public final T read(CommandSender sender) throws TypeException {
    return this.read(null, sender);
  }
  @Override
  public final T read(String arg) throws TypeException {
    return this.read(arg, null);
  }
  @Override
  public final T read() throws TypeException {
    return this.read(null, null);
  }

  @Override
  public boolean isValid(String arg, CommandSender sender) {
    try {
      this.read(arg, sender);
      return true;
    } catch (TypeException ex) {
      return false;
    }
  }

  @Override
  public Collection<String> getTabList(CommandSender sender, String arg) {
    return null;
  }
  @Override
  public final List<String> getTabListFiltered(CommandSender sender, String arg) {
    // Get the raw tab list.
    Collection<String> raw = this.getTabList(sender, arg);

    // Handle null case.
    if (raw == null || raw.isEmpty()) return Collections.emptyList();

    // Only keep the suggestions that starts with what the user already typed in.
    // This is the first basic step of tab completion.
    // "Ca" can complete into "Cayorion".
    // "Ma" can complete into "Madus"
    // "Ca" can not complete into "Madus" because it does not start with ignore case.
    List<String> ret = Text.getStartsWithIgnoreCase(raw, arg);

    // Initial simple cleanup of suggestions.
    cleanSuggestions(ret);

    return ret;
  }
  private static void cleanSuggestions(List<String> suggestions) {
    ListIterator<String> iter = suggestions.listIterator();
    while (iter.hasNext()) {
      String suggestion = iter.next();
      if (suggestion == null || suggestion.isEmpty()) {
        iter.remove();
      }
    }
  }

  boolean allowSpaceAfterTab = false;
  public boolean allowSpaceAfterTab() {
    return allowSpaceAfterTab;
  }
  public <T extends Type> T setAllowSpaceAfterTab(boolean allowSpaceAfterTab) {
    this.allowSpaceAfterTab = allowSpaceAfterTab;
    return (T) this;
  }

  @Override
  public final boolean equals(T type1, T type2) {
    if (type1 == null) return type2 == null;
    if (type2 == null) return type1 == null;
    return this.equalsInner(type1, type2);
  }
  @Override
  public final boolean equalsInner(T type1, T type2) {
    return type1.equals(type2);
  }
}
