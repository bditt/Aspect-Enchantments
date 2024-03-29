package net.AspectNetwork.bditt.API.utils;


import java.util.regex.Pattern;

public class RomanNumeral {

  // Global fields
  private static int[] numbers = {
      1000,
      900,
      500,
      400,
      100,
      90,
      50,
      40,
      10,
      9,
      5,
      4,
      1
  };
  private static String[] letters = {
      "M",
      "CM",
      "D",
      "CD",
      "C",
      "XC",
      "L",
      "XL",
      "X",
      "IX",
      "V",
      "IV",
      "I"
  };
  // End of Global Fields

  // Methods
  public static String getRomanFromInt(int number) {
    return new RN(number).toString();
  }
  public static int getIntFromRoman(String num) {
    return new RN(num).toInt();
  }

  private final static Pattern regex = Pattern
      .compile("M{0,4}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})");
  public static boolean isRoman(String s) {
    return s != null && !"".equals(s) && regex.matcher(s.toUpperCase()).matches();
  }

  private static class RN {

    // Class base fields
    private final int num;
    //

    // Constructor
    public RN(int arabic) {
      if (arabic < 1) throw new NumberFormatException("Value of RomanNumeral must be positive.");
      if (arabic > 3999)
        throw new NumberFormatException("Value of RomanNumeral must be 3999 or less.");
      num = arabic;
    }

    public RN(String roman) {

      if (roman.length() == 0)
        throw new NumberFormatException("An empty string does not define a Roman numeral.");

      roman = roman.toUpperCase();

      int i = 0;
      int arabic = 0;
      while (i < roman.length()) {

        char letter = roman.charAt(i);
        int number = letterToNumber(letter);

        i++;

        if (i == roman.length()) {
          arabic += number;
        } else {
          int nextNumber = letterToNumber(roman.charAt(i));
          if (nextNumber > number) {
            arabic += (nextNumber - number);
            i++;
          } else {
            arabic += number;
          }
        }

      }

      if (arabic > 3999)
        throw new NumberFormatException("Roman numeral must have value 3999 or less.");

      num = arabic;

    }

    private int letterToNumber(char letter) {
      switch (letter) {
        case 'I':
          return 1;
        case 'V':
          return 5;
        case 'X':
          return 10;
        case 'L':
          return 50;
        case 'C':
          return 100;
        case 'D':
          return 500;
        case 'M':
          return 1000;
        default:
          throw new NumberFormatException("Illegal character \"" + letter + "\" in Roman numeral");
      }
    }

    public String toString() {
      String roman = ""; // The roman numeral.
      int N = num; // N represents the part of num that still has
      // to be converted to Roman numeral representation.
      for (int i = 0; i < numbers.length; i++) {
        while (N >= numbers[i]) {
          roman += letters[i];
          N -= numbers[i];
        }
      }
      return roman;
    }
    public int toInt() {
      return num;
    }

  }
}