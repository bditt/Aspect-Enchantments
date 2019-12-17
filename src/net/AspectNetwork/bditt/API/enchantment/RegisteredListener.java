package net.AspectNetwork.bditt.API.enchantment;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import net.AspectNetwork.bditt.API.enchantment.event.EnchantmentEvent;
import net.AspectNetwork.bditt.API.enchantment.event.EnchantmentEventHandler;
import net.AspectNetwork.bditt.API.enchantment.event.EnchantmentEventPriority;
import net.AspectNetwork.bditt.API.enchantment.event.EnchantmentEventWithLevel;

public class RegisteredListener implements Comparable<RegisteredListener> {

  private final Enchantment enchantment;
  private final Method method;
  private final Type type;

  // Constructor
  RegisteredListener(Enchantment listener, Method method) {
    this.enchantment = listener;
    this.method = method;
    method.setAccessible(true);

    if (method.getParameterCount() == 1) {
      Parameter event_EnchantmentEvent = method.getParameters()[0];
      if (!EnchantmentEvent.class.isAssignableFrom(event_EnchantmentEvent.getType()))
        exception("The only Parameter of the method must be an instance of EnchantmentEvent");
      type = Type.EVENT;
      return;
    } else if (method.getParameterCount() == 2) {
      Parameter event_EnchantmentEvent = method.getParameters()[0];
      if (!EnchantmentEvent.class.isAssignableFrom(event_EnchantmentEvent.getType()))
        exception("The first Parameter of the method must be an instance of EnchantmentEvent");

      Parameter lvl_int = method.getParameters()[1];
      if (!int.class.equals(lvl_int.getType()))
        exception("The second Parameter of the method must be an int(Lvl)");

      if (event_EnchantmentEvent.getType().getAnnotation(EnchantmentEventWithLevel.class) == null)
        exception(
            "Method can't contain a int(lvl) as the second parameter, because the Event doesn't support it");

      type = Type.EVENT_LVL;
      return;
    } else {
      type = Type.INVALID;
      exception("Method must have a EnchantmentEvent(event), [int(lvl)] as the only parameter/s");
    }
  }

  private void exception(String message) throws IllegalArgumentException {
    throw new IllegalArgumentException(message + ": " + this);

  }

  void fireEvent(EnchantmentEvent event, int lvl) {
    try {
      if (Type.EVENT.equals(type)) {
        method.invoke(enchantment, event);
      } else if (Type.EVENT_LVL.equals(type)) {
        method.invoke(enchantment, event, lvl);
      }
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      throw new EnchantmentEventException("Exception in: " + this + "(" + e.getMessage() + ")",
          e.getCause());
    }
  }

  @Override
  public String toString() {
    return enchantment.getClass().getName() + "::" + method.getName();
  }

  @Override
  public int compareTo(RegisteredListener other) {
    return other.getPriority().compareTo(getPriority());
  }

  EnchantmentEventPriority getPriority() {
    return method.getAnnotation(EnchantmentEventHandler.class).priority();
  }

  @Override
  public int hashCode() {
    int result = enchantment.hashCode();
    result = 31 * result + method.hashCode();
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) return true;
    if (o == null || getClass() != o.getClass()) return false;
    RegisteredListener other = (RegisteredListener) o;
    return enchantment.equals(other.enchantment) && method.equals(other.method);
  }

  // Getters
  Class<?> getEventClass() {
    return method.getParameterTypes()[0];
  }

  Enchantment getEnchantment() {
    return enchantment;
  }

  Method getMethod() {
    return method;
  }

  boolean isIgnoreCancelled() {
    return method.getAnnotation(EnchantmentEventHandler.class).ignoreCancelled();
  }

  private enum Type {
    EVENT,
    EVENT_LVL,
    INVALID
  }
}
