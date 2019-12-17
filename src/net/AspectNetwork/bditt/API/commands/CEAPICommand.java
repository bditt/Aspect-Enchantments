package net.AspectNetwork.bditt.API.commands;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import net.AspectNetwork.bditt.API.CustomEnchantmentAPI;
import net.AspectNetwork.bditt.API.commands.exceptions.CEAPICommandException;
import net.AspectNetwork.bditt.API.commands.exceptions.TypeException;
import net.AspectNetwork.bditt.API.commands.requirement.Requirement;
import net.AspectNetwork.bditt.API.commands.type.Type;
import net.AspectNetwork.bditt.API.utils.Text;

public class CEAPICommand {

  protected Visibility visibility = Visibility.SECRET;
  public final Visibility getVisibility() {
    return this.visibility;
  }
  public final <T extends CEAPICommand> T setVisibility(Visibility visibility) {
    this.visibility = visibility;
    return (T) this;
  }
  public boolean isVisibleTo(CommandSender sender) {
    if (this.getVisibility() == Visibility.VISIBLE) return true;
    if (this.getVisibility() == Visibility.INVISIBLE) return false;
    return this.isRequirementsMet(sender);
  }

  protected Plugin activePlugin = null;
  public final Plugin getActivePlugin() {
    return this.activePlugin;
  }
  public final <T extends CEAPICommand> T setActivePlugin(Plugin activePlugin) {
    this.activePlugin = activePlugin;
    return (T) this;
  }

  protected final List<String> alias = new ArrayList<>();
  public final List<String> getAlias() {
    return new ArrayList<>(alias);
  }
  public final <T extends CEAPICommand> T addAlias(String alia) {
    alias.add(alia);
    return (T) this;
  }
  public final <T extends CEAPICommand> T removeAlias(String alia) {
    alias.remove(alia);
    return (T) this;
  }

  protected final List<CEAPICommand> subCommands = new ArrayList<>();
  public final List<CEAPICommand> getSubCommands() {
    return new ArrayList<>(subCommands);
  }
  public final <T extends CEAPICommand> T addSubCommands(CEAPICommand subCommand) {
    subCommands.add(subCommand);
    return (T) this;
  }
  public final <T extends CEAPICommand> T removeSubCommands(CEAPICommand subCommand) {
    subCommands.remove(subCommand);
    return (T) this;
  }
  public CEAPICommand getSubCommand(String token) {
    for (CEAPICommand subCommand : getSubCommands()) {
      for (String alia : subCommand.getAlias()) {
        if (alia.equalsIgnoreCase(token)) return subCommand;
      }
    }
    return null;
  }

  protected final List<Requirement> requirements = new ArrayList<>();
  public final List<Requirement> getRequirements() {
    return new ArrayList<>(requirements);
  }
  public final <T extends CEAPICommand> T addRequirements(Requirement requirement) {
    requirements.add(requirement);
    return (T) this;
  }
  public final <T extends CEAPICommand> T removeRequirements(Requirement requirement) {
    requirements.remove(requirement);
    return (T) this;
  }
  /* *********************** */
  /*       PARAMETERS        */
  /* *********************** */

  protected final List<Parameter<?>> parameters = new ArrayList<>();
  public final List<Parameter<?>> getParameters() {
    return this.parameters;
  }
  public final <T extends CEAPICommand> T setParameters(List<Parameter<?>> parameters) {
    this.parameters.clear();
    this.parameters.addAll(parameters);
    return (T) this;
  }

  public final Parameter<?> getParameter(int index) {
    if (this.isConcatenating() && this.getConcatenationIndex() < index)
      index = this.getConcatenationIndex();
    return this.getParameters().get(index);
  }
  public final <T extends CEAPICommand> T setParameter(int index, Parameter<?> parameter) {
    if (this.isConcatenating() && this.getConcatenationIndex() < index)
      index = this.getConcatenationIndex();
    this.getParameters().set(index, parameter);
    return (T) this;
  }

  public final Type<?> getParameterType(int index) {
    Parameter<?> parameter = this.getParameter(index);
    return parameter.getType();
  }
  public final <T extends CEAPICommand> T setParameterType(int index, Type<?> type) {
    this.getParameter(index).setType((Type) type);
    return (T) this;
  }

  public boolean hasParameterForIndex(int index) {
    if (index < 0) return false;
    if (this.isConcatenating() && this.getConcatenationIndex() < index)
      index = this.getConcatenationIndex();
    if (this.getParameters().size() <= index) return false;
    return true;
  }

