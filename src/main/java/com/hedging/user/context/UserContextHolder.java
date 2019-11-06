package com.hedging.user.context;

import com.hedging.user.entity.User;

/**
 * @author tianwenlong
 *
 */
public abstract class UserContextHolder {

  private static final ThreadLocal<User> contextHolder = new ThreadLocal<>();

  public static User getCurrentUser() {
    return contextHolder.get();
  }

  public static void setCurrentUser(User user) {
    contextHolder.set(user);
  }

  public static void clear() {
    contextHolder.remove();
  }

}
