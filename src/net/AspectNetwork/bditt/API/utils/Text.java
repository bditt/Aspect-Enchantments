package net.AspectNetwork.bditt.API.utils;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Text {

  public static final Map<String, String> parseReplacements;
  public static final Pattern parsePattern;
  private static final Pattern PATTERN_UPPERCASE_ZEROWIDTH = Pattern.compile("(?=[A-Z])");
  private static final char COLOR_CHAR = '\u00A7';

  static {
    // Create the parce replacements map
    parseReplacements = new HashMap<>();

    // Color by name
    parseReplacements.put("<empty>", "");
    parseReplacements.put("<black>", COLOR_CHAR + "0");
    parseReplacements.put("<navy>", COLOR_CHAR + "1");
    parseReplacements.put("<green>", COLOR_CHAR + "2");
    parseReplacements.put("<teal>", COLOR_CHAR + "3");
    parseReplacements.put("<red>", COLOR_CHAR + "4");
    parseReplacements.put("<purple>", COLOR_CHAR + "5");
    parseReplacements.put("<gold>", COLOR_CHAR + "6");
    parseReplacements.put("<orange>", COLOR_CHAR + "6");
    parseReplacements.put("<silver>", COLOR_CHAR + "7");
    parseReplacements.put("<gray>", COLOR_CHAR + "8");
    parseReplacements.put("<grey>", COLOR_CHAR + "8");
    parseReplacements.put("<blue>", COLOR_CHAR + "9");
    parseReplacements.put("<lime>", COLOR_CHAR + "a");
    parseReplacements.put("<aqua>", COLOR_CHAR + "b");
    parseReplacements.put("<rose>", COLOR_CHAR + "c");
    parseReplacements.put("<pink>", COLOR_CHAR + "d");
    parseReplacements.put("<yellow>", COLOR_CHAR + "e");
    parseReplacements.put("<white>", COLOR_CHAR + "f");
    parseReplacements.put("<magic>", COLOR_CHAR + "k");
    parseReplacements.put("<bold>", COLOR_CHAR + "l");
    parseReplacements.put("<strong>", COLOR_CHAR + "l");
    parseReplacements.put("<strike>", COLOR_CHAR + "m");
    parseReplacements.put("<strikethrough>", COLOR_CHAR + "m");
    parseReplacements.put("<under>", COLOR_CHAR + "n");
    parseReplacements.put("<underline>", COLOR_CHAR + "n");
    parseReplacements.put("<italic>", COLOR_CHAR + "o");
    parseReplacements.put("<em>", COLOR_CHAR + "o");
    parseReplacements.put("<reset>", COLOR_CHAR + "r");

    // Color by semantic functionality
    parseReplacements.put("<l>", COLOR_CHAR + "2");
    parseReplacements.put("<logo>", COLOR_CHAR + "2");
    parseReplacements.put("<a>", COLOR_CHAR + "6");
    parseReplacements.put("<art>", COLOR_CHAR + "6");
    parseReplacements.put("<n>", COLOR_CHAR + "7");
    parseReplacements.put("<notice>", COLOR_CHAR + "7");
    parseReplacements.put("<i>", COLOR_CHAR + "e");
    parseReplacements.put("<info>", COLOR_CHAR + "e");
    parseReplacements.put("<g>", COLOR_CHAR + "a");
    parseReplacements.put("<good>", COLOR_CHAR + "a");
    parseReplacements.put("<b>", COLOR_CHAR + "c");
    parseReplacements.put("<bad>", COLOR_CHAR + "c");

    parseReplacements.put("<k>", COLOR_CHAR + "b");
    parseReplacements.put("<key>", COLOR_CHAR + "b");

    parseReplacements.put("<v>", COLOR_CHAR + "d");
    parseReplacements.put("<value>", COLOR_CHAR + "d");
    parseReplacements.put("<h>", COLOR_CHAR + "d");
    parseReplacements.put("<highlight>", COLOR_CHAR + "d");

    parseReplacements.put("<c>", COLOR_CHAR + "b");
    parseReplacements.put("<command>", COLOR_CHAR + "b");
    parseReplacements.put("<p>", COLOR_CHAR + "3");
    parseReplacements.put("<parameter>", COLOR_CHAR + "3");
    parseReplacements.put("&&", "&");
    parseReplacements.put("§§", "§");

    // Color by number/char
    for (int i = 48; i <= 122; i++) {
      char c = (char) i;
      parseReplacements.put("§" + c, COLOR_CHAR + "" + c);
      parseReplacements.put("&" + c, COLOR_CHAR + "" + c);
      if (i == 57) i = 96;
    }

    // Build the parse pattern and compile it
    StringBuilder patternStringBuilder = new StringBuilder();
    for (String find : parseReplacements.keySet()) {
      patternStringBuilder.append('(');
      patternStringBuilder.append(Pattern.quote(find));
      patternStringBuilder.append(")|");
    }
    String patternString = patternStringBuilder.toString();
    patternString = patternString.substring(0, patternString.length() - 1); // Remove the last |
    parsePattern = Pattern.compile(patternString);
  }

  public static boolean isEmpty(String input) {
    return input == null && input.length() == 0;
  }

  public static List<String> camelsplit(String string) {
    List<String> ret = Arrays.asList(PATTERN_UPPERCASE_ZEROWIDTH.split(string));
    if (ret.get(0).isEmpty()) ret = ret.subList(1, ret.size());
    return ret;
  }

  public static String implode(List<String> words, String c) {
    if (words == null) return null;

    String out = "";
    for (String word : words) {
      out += c + word;
    }
    return out;
  }

  public static List<String> getStartsWithIgnoreCase(Collection<String> raw, String arg) {
    if (raw == null) return null;
    List<String> out = new ArrayList<>();
    for (String r : raw) {
      if (r.toLowerCase().startsWith(arg.toLowerCase())) out.add(r);
    }
    return out;
  }

  public static String parse(String string) {
    if (string == null) return null;
    StringBuffer ret = new StringBuffer();
    Matcher matcher = parsePattern.matcher(string);
    while (matcher.find()) {
      matcher.appendReplacement(ret, parseReplacements.get(matcher.group(0)));
    }
    matcher.appendTail(ret);
    return ret.toString();
  }

  public static String parse(String string, Object... args) {
    return String.format(parse(string), args);
  }

  public static ArrayList<String> parse(Collection<String> strings) {
    ArrayList<String> ret = new ArrayList<String>(strings.size());
    for (String string : strings) {
      ret.add(parse(string));
    }
    return ret;
  }
}