  // -------------------------------------------- //
  // PARAMETERS > COUNT
  // -------------------------------------------- //
  public int getParameterCount(CommandSender sender) {
    return this.getParameterCountRequired(sender) + this.getParameterCountOptional(sender);
  }
  public int getParameterCountRequired(CommandSender sender) {
    int ret = 0;
    for (Parameter<?> parameter : this.getParameters()) {
      if (parameter.isRequiredFor(sender)) ret++;
    }
    return ret;
  }
  public int getParameterCountOptional(CommandSender sender) {
    int ret = 0;
    for (Parameter<?> parameter : this.getParameters()) {
      if (parameter.isOptionalFor(sender)) ret++;
    }
    return ret;
  }

  // -------------------------------------------- //
  // PARAMETERS > ADD
  // -------------------------------------------- //
  public <T> Parameter<T> addParameter(Parameter<T> parameter, boolean concatFromHere) {
    if (this.isConcatenating()) {
      throw new IllegalStateException("You can't add args if a prior one concatenates.");
    }

    int prior = this.getParameters().size() - 1;
    if (this.hasParameterForIndex(prior) && this.getParameter(prior).isOptional() && parameter
        .isRequired()) {
      throw new IllegalArgumentException(
          "You can't add required args, if a prior one is optional.");
    }
    this.setConcatenating(concatFromHere);

    this.getParameters().add(parameter);
    return parameter;
  }
  public <T> Parameter<T> addParameter(Parameter<T> parameter) {
    return this.addParameter(parameter, false);
  }
  public <T> Parameter<T> addParameter(T defaultValue, Type<T> type, boolean requiredFromConsole,
      String name, String defaultDesc, boolean concatFromHere) {
    return this
        .addParameter(new Parameter<T>(defaultValue, type, requiredFromConsole, name, defaultDesc),
            concatFromHere);
  }
  public <T> Parameter<T> addParameter(Type<T> type, boolean requiredFromConsole, String name,
      String defaultDesc, boolean concatFromHere) {
    return this.addParameter(new Parameter<T>(type, requiredFromConsole, name, defaultDesc),
        concatFromHere);
  }
  public <T> Parameter<T> addParameter(T defaultValue, Type<T> type, String name,
      String defaultDesc, boolean concatFromHere) {
    return this
        .addParameter(new Parameter<T>(defaultValue, type, name, defaultDesc), concatFromHere);
  }
  public <T> Parameter<T> addParameter(T defaultValue, Type<T> type, boolean requiredFromConsole,
      String name, boolean concatFromHere) {
    return this.addParameter(new Parameter<T>(defaultValue, type, requiredFromConsole, name),
        concatFromHere);
  }
  public <T> Parameter<T> addParameter(T defaultValue, Type<T> type, boolean requiredFromConsole,
      String name, String defaultDesc) {
    return this
        .addParameter(new Parameter<T>(defaultValue, type, requiredFromConsole, name, defaultDesc),
            false);
  }
  public <T> Parameter<T> addParameter(Type<T> type, String name, String defaultDesc,
      boolean concatFromHere) {
    return this.addParameter(new Parameter<T>(type, name, defaultDesc), concatFromHere);
  }
  public <T> Parameter<T> addParameter(Type<T> type, boolean requiredFromConsole, String name,
      boolean concatFromHere) {
    return this.addParameter(new Parameter<T>(type, requiredFromConsole, name), concatFromHere);
  }
  public <T> Parameter<T> addParameter(Type<T> type, boolean requiredFromConsole, String name,
      String defaultDesc) {
    return this.addParameter(new Parameter<T>(type, requiredFromConsole, name, defaultDesc));
  }
  public <T> Parameter<T> addParameter(T defaultValue, Type<T> type, String name,
      boolean concatFromHere) {
    return this.addParameter(new Parameter<T>(defaultValue, type, name), concatFromHere);
  }
  public <T> Parameter<T> addParameter(T defaultValue, Type<T> type, String name,
      String defaultDesc) {
    return this.addParameter(new Parameter<T>(defaultValue, type, name, defaultDesc));
  }
  public <T> Parameter<T> addParameter(T defaultValue, Type<T> type, boolean requiredFromConsole,
      String name) {
    return this.addParameter(new Parameter<T>(defaultValue, type, requiredFromConsole, name));
  }
  public <T> Parameter<T> addParameter(Type<T> type, String name, boolean concatFromHere) {
    return this.addParameter(new Parameter<T>(type, name), concatFromHere);
  }
  public <T> Parameter<T> addParameter(Type<T> type, String name, String defaultDesc) {
    return this.addParameter(new Parameter<T>(type, name, defaultDesc));
  }
  public <T> Parameter<T> addParameter(Type<T> type, boolean requiredFromConsole, String name) {
    return this.addParameter(new Parameter<T>(type, requiredFromConsole, name));
  }
  public <T> Parameter<T> addParameter(T defaultValue, Type<T> type, String name) {
    return this.addParameter(new Parameter<T>(defaultValue, type, name));
  }
  public <T> Parameter<T> addParameter(Type<T> type, String name) {
    return this.addParameter(new Parameter<T>(type, name));
  }
  public <T> Parameter<T> addParameter(Type<T> type, boolean concatFromHere) {
    return this.addParameter(new Parameter<T>(type), concatFromHere);
  }
  public <T> Parameter<T> addParameter(Type<T> type) {
    return this.addParameter(new Parameter<T>(type));
  }

