package net.AspectNetwork.bditt.API.enchantment.event;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EnchantmentEventHandler {

  EnchantmentEventPriority priority() default EnchantmentEventPriority.NORMAL;
  boolean ignoreCancelled() default false;

}
