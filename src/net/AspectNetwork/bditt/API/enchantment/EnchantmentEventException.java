package net.AspectNetwork.bditt.API.enchantment;


public class EnchantmentEventException extends RuntimeException {

  // Global fields
  private static final long serialVersionUID = 6141478629585371864L;
  // End of Global Fields

  // Constructor
  EnchantmentEventException(String message) {
    super(message);
  }
  EnchantmentEventException(String message, Throwable cause) {
    super(message, cause);
  }
  EnchantmentEventException(Throwable cause) {
    super(cause);
  }
}