  /* *********************** */
  /*        PUZZLER          */
  /* *********************** */
  protected boolean overflowSensitive = true;
  public boolean isOverflowSensitive() {
    return overflowSensitive;
  }
  public <T extends CEAPICommand> T setOverflowSensitive(boolean overflowSensitive) {
    this.overflowSensitive = overflowSensitive;
    return (T) this;
  }

  private boolean concatenating = false;
  public boolean isConcatenating() {
    return this.concatenating;
  }
  public void setConcatenating(boolean concatenating) {
    this.concatenating = concatenating;
  }
  public int getConcatenationIndex() {
    return this.getParameters().size() - 1;
  }

  public List<String> applyConcatenating(List<String> args) {
    if (!this.isConcatenating()) return args;

    List<String> ret = new ArrayList<>();

    final int maxIdx = Math.min(this.getConcatenationIndex(), args.size());
    ret.addAll(args.subList(0, maxIdx)); // The args that should not be concatenated.
    if (args.size() > maxIdx) {
      ret.add(Text.implode(args.subList(maxIdx, args.size()), " "));
    }
    return ret;
  }

  // -------------------------------------------- //
  // CALL VALIDATION
  // -------------------------------------------- //

  public boolean isArgsValid(List<String> args, CommandSender sender) {
    if (args.size() < this.getParameterCountRequired(sender)) {
      if (sender != null) {
        sender.sendMessage(
            CustomEnchantmentAPI.getInstance().getLanguageConfig().NOT_ENOUGH_ARGUMENTS.format());
      }
      return false;
    }
    if (args.size() > this.getParameterCount(sender) && this.isOverflowSensitive()) {
      if (sender != null) {
        List<String> theTooMany = args.subList(this.getParameterCount(sender), args.size());
        sender.sendMessage(
            CustomEnchantmentAPI.getInstance().getLanguageConfig().TO_MANY_ENOUGH_ARGUMENTS
                .format(theTooMany.toString()));
      }
      return false;
    }

    return true;
  }
  public boolean isArgsValid(List<String> args) {
    return this.isArgsValid(args, null);
  }

  /* *********************** */
  /*        EXECUTOR         */
  /* *********************** */
  private String fullCommand = "";
  public <T extends CEAPICommand> T setFullCommand(String fullCommand) {
    this.fullCommand = fullCommand;
    return (T) this;
  }
  public String getFullCommand() {
    return this.fullCommand;
  }

  private List<String> args = new ArrayList<>();
  public List<String> getArgs() {
    return this.args;
  }
  public <T extends CEAPICommand> T setArgs(List<String> args) {
    this.args = args;
    return (T) this;
  }

  private List<CEAPICommand> chain = new ArrayList<>();
  public List<CEAPICommand> getChain() {
    return new ArrayList<>(this.chain);
  }
  public <T extends CEAPICommand> T setChain(List<CEAPICommand> chain) {
    this.chain = new ArrayList<>(chain);
    return (T) this;
  }
  public <T extends CEAPICommand> T addToChain(CEAPICommand command) {
    this.chain.add(0, command);
    return (T) this;
  }

  public CEAPICommand getParent() {
    List<CEAPICommand> chain = this.getChain();
    if (chain == null) return null;
    if (chain.isEmpty()) return null;
    return chain.get(chain.size() - 1);
  }
  public boolean hasParent() {
    return this.getParent() != null;
  }
  public boolean isParent() {
    return this.getSubCommands().size() > 0;
  }

  /* *********************** */
  /*        EXECUTION        */
  /* *********************** */

