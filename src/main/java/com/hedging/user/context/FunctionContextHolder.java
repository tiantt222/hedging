package com.hedging.user.context;

import com.hedging.user.entity.Function;

/**
 * @author tianwenlong
 */
public abstract class FunctionContextHolder {

  private static final ThreadLocal<Function> contextHolder = new ThreadLocal<>();

  public static Function getFunction() {
    return contextHolder.get();
  }

  public static void setFunction(Function function) {
    contextHolder.set(function);
  }

  public static void clear() {
    contextHolder.remove();
  }

}