  private final void execute(CommandSender sender, List<String> args, List<CEAPICommand> chain) {
    try {
      // Update Chain
      setChain(chain);

      this.senderFieldsOuter(sender);

      // Update args
      this.setArgs(args);

      // Checks Command Requirements
      if (!isRequirementsMet(sender)) {
        sender.sendMessage(this.getRequirementsError(sender));
        return;
      }

      // Checks for sub commands
      if (this.isParent() && args.size() > 0) {
        String token = args.get(0).toLowerCase();
        CEAPICommand subCommand = getSubCommand(token);
        if (subCommand != null) {
          args.remove(0);

          List<CEAPICommand> childChain = new ArrayList<>(chain);
          childChain.add(this);

          subCommand.setFullCommand(this.getFullCommand());
          subCommand.execute(sender, args, childChain);
          return;
        }
        sender.sendMessage(CustomEnchantmentAPI.getInstance().getLanguageConfig().UNKNOWN_COMMAND
            .format(this.getFullCommand()));
        return;
      }

      if (!this.isArgsValid(this.getArgs(), this.sender)) return;

      loop:
      for (int i = 0; i < args.size(); i++) {
        if (i + 1 > parameters.size()) break loop;
        if (!parameters.get(i).getType().isValid(args.get(i), sender)) {
          sender.sendMessage(parameters.get(i).getType().getInvelidErrorMessage(args.get(i)));
          return;
        }
      }

      this.perform();
    } catch (CEAPICommandException error) {
      sender.sendMessage(error.getMessage());
    } finally {
      senderFieldsOuter(null);
    }
  }
  private final void execute(CommandSender sender, List<String> args) {
    execute(sender, args, new ArrayList<>());
  }

  public void perform() throws CEAPICommandException {
    if (subCommands.size() > 0 || getParameterCountRequired(sender) > 0)
      sender.sendMessage(
          CustomEnchantmentAPI.getInstance().getLanguageConfig().NOT_ENOUGH_ARGUMENTS.format());
  }

  /* ************************** */
  /*         CHECKS             */
  /* ************************** */

  public final boolean isRequirementsMet(CommandSender sender) {
    for (Requirement requirement : this.getRequirements()) {
      if (!requirement.apply(sender, this)) return false;
    }
    return true;
  }
  public String getRequirementsError(CommandSender sender) {
    for (Requirement requirement : this.getRequirements()) {
      if (!requirement.apply(sender, this)) return requirement.createErrorMessage(sender, this);
    }
    return null;
  }

  // ------------------------------------------- //
  //              READING DATA                   //
  // ------------------------------------------- //

  // ARGS
  protected int nextArg = 0;
  public final int getNextArg() {
    return nextArg;
  }

  public boolean argIsSet(int idx) {
    if (idx < 0) return false;
    if (idx + 1 > this.getArgs().size()) return false;
    if (this.getArgs().get(idx) == null) return false;
    return true;
  }
  public boolean argIsSet() {
    return this.argIsSet(nextArg);
  }

  public String arg() {
    return this.argAt(nextArg);
  }
  public String argAt(int idx) {
    nextArg = idx + 1;
    if (!this.argIsSet(idx)) return null;
    return this.getArgs().get(idx);
  }

  public <T> T readArg() throws TypeException {
    return this.readArgAt(nextArg);
  }
  public <T> T readArg(T defaultNotSet) throws TypeException {
    return this.readArgAt(nextArg, defaultNotSet);
  }

  @SuppressWarnings("unchecked")
  public <T> T readArgAt(int idx) throws TypeException {
    // Make sure that a Parameter is present.
    if (!this.hasParameterForIndex(idx)) throw new IllegalArgumentException(
        idx + " is out of range. Parameters size: " + this.getParameters().size());

    // Increment
    nextArg = idx + 1;

    // Get the parameter
    Parameter<T> parameter = (Parameter<T>) this.getParameter(idx);
    // Return the default in the parameter.
    if (!this.argIsSet(idx) && parameter.isDefaultValueSet()) return parameter.getDefaultValue();

    // OLD: Throw error if there was no arg, or default value in the parameter.
    // OLD: if ( ! this.argIsSet(idx)) throw new IllegalArgumentException("Trying to access arg: " + idx + " but that is not set.");
    // NOTE: This security actually blocks some functionality. Certain AR handle null argument values and specify their own default from within.
    // NOTE: An example is the MassiveQuest ARMNode which defaults to the used node of the player but must error when the player has no used node: "You must use a quest to skip the optional argument.".

    // Get the arg.
    String arg = null;
    if (this.argIsSet(idx)) arg = this.getArgs().get(idx);

    // Read and return the arg.
    return parameter.getType().read(arg, sender);
  }
  public <T> T readArgAt(int idx, T defaultNotSet) throws TypeException {
    // Return the default passed.
    if (!this.argIsSet(idx)) {
      // Increment
      nextArg = idx + 1;

      // Use default
      return defaultNotSet;
    }

    // Increment is done in this method
    return readArgAt(idx);
  }

  // SENDER
  protected CommandSender sender = null;
  public final CommandSender getSender() {
    return sender;
  }

  protected Player me = null;
  public final Player getMe() {
    return me;
  }

  protected boolean senderIsConsole = true;
  public final boolean isSenderIsConsole() {
    return senderIsConsole;
  }

  // DEFINERS FOR THE VARIABLES ABOVE
  public void senderFieldsOuter(CommandSender sender) {
    this.nextArg = 0;
    this.sender = sender;
    this.senderIsConsole = true;
    this.me = null;
    if (sender instanceof Player) {
      this.me = (Player) sender;
      this.senderIsConsole = false;
    }

    boolean set = (sender != null);
    this.senderFields(set);
  }
  public void senderFields(boolean set) {
  }

  // --------------------------------- //
  //               TAB COMPLETE        //
  // --------------------------------- //

  public List<String> getTabCompletions(List<String> args, CommandSender sender) {
    if (args == null) throw new NullPointerException("args");
    if (sender == null) throw new IllegalArgumentException("sender was null");
    if (args.isEmpty()) throw new IllegalArgumentException("args was empty");

    if (this.isParent()) {
      return this.getTabCompletionsChild(args, sender);
    } else {
      return this.getTabCompletionsArg(args, sender);
    }
  }
  protected List<String> getTabCompletionsChild(List<String> args, CommandSender sender) {
    if (args.size() != 1) {
      CEAPICommand child = this.getSubCommand(args.get(0));
      if (child == null) return Collections.emptyList();

      args.remove(0);
      return child.getTabCompletions(args, sender);
    }

    List<String> ret = new ArrayList<>();
    String token = args.get(args.size() - 1).toLowerCase();
    for (CEAPICommand child : this.getSubCommands()) {
      if (!child.isVisibleTo(sender)) continue;
      ret.addAll(Text.getStartsWithIgnoreCase(child.getAlias(), token));
    }
    return addSpaceAtEnd(ret);
  }
  protected List<String> getTabCompletionsArg(List<String> args, CommandSender sender) {
    args = this.applyConcatenating(args);

    int index = args.size() - 1;
    if (!this.hasParameterForIndex(index)) return Collections.emptyList();
    Type<?> type = this.getParameterType(index);

    List<String> ret = type.getTabListFiltered(sender, args.get(index));

    // If the type allows space after tab and this is not the last possible argument...
    if (type.allowSpaceAfterTab() && this.hasParameterForIndex(args.size())) {
      // ...we will sometimes add a space at the end. Depending on list size.
      ret = addSpaceAtEnd(ret);
    }

    return ret;
  }
  protected static List<String> addSpaceAtEnd(List<String> suggestions) {
    if (suggestions.size() != 1) return suggestions;
    String suggestion = suggestions.get(0);

    suggestion += ' ';
    return Collections.singletonList(suggestion);
  }

  /* ******************************************* */
  /*         DEFAULT CLASS OVERRIDES             */
  /* ******************************************* */

  @Override
  public boolean equals(Object o) {
    if (o == this) return true;
    if (o.getClass() != this.getClass()) return false;
    CEAPICommand other = (CEAPICommand) o;

    for (String alias : getAlias()) {
      if (other.getAlias().contains(alias)) return true;
    }
    return false;
  }

  // ------------------------------------------ //
  //                REGISTERY                   //
  // ------------------------------------------ //

  public void registerCommand(Plugin plugin) {
    registerCommand(plugin, this);
  }
  public static void registerCommand(Plugin plugin, CEAPICommand command) {
    TabExecutor exec = new DefailtExecutor(command);
    for (String alias : command.getAlias()) {
      PluginCommand com = Bukkit.getPluginCommand(alias);
      com.setExecutor(exec);
      com.setTabCompleter(exec);
    }
    command.setActivePlugin(plugin);
  }
  private static class DefailtExecutor implements TabExecutor {

    private final CEAPICommand com;

    public DefailtExecutor(CEAPICommand com) {
      this.com = com;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command c, String label, String[] args) {
      com.setFullCommand(label + Text.implode(Arrays.asList(args), " "));
      com.execute(sender, new ArrayList<>(Arrays.asList(args)));
      return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command c, String alias,
        String[] args) {
      if (args.length < 1) return null;
      return com.getTabCompletions(new ArrayList<>(Arrays.asList(args)), sender);
    }
  }
}
